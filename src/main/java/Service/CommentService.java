package Service;

import Entities.Blog;
import Entities.Comment;
import Entities.Category;
import Database.dbconnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentService {

    Connection cnx;

    public CommentService() {

        cnx = dbconnect
                .getInstance()
                .getConnection();
    }

    // ✅ AJOUTER

    public void ajouter(Comment c) {

        String sql =
                "INSERT INTO comment(content, created_at, rating, blog_id) " +
                        "VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setString(1,
                    c.getContent());

            ps.setDate(2,
                    c.getCreatedAt());

            ps.setInt(3,
                    c.getRating());

            ps.setInt(4,
                    c.getBlog().getId());

            ps.executeUpdate();

            System.out.println("✅ Comment ajouté");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ✅ AFFICHER

    public List<Comment> afficher() {

        List<Comment> comments =
                new ArrayList<>();

        String sql =
                "SELECT c.*, b.title " +
                        "FROM comment c " +
                        "JOIN blog b " +
                        "ON c.blog_id = b.id";

        try {

            Statement st =
                    cnx.createStatement();

            ResultSet rs =
                    st.executeQuery(sql);

            while(rs.next()) {

                Blog b =
                        new Blog();

                b.setId(rs.getInt("blog_id"));

                b.setTitle(
                        rs.getString("title")
                );

                Comment c =
                        new Comment(
                                rs.getInt("id"),
                                rs.getString("content"),
                                rs.getDate("created_at"),
                                rs.getInt("rating"),
                                b
                        );

                comments.add(c);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return comments;
    }

    // ✅ AFFICHER PAR BLOG ID
    public List<Comment> getCommentsByBlogId(int blogId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comment WHERE blog_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setContent(rs.getString("content"));
                c.setCreatedAt(rs.getDate("created_at"));
                c.setRating(rs.getInt("rating"));
                Blog b = new Blog();
                b.setId(rs.getInt("blog_id"));
                c.setBlog(b);
                comments.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comments;
    }

    // ✅ SUPPRIMER

    public void supprimer(int id) {

        String sql =
                "DELETE FROM comment WHERE id=?";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("✅ Comment supprimé");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ✅ MODIFIER

    public void modifier(Comment c) {

        String sql =
                "UPDATE comment " +
                        "SET content=?, rating=? " +
                        "WHERE id=?";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setString(1,
                    c.getContent());

            ps.setInt(2,
                    c.getRating());

            ps.setInt(3,
                    c.getId());

            ps.executeUpdate();

            System.out.println("✅ Comment modifié");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
    }

    // ✅ RECHERCHE

    public List<Comment> rechercher(String mot) {

        List<Comment> comments =
                new ArrayList<>();

        String sql =
                "SELECT c.*, b.title " +
                        "FROM comment c " +
                        "JOIN blog b " +
                        "ON c.blog_id = b.id " +
                        "WHERE content LIKE ?";

        try {

            PreparedStatement ps =
                    cnx.prepareStatement(sql);

            ps.setString(1,
                    "%" + mot + "%");

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                Blog b =
                        new Blog();

                b.setId(rs.getInt("blog_id"));

                b.setTitle(
                        rs.getString("title")
                );

                Comment c =
                        new Comment(
                                rs.getInt("id"),
                                rs.getString("content"),
                                rs.getDate("created_at"),
                                rs.getInt("rating"),
                                b
                        );

                comments.add(c);
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return comments;
    }
}