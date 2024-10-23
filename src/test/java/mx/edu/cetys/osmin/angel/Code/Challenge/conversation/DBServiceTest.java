package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.verify;

public class DBServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private DBService dbService;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUserMessage() {
        // Dado
        String userInput = "Hello World";

        // Cuando
        dbService.saveUserMessage(userInput);

        // Entonces
        verify(jdbcTemplate).update("INSERT INTO user_messages (message) VALUES (?)", userInput);
    }
}