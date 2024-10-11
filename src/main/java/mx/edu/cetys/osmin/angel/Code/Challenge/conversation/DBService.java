package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBService {
    private final JdbcTemplate jdbcTemplate;

    public DBService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUserMessage(String userInput) {
        String sql = "INSERT INTO user_messages (message) VALUES (?)";
        jdbcTemplate.update(sql, userInput);
    }
}
