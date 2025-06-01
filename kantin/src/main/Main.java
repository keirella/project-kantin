package main;

import controller.LoginController;
import helper.Koneksi;
import view.LoginViews;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = Koneksi.getConnection();
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginViews loginViews = new LoginViews();
            new LoginController(loginViews);
            loginViews.setVisible(true);
        });
    }
}
