package com.tetris;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

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
        JFinal.start("src/main/resources/webapp", 8080, "/", 5, Main.class);
    }
}
