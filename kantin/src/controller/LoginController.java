package controller;

import com.sun.jdi.connect.spi.Connection;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import view.DashboardViews;
import view.LoginViews;

import javax.swing.*;

public class LoginController {
    private LoginViews view;
    private UserDao userDao;
    private Connection conn;

    public LoginController(LoginViews view) {
        this.view = view;
        this.userDao = new UserDaoImpl();
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(e -> login());
    }

    private void login() {
    String username = view.getUsername();
    String password = view.getPassword();

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);

    if (userDao.login(user)) {
        JOptionPane.showMessageDialog(view, "Login berhasil");
        new DashboardViews().setVisible(true);
        view.dispose();
    } else {
        JOptionPane.showMessageDialog(view, "Login gagal, coba lagi");
    }
}


}