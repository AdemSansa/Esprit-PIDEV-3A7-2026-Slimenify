package Database;

public class TestDB {
    public static void main(String[] args) {
        try {
            System.out.println("Starting DB connection test...");
            dbconnect db = dbconnect.getInstance();
            if (db.getConnection() != null) {
                System.out.println("SUCCESS: Connected to database!");
            } else {
                System.out.println("FAILURE: Connection is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
