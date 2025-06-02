package controller;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import view.LoginViews;
import view.PendaftaranViews;

import javax.swing.*;

public class PendaftaranController {
    private PendaftaranViews view;
    private UserDao dao;

    public PendaftaranController(PendaftaranViews view) {
        this.view = view;
        this.dao = new UserDaoImpl();
        initListener();
    }

    private void initListener() {
        view.getSignUp().addActionListener(e -> daftar());
    }

    private void daftar() {
        String username = view.getjText_Username().getText();
        String password = String.valueOf(view.getjText_Password().getText());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Semua field harus diisi.");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        boolean success = dao.insertUser(user);
        if (success) {
            JOptionPane.showMessageDialog(view, "Pendaftaran berhasil. Silakan login.");
            view.dispose();

            LoginViews loginViewBaru = new LoginViews();
            LoginController loginController = new LoginController(loginViewBaru); // inisialisasi controller baru
            loginViewBaru.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Pendaftaran gagal.");
        }
    }
}
