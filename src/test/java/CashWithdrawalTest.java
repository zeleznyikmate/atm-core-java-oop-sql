import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

class CashWithdrawalTest {

    private UserDatabase db;
    private CashWithdrawal cashWithdrawal;

    @BeforeEach
    public void setUp() throws SQLException {
        String testDbUrl = "jdbc:h2:mem:db_withdrawal_test;DB_CLOSE_DELAY=-1";
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS cards");
        }

        db = new UserDatabase(testDbUrl);
        cashWithdrawal = new CashWithdrawal(db);

    }

    @Test
    public void should_DecreaseBalance_When_WithdrawalIsSuccessful() {
        cashWithdrawal.execute("87654321", 2500.0);

        assertEquals(10000.0, db.getBalance("87654321"));
    }

    @Test
    public void should_NotModifyBalance_When_InsufficientFunds() {
        cashWithdrawal.execute("87654321", 20000.0);

        assertEquals(12500.0, db.getBalance("87654321"));
    }
}