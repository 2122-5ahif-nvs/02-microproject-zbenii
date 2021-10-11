package at.htl.centermanager.boundary;

import at.htl.centermanager.repository.ContractRepository;
import at.htl.centermanager.entity.Contract;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Tag(name = "Contract Service", description = "CRUD Operations for the Contract class")
@Path("/contracts")
public class ContractService {

    @Inject
    Logger logger;

    @Inject
    ContractRepository repo;

    @GET
    @Path("/byCompany")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Returns all shops for a specific company with Query Param")
    @APIResponse(responseCode = "200", description = "Shops returned")
    public Response getShopsByCompany(@QueryParam("compName") String compName) {
        logger.log(Logger.Level.INFO, "Desired Shops returned");
        return Response
                .accepted(repo.getShopsByCompName(compName))
                .header("tag", "A list of Shops")
                .build();
    }

    @GET
    @Path("/byCompany/{compName}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Returns info object with shop,contract and company")
    @APIResponse(responseCode = "200", description = "Shops returned")
    public Response getInfoByCompany(@PathParam("compName") String compName) {
        logger.log(Logger.Level.INFO, "Desired Info returned");
        return Response
                .accepted(repo.getInfoByCompName(compName))
                .header("tag", "Info object")
                .build();
    }

    @GET
    @Path("/totalRent/{compName}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Returns total rent cost for a company")
    public Response getTotalRentForCompany(@PathParam("compName") String compName) {
        logger.log(Logger.Level.INFO, "Total rent returned");
        return Response
                .accepted("Total Rent for " + compName + ": " + repo.getTotalRentForCompany(compName))
                .build();
    }

    @GET
    @Path("/totalSpace/{compName}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Returns total rent space for a company")
    public Response getTotalRentForSpaceCompany(@PathParam("compName") String compName) {
        logger.log(Logger.Level.INFO, "Total space returned");
        return Response
                .accepted("Total Space for " + compName + ": " + repo.getTotalRentSpaceForCompany(compName) + " m2")
                .build();
    }

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Returns all contracts   ")
    @APIResponse(responseCode = "200", description = "Contracts returned")
    public Response getContracts() {
        logger.log(Logger.Level.INFO, "All contracts returned");
        return Response
                .accepted(repo.getContractList())
                .header("tag", "A list of contracts")
                .build();
    }

    @Path("/{id}")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Returns single contract by given name")
    public Response getContract(@PathParam("id") String id) {
        Contract contract = repo.getContractById(Integer.parseInt(id));
        logger.logf(Logger.Level.INFO, "Contract for %s returned", id);

        return Response
                .accepted(contract)
                .header("tag", "Your desired Contract")
                .build();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Adds new given Contract")
    public Response addContract(Contract c) {
        boolean isAdded = repo.addContract(c);
        if (!isAdded) {
            logger.logf(Logger.Level.WARN, "Contract couldn't be added");
            return Response
                    .ok("Contract couldn't be added!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Contract for %s added", c.getCompany().getName());
        return Response
                .ok("Contract for " + c.getId() + " added!")
                .build();
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes Contract by given name")
    public Response deleteContractById(@PathParam("id") String id) {
        boolean isDeleted = repo.deleteContractById(Integer.parseInt(id));

        if (!isDeleted) {
            logger.logf(Logger.Level.WARN, "Contract for %s could't be deleted", id);
            return Response
                    .ok("Contract for " + id + " doesn't exist!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Contract for %s deleted", id);
        return Response
                .ok("Contract for " + id + " deleted!")
                .build();
    }

    @Transactional
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates contract by given name and new contract")
    public Response updateContractById(@PathParam("id") String id, Contract c) {
        boolean isUpdated = repo.updateContract(Integer.parseInt(id), c);

        if (!isUpdated) {
            logger.logf(Logger.Level.WARN, "Contract for %s could't be updated", id);
            return Response
                    .ok("Contract for " + id + " couldn't be updated!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Contract for %s updated", id);
        return Response
                .ok("Contract for " + id + " updated!")
                .build();
    }
}
