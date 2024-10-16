package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.chat.client.ChatClient;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ConversationServiceTest {

    @Mock
    private DBService dbService;

    @Mock
    private AIChatClientService aiChatClientService;

    @InjectMocks
    private ConversationService conversationService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Test salute")
    void testSalute(){
        when(aiChatClientService.salute()).thenReturn("¡Hola! Soy Auto, el timón de Wall-e.");

        String response = conversationService.salute();

        assertEquals("¡Hola! Soy Auto, el timón de Wall-e.", response);

        verify(aiChatClientService).salute();
    }

    @Test
    @DisplayName("Test Handle user message")
    void handleUserMessage(){
        String userInput = "Hola, ¿quién eres?";
        String expectedResponse = "Soy Auto, el timón de Wall-e.";

        doNothing().when(dbService).saveUserMessage(userInput);
        when(aiChatClientService.userMessageHandler(userInput)).thenReturn(expectedResponse);

        String response = conversationService.handleUserMessage(userInput);

        assertEquals(expectedResponse, response);

        verify(dbService).saveUserMessage(userInput);

        verify(aiChatClientService).userMessageHandler(userInput);

    }

}
