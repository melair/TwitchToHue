package net.melaircraft.twitchtohue;

import net.melaircraft.twitchtohue.configuration.Configuration;
import net.melaircraft.twitchtohue.hue.Controller;
import net.melaircraft.twitchtohue.web.NotificationEndPoint;
import net.melaircraft.twitchtohue.web.WebServer;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws InterruptedException, IOException {
        Configuration configuration = new Configuration();

        Controller controller = new Controller(configuration);
        Thread controllerThread = new Thread(controller);
        controllerThread.setDaemon(true);
        controllerThread.start();

        WebServer webServer = new WebServer();
        webServer.registerResource(new NotificationEndPoint(controller));

        webServer.start(configuration.getHttpPort());
    }
}
