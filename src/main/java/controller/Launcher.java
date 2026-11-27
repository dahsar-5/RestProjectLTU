package controller;

import REST.CanvasRestAPI;
import REST.LadokRestAPI;
import REST.EpokRestAPI;
import REST.ITSAdminRestAPI;

import javafx.application.Application;

public class Launcher {

    public static void main(String[] args) {

        // Start REST services
        CanvasRestAPI.main(args);
        LadokRestAPI.main(args);
        EpokRestAPI.main(args);
        ITSAdminRestAPI.main(args);

        // Start JavaFX GUI
        Application.launch(MainView.class, args);
    }
}