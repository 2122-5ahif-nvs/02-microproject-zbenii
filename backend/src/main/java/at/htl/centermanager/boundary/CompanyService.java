package at.htl.centermanager.boundary;

import at.htl.centermanager.repository.CompanyRepository;
import at.htl.centermanager.entity.Company;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Tag(name = "Company Service", description = "CRUD Operations for the Company class")
@Path("/companies")
public class CompanyService {

    @Inject
    Logger logger;

    @Inject
    CompanyRepository repo;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Companies returned")
    @Transactional
    public Response getCompanies() {
        logger.log(Logger.Level.INFO, "All Companies returned");
        return Response
                .accepted(repo.getCompanyList())
                .header("tag", "A list of Companies")
                .build();
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getCompany(@PathParam("name") String name) {
        Company company = repo.getCompanyByName(name);
        logger.logf(Logger.Level.INFO, "Company %s returned", name);

        return Response
                .accepted(company)
                .header("tag", "Your desired Company")
                .build();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCompany(Company s) {
        boolean isAdded = repo.addCompany(s);

        if (!isAdded) {
            logger.logf(Logger.Level.WARN, "Company %s couldn't be added", s.getName());
            return Response
                    .ok("Company: " + s.getName() + " already exists!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Company %s added", s.getName());
        return Response
                .ok("Company: " + s.getName() + " added!")
                .build();
    }

    @Transactional
    @POST
    @Path("/multiple")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add multiple companies from an Array")
    public Response addCompanies(List<Company> companies) {
        List<Company> added = repo.addCompanies(companies);

        if (added.size() == 0) {
            logger.logf(Logger.Level.WARN, "Companies couldn't be added");
            return Response
                    .ok("All Companies already exist!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Companies %s added");
        return Response
                .ok("Companies added!")
                .build();
    }

    @Transactional
    @DELETE
    @Path("/{name}")
    public Response deleteCompanyByName(@PathParam("name") String name) {
        boolean isDeleted = repo.deleteCompanyByName(name);

        if (!isDeleted) {
            logger.logf(Logger.Level.WARN, "Company %s couldn't be deleted", name);
            return Response
                    .ok("Company: " + name + " doesn't exist!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Company %s added", name);
        return Response
                .ok("Company: " + name + " deleted!")
                .build();
    }

    @Transactional
    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCompanyByName(@PathParam("name") String name, Company s) {
        boolean isUpdated = repo.updateCompany(name, s);

        if (!isUpdated) {
            logger.logf(Logger.Level.WARN, "Company %s couldn't be updated", name);
            return Response
                    .ok("Company: " + name + " doesn't exist!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Company %s updated", name);
        return Response
                .ok("Company: " + name + " updated!")
                .build();
    }

    @Transactional
    @PATCH
    @Path("/{name}")
    @Operation(summary = "Update Patch for Company (doesn't work)")
    @Consumes(MediaType.APPLICATION_JSON_PATCH_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCompanyByName(@PathParam("name") String name, JsonPatch patch) throws JsonProcessingException {
        Company c = repo.getCompanyByName(name);

        if(c == null){
            return Response.ok("Company "+ name+ "doesn't exist!").build();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonStructure struct = mapper.convertValue(c, JsonStructure.class);
        JsonValue value = (JsonValue) patch.apply(struct);
        Company patchedCompany =  mapper.convertValue(value,Company.class);
        return Response.ok("Company "+ name+ "updated!").build();
    }


    /*
    @POST
    @Path("s4")
    @Consumes(MediaType.APPLICATION_JSON)
    public Company s4(JsonObject CompanyJson){
        return new Company(CompanyJson.getString("name"), Integer.parseInt(CompanyJson.getString("employeeAmount")),CompanyCategory.valueOf(CompanyJson.getString("category")));

        JsonValue x;
        x.getValueType();

    }
    */
}