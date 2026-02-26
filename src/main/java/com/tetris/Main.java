package com.tetris;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main extends JFinalConfig {

    @Override
    public void configConstant(Constants constants) {
        constants.setDevMode(true);
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add("/", GameController.class);
    }

    @Override
    public void configEngine(com.jfinal.template.Engine engine) {
        // no template engine required for this project
    }

    @Override
    public void configPlugin(Plugins plugins) {
        // no plugin required for this project
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        // no global interceptors required
    }

    @Override
    public void configHandler(Handlers handlers) {
        // no global handlers required
    }

    public static void main(String[] args) {
        JFinal.start(resolveWebAppDir(), 8080, "/", 5);
    }

    private static String resolveWebAppDir() {
        Path devWebapp = Paths.get("src/main/webapp");
        if (Files.exists(devWebapp.resolve("index.html"))) {
            return devWebapp.toString();
        }

        try {
            Path tempWebApp = Files.createTempDirectory("jfinal-tetris-webapp");
            tempWebApp.toFile().deleteOnExit();
            copyResourceToPath("/webapp/index.html", tempWebApp.resolve("index.html"));
            return tempWebApp.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to prepare web resources", e);
        }
    }

    private static void copyResourceToPath(String resourcePath, Path target) throws IOException {
        try (InputStream in = Main.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Missing resource: " + resourcePath);
            }
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            target.toFile().deleteOnExit();
        }
    }
}
