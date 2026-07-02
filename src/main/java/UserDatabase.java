import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase {
    private final String dbUrl;

    public UserDatabase() {
        this.dbUrl = "jdbc:h2:mem:atm_db;DB_CLOSE_DELAY=-1";
        initializeDatabase();
    }

    public UserDatabase(String alternativeDbUrl) {
        this.dbUrl = alternativeDbUrl;
        initializeDatabase();
    }

    private void initializeDatabase() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS cards (" +
                "card_number VARCHAR(20) PRIMARY KEY, " +
                "pin_code VARCHAR(4), " +
                "balance DOUBLE)";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSql);

            String countSql = "SELECT COUNT(*) FROM cards";
            try (ResultSet rs = stmt.executeQuery(countSql)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.execute("INSERT INTO cards VALUES ('12345678', '1111', 0.0)");
                    stmt.execute("INSERT INTO cards VALUES ('87654321', '2222', 12500.0)");
                    stmt.execute("INSERT INTO cards VALUES ('11112222', '3333', 0.0)");
                }
            }
        } catch (SQLException e) {
            System.err.println("Hiba az adatbázis inicializálásakor: " + e.getMessage());
        }
    }

    public boolean isValidCard(String cardNumber, String pinCode) {
        String sql = "SELECT * FROM cards WHERE card_number = ? AND pin_code = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);
            pstmt.setString(2, pinCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Ha van találat, igazat ad vissza
            }
        } catch (SQLException e) {
            System.err.println("Hiba a kártya ellenőrzésekor: " + e.getMessage());
        }
        return false;
    }

    public double getBalance(String cardNumber) {
        String sql = "SELECT balance FROM cards WHERE card_number = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            System.err.println("Hiba az egyenleg lekérdezésekor: " + e.getMessage());
        }
        return 0.0;
    }

    public boolean updateBalance(String cardNumber, double newBalance) {
        String sql = "UPDATE cards SET balance = ? WHERE card_number = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, cardNumber);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Ha sikerült frissíteni a sort, igazat ad vissza
        } catch (SQLException e) {
            System.err.println("Hiba az egyenleg frissítésekor: " + e.getMessage());
        }
        return false;
    }
}