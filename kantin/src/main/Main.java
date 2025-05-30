package main;

import controller.LoginController;
import view.LoginViews;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginViews loginViews = new LoginViews();
            new LoginController(loginViews);
            loginViews.setVisible(true);
        });
    }
}