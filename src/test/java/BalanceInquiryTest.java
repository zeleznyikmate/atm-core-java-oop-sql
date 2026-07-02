import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

class BalanceInquiryTest {

    private final String testDbUrl = "jdbc:h2:mem:db_balance_test;DB_CLOSE_DELAY=-1";
    private UserDatabase db;
    private BalanceInquiry balanceInquiry;

    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS cards");
        }

        db = new UserDatabase(testDbUrl);
        balanceInquiry = new BalanceInquiry(db);
    }

    @Test
    public void should_ReturnCorrectBalance_ForValidCard() {
        balanceInquiry.execute("87654321");

        double balance = db.getBalance("87654321");
        assertEquals(12500.0, balance);
    }
}