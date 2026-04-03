package tech.qmates.stringcalculator;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;

public class Application {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new HealthServlet()), "/health");

        server.setHandler(context);

        server.start();
        System.out.println("Server started on http://localhost:8080");
        server.join();
    }
}
