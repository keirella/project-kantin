package helper;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static MysqlDataSource dataSource;
    static{
        dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/kantin_db");
        dataSource.setUser("root");
        dataSource.setPassword("");
    }
     public static Connection getConnection() {
        try {
            return dataSource.getConnection(); // selalu koneksi baru
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}