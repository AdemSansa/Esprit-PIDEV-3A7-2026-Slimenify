package Database;
import java.sql.*;
public class CheckSchema {
    public static void main(String[] args) {
        try {
            Connection conn = dbconnect.getInstance().getConnection();
            ResultSet rs = conn.createStatement().executeQuery("DESCRIBE blog");
            while(rs.next()) {
                System.out.println(rs.getString("Field") + " - " + rs.getString("Type"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
