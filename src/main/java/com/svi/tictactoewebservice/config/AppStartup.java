package com.svi.tictactoewebservice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppStartup implements ServletContextListener {

    private static final String CONFIG_INI_LOCATION = "CONFIG_INI_LOCATION";

    private ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.context = sce.getServletContext();

        String configLocation =
                context.getInitParameter(CONFIG_INI_LOCATION);

        if (configLocation == null || configLocation.trim().isEmpty()) {
            throw new IllegalStateException(
                    "CONFIG_INI_LOCATION is not configured in web.xml"
            );
        }

        // Setup config
        // Database initialization
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close database resources
    }
}