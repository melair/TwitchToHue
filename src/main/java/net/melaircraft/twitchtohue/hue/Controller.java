package net.melaircraft.twitchtohue.hue;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.melaircraft.twitchtohue.configuration.Configuration;
import net.melaircraft.twitchtohue.hue.bulbs.BulbData;
import net.melaircraft.twitchtohue.hue.commands.Command;
import net.melaircraft.twitchtohue.hue.commands.FlashCommand;
import org.json.JSONObject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Controller implements Runnable {
    private final BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();
    private final Configuration configuration;
    private final String lightURL;

    public Controller(Configuration configuration) {
        this.configuration = configuration;
        lightURL = "http://" + configuration.getHueAddress() + "/api/" + configuration.getHueAuthToken() + "/lights/" + configuration.getHueLightNumber();

        try {
            final HttpResponse<String> check = Unirest.get(lightURL).asString();

            if (check.getStatus() != 200) {
                System.out.println("Could not get light status! Check address, token and light number.");
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully checked light is available.");
    }

    private BulbData getBulb() {
        try {
            final HttpResponse<JsonNode> check = Unirest.get(lightURL).asJson();

            if (check.getStatus() != 200) {
                System.out.println("Could not get light status! Check address, token and light number.");
                return null;
            }

            final JsonNode jsonNode = check.getBody();

            if (jsonNode.isArray()) {
                System.out.println("Could not get light status! Returned data was a JSON array, not object.");
                return null;
            }

            System.out.println("Get Bulb Debug: " + check.getBody().toString());

            final JSONObject rootObject = jsonNode.getObject();
            final JSONObject stateObject = rootObject.getJSONObject("state");
            return BulbData.getBulb(stateObject);
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setBulb(BulbData bulb) {
        final JSONObject request = bulb.getJson();
        request.put("transitiontime", 5);

        try {
            final HttpResponse<String> check = Unirest.put(lightURL + "/state").body(request).asString();

            if (check.getStatus() != 200) {
                System.out.println("Could not set light status! Check address, token and light number.");
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("Set bulb status to: " + bulb);
    }

    public void submitCommand(Command command) {
        commandQueue.add(command);
    }

    private void runCommand(Command command) throws InterruptedException {
        final BulbData originalBulbData = getBulb();

        if (command instanceof FlashCommand) {
            final FlashCommand flashCommand = (FlashCommand) command;
            setBulb(flashCommand.getBulbData());
        }

        Thread.sleep(configuration.getHueOnTime());

        setBulb(originalBulbData);
    }

    @Override
    public void run() {
        while(true) {
            try {
                final Command command = commandQueue.poll(5, TimeUnit.SECONDS);

                if (command != null) {
                    runCommand(command);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
