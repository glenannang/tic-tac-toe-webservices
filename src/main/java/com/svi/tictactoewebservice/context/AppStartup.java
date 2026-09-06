package com.svi.tictactoewebservice.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.svi.tictactoewebservice.config.Config;
import java.io.IOException;
import java.io.InputStream;

@WebListener
public class AppStartup implements ServletContextListener {

    private static final String CONFIG_INI_LOCATION = "CONFIG_INI_LOCATION";

    private ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.context = sce.getServletContext();

        String configLocation = context.getInitParameter(CONFIG_INI_LOCATION);

        if (configLocation == null || configLocation.trim().isEmpty()) {
            throw new IllegalStateException("CONFIG_INI_LOCATION is not configured in web.xml");
        }

        // Config setup
        try (InputStream input = context.getResourceAsStream(configLocation)) {

            if (input == null) {
                throw new IllegalStateException(
                        "Configuration file not found: " + configLocation
                );
            }

            Config.load(input);
            System.out.println("Records directory: " + Config.get(Config.Keys.RECORDS_DIR.value()));

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load configuration file.",
                    e
            );
        }


        // Database initialization
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Close database resources
    }
}