package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ConversationServiceTest {

    @Mock
    private ChatClient autoWallE;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ConversationService conversationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSalute() {
        // Mock del comportamiento del cliente OpenAI
        ChatClient.ChatPrompt chatPrompt = mock(ChatClient.ChatPrompt.class);
        ChatClient.ChatResponse chatResponse = mock(ChatClient.ChatResponse.class);

        when(autoWallE.prompt()).thenReturn(chatPrompt);
        when(chatPrompt.user(anyString())).thenReturn(chatPrompt);
        when(chatPrompt.call()).thenReturn(chatResponse);
        when(chatResponse.content()).thenReturn("¡Hola, soy Auto de Wall-e!");

        // Ejecutar el método y verificar el resultado
        String result = conversationService.salute();
        assertEquals("¡Hola, soy Auto de Wall-e!", result);

        // Verificar que el método fue llamado con el prompt esperado
        verify(autoWallE).prompt();
        verify(chatPrompt).user("""
                Te llamas Auto, eres el timón IA de Wall-e.
                Saludame como este personaje.
                """);
        verify(chatPrompt).call();
    }

    @Test
    void testHandleUserMessage() {
        // Mock del comportamiento de insertar en la base de datos
        doNothing().when(jdbcTemplate).update(anyString(), anyString());

        // Mock del comportamiento del cliente OpenAI
        ChatClient.ChatPrompt chatPrompt = mock(ChatClient.ChatPrompt.class);
        ChatClient.ChatResponse chatResponse = mock(ChatClient.ChatResponse.class);

        when(autoWallE.prompt()).thenReturn(chatPrompt);
        when(chatPrompt.user(anyString())).thenReturn(chatPrompt);
        when(chatPrompt.call()).thenReturn(chatResponse);
        when(chatResponse.content()).thenReturn("Respuesta de Auto");

        // Ejecutar el método con un mensaje del usuario
        String userInput = "¿Qué opinas de Wall-e?";
        String result = conversationService.handleUserMessage(userInput);

        // Verificar que el mensaje se guardó en la base de datos
        verify(jdbcTemplate).update("INSERT INTO user_messages (message) VALUES (?)", userInput);

        // Verificar que el método fue llamado con el prompt esperado
        verify(autoWallE).prompt();
        verify(chatPrompt).user("""
                Te llamas Auto, eres el timón IA de Wall-e.
                Actúa como este personaje para responder:
                """ + userInput);
        verify(chatPrompt).call();

        // Verificar que la respuesta es la esperada
        assertEquals("Respuesta de Auto", result);
    }

}
