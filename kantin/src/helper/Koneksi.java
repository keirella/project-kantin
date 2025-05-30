package helper;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Koneksi {
    static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setDatabaseName("kantin_db");
            dataSource.setUser("root");
            dataSource.setPassword("");

            try {
                conn = dataSource.getConnection();
                System.out.println("Koneksi Berhasil");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Koneksi Gagal: " + ex.getMessage());
            }
        }
        return conn;
    }
}