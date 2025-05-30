package dao;

import java.util.List;
import model.Menu;

public interface MenuDao {
    List<Menu> getAll();
    List<Menu> searchByName(String keyword);
}