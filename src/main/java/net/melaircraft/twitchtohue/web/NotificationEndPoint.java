package net.melaircraft.twitchtohue.web;

import net.melaircraft.twitchtohue.hue.Controller;
import net.melaircraft.twitchtohue.hue.bulbs.XYBulbData;
import net.melaircraft.twitchtohue.hue.commands.FlashCommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Base64;

@Path("/")
public class NotificationEndPoint {
    private static final String base64Image = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR4nGP6zwAAAgcBApocMXEAAAAASUVORK5CYII=";
    private static final byte[] imageBytes = Base64.getDecoder().decode(base64Image);

    private final Controller controller;

    public NotificationEndPoint(Controller controller) {
        this.controller = controller;
    }

    @GET
    @Path("/{type}/{person}")
    @Produces("image/png")
    public Response receiveNotification(@PathParam("type") String type, @PathParam("person") String person) {
        System.out.println("Notification of " + type + " received for " + person + "!");

        switch (type) {
            case "follow":
                controller.submitCommand(new FlashCommand(new XYBulbData(true, 1.0, 0.6917, 0.2969)));
                break;
            case "host":
                controller.submitCommand(new FlashCommand(new XYBulbData(true, 1.0, 0.2093,0.6698)));
                break;
        }

        final Response.ResponseBuilder responseBuilder = Response.ok().entity(imageBytes);
        responseBuilder.header("Cache-Control", "no-cache, no-store, must-revalidate");
        responseBuilder.header("Pragma", "no-cache");
        responseBuilder.header("Expires", "0");
        return responseBuilder.build();
    }
}
