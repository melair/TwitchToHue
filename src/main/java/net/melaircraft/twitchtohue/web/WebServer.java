package net.melaircraft.twitchtohue.web;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class WebServer extends Application {
    private HttpServer server;
    private final Set<Object> resources = new HashSet<>();

    public void start(int port) throws IOException {
        final URI base = getBaseURI(port);

        server = HttpServer.create(new InetSocketAddress(base.getPort()), 0);

        final HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(this, HttpHandler.class);
        server.createContext(base.getPath(), handler);

        server.start();
    }

    public void stop() {
        server.stop(0);
        server = null;
    }

    public void registerResource(Object object) {
        if (server != null) {
            throw new IllegalStateException("Can not add resource once server has started!");
        }

        resources.add(object);
    }

    private URI getBaseURI(int port) {
        return UriBuilder.fromUri("http://localhost/").port(port).build();
    }

    @Override
    public Set<Object> getSingletons() {
        return resources;
    }
}
