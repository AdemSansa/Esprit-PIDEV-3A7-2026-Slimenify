package Service;

import Entities.Blog;
import Entities.Category;
import Database.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogService {

    Connection cnx;

    public BlogService() {

        cnx = dbconnect
                .getInstance()
                .getConnection();
        try {
            // Attempt to add therapist_id column just in case it doesn't exist
            cnx.createStatement().executeUpdate("ALTER TABLE blog ADD COLUMN therapist_id INT");
        } catch (SQLException e) {
            // Ignore if column already exists
        }
    }

    // ✅ AJOUTER BLOG

    public void ajouter(Blog b) {

        String sql =
                "INSERT INTO blog(title, content, photo, created_at, category_id, therapist_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setString(1, b.getTitle());

            ps.setString(2, b.getContent());

            ps.setString(3, b.getPhoto());

            ps.setDate(4, b.getCreatedAt());

            ps.setInt(5,
                    b.getCategory().getId()
            );

            ps.setInt(6, b.getAuthorId());

            ps.executeUpdate();

            System.out.println("✅ Blog ajouté");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ✅ AFFICHER BLOGS

    public List<Blog> afficher() {

        List<Blog> blogs =
                new ArrayList<>();

        String sql =
                "SELECT b.*, c.name " +
                        "FROM blog b " +
                        "JOIN category c " +
                        "ON b.category_id = c.id";

        try {

            Statement st =
                    cnx.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()) {

                Category c =
                        new Category(
                                rs.getInt("category_id"),
                                rs.getString("name")
                        );

                Blog b =
                        new Blog(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getString("photo"),
                                rs.getDate("created_at"),
                                c,
                                rs.getInt("therapist_id")
                        );

                blogs.add(b);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return blogs;
    }

    // ✅ SUPPRIMER BLOG

    public void supprimer(int id) {

        String sql =
                "DELETE FROM blog WHERE id=?";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("✅ Blog supprimé");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ✅ MODIFIER BLOG

    public void modifier(Blog b) {

        String sql =
                "UPDATE blog " +
                        "SET title=?, content=?, photo=?, category_id=? " +
                        "WHERE id=?";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setString(1,
                    b.getTitle());

            ps.setString(2,
                    b.getContent());

            ps.setString(3,
                    b.getPhoto());

            ps.setInt(4,
                    b.getCategory().getId());

            ps.setInt(5,
                    b.getId());

            ps.executeUpdate();

            System.out.println("✅ Blog modifié");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ✅ RECHERCHE

    public List<Blog> rechercher(String mot) {

        List<Blog> blogs =
                new ArrayList<>();

        String sql =
                "SELECT b.*, c.name " +
                        "FROM blog b " +
                        "JOIN category c " +
                        "ON b.category_id = c.id " +
                        "WHERE title LIKE ?";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setString(1,
                    "%" + mot + "%");

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                Category c =
                        new Category(
                                rs.getInt("category_id"),
                                rs.getString("name")
                        );

                Blog b =
                        new Blog(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("content"),
                                rs.getString("photo"),
                                rs.getDate("created_at"),
                                c,
                                rs.getInt("therapist_id")
                        );

                blogs.add(b);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return blogs;
    }
}