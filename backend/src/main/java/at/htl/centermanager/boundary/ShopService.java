package at.htl.centermanager.boundary;

import at.htl.centermanager.entity.Shop;
import at.htl.centermanager.repository.ShopRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Tag(name = "Shop Service", description = "CRUD Operations for the Shop class")
@Path("/shops")
public class ShopService {

    @Inject
    Logger logger;

    @Inject
    ShopRepository repo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getShops() {
        logger.log(Logger.Level.INFO, "All Shops returned");
        return Response
                .accepted(repo.getShopList())
                .header("tag", "A list of Shops")
                .build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getShop(@PathParam("id") String id) {
        Shop shop = repo.getShopById(Integer.parseInt(id));
        logger.logf(Logger.Level.INFO, "Shop: %s returned", shop.getId());

        return Response
                .accepted(shop)
                .header("tag", "Your desired Shop")
                .build();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShop(Shop r) {
        boolean isAdded = repo.addShop(r);
        if (!isAdded) {
            logger.logf(Logger.Level.WARN, "Shop: %d couldn't be added", r.getId());
            return Response
                    .ok(r.toString() + " already exists!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Shop: %d added", r.getId());
        return Response
                .ok(r.toString() + " added!")
                .build();
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    public Response deleteShopById(@PathParam("id") String id) {
        boolean isDeleted = repo.deleteShopById(Integer.parseInt(id));

        if (!isDeleted) {
            logger.logf(Logger.Level.WARN, "Shop: %s couldn't be deleted", id);
            return Response
                    .ok("Shop with id " + id + " doesn't exist!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Shop %s deleted", id);
        return Response
                .ok("Shop with id " + id + " deleted!")
                .build();
    }

    @Transactional
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateShopById(@PathParam("id") String id, Shop r) {
        boolean isUpdated = repo.updateShop(Integer.parseInt(id), r);

        if (!isUpdated) {
            logger.logf(Logger.Level.WARN, "Shop %s couldn't be updated", id);
            return Response
                    .ok("Shop with id " + id + " doesn't exist!")
                    .build();
        }

        logger.logf(Logger.Level.INFO, "Shop %s updated", id);
        return Response
                .ok("Shop with id " + id + " updated!")
                .build();
    }
}
