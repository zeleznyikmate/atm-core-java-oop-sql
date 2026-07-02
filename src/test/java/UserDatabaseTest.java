import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

class UserDatabaseTest {

    private UserDatabase db;

    @BeforeEach
    public void setUp() throws SQLException {
        String testDbUrl = "jdbc:h2:mem:db_user_test;DB_CLOSE_DELAY=-1";
        try (Connection conn = DriverManager.getConnection(testDbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS cards");
        }

        db = new UserDatabase(testDbUrl);
    }

    @Test
    public void should_ReturnTrue_When_CardAndPinMatchValidRecord() {
        boolean result = db.isValidCard("12345678", "1111");
        assertTrue(result);
    }

    @Test
    public void should_ReturnFalse_When_PinIsIncorrect() {
        boolean result = db.isValidCard("12345678", "9999");
        assertFalse(result);
    }

    @Test
    public void should_ReturnFalse_When_CardDoesNotExist() {
        boolean result = db.isValidCard("99999999", "1111");
        assertFalse(result);
    }
}