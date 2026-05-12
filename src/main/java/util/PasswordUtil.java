package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // Hash password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Verify password (for login later)
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }

        // Symfony uses $2y$ for bcrypt, but jBcrypt 0.4 expects $2a$
        // Both are functionally equivalent, so we can safely swap the prefix
        if (hashedPassword.startsWith("$2y$")) {
            hashedPassword = "$2a$" + hashedPassword.substring(4);
        }

        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.err.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }
}
