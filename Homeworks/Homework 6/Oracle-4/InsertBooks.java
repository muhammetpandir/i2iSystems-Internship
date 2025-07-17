import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Properties;

public class InsertBooks {

    private static final String URL = "jdbc:oracle:thin:@localhost:1522:xe";
    private static final String USER = "SYS";
    private static final String PASS = "ORACLE";

    public static void main(String[] args) {
        String sql = "INSERT INTO BOOK (NAME, ISBN) VALUES (?, ?)";

        try {
            Properties props = new Properties();
            props.put("user", USER);
            props.put("password", PASS);
            props.put("internal_logon", "sysdba");  // SYSDBA yetkisi için

            try (Connection conn = DriverManager.getConnection(URL, props);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                Random random = new Random();

                for (int i = 1; i <= 100; i++) {
                    String name = "Book " + i;
                    String isbn = "ISBN-" + (1000000 + random.nextInt(9000000));
                    pstmt.setString(1, name);
                    pstmt.setString(2, isbn);
                    pstmt.executeUpdate();
                    System.out.println("Inserted: " + name + ", " + isbn);
                }

                System.out.println("100 kitap başarıyla eklendi!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
