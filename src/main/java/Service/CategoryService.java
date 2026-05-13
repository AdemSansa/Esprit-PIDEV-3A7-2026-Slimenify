package Service;

import Entities.Category;
import Database.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    Connection cnx;

    public CategoryService() {

        cnx = dbconnect
                .getInstance()
                .getConnection();
    }

    public List<Category> afficher() {

        List<Category> categories =
                new ArrayList<>();

        String sql =
                "SELECT * FROM category";

        try {

            Statement st =
                    cnx.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()) {

                Category c =
                        new Category(
                                rs.getInt("id"),
                                rs.getString("name")
                        );

                categories.add(c);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return categories;
    }
}