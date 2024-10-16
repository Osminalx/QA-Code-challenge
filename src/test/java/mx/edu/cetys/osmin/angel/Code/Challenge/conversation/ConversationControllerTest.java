package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConversationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ConversationService conversationService;

    @InjectMocks
    private ConverstionController conversationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(conversationController).build();
    }

    @Test
    void testSalute() throws Exception {
        // Simulamos la respuesta del servicio
        when(conversationService.salute()).thenReturn("Hola!");

        // Ejecutamos el endpoint GET /salute
        mockMvc.perform(get("/salute"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salute").value("Hola!"));
    }

    @Test
    void testTalkWithValidMessage() throws Exception {
        String validMessage = "Hola, ¿cómo estás?";
        String expectedResponse = "Estoy bien, gracias.";

        // Simulamos la respuesta del servicio
        when(conversationService.handleUserMessage(validMessage)).thenReturn(expectedResponse);

        // Ejecutamos el endpoint POST /talk
        mockMvc.perform(post("/talk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"" + validMessage + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(expectedResponse));
    }

    @Test
    void testTalkWithLongMessage() throws Exception {
        String longMessage = "Este mensaje es demasiado largo, tiene más de cincuenta caracteres, lo cual no es permitido.";

        // Ejecutamos el endpoint POST /talk
        mockMvc.perform(post("/talk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"" + longMessage + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("El mensaje no debe exceder los 50 caracteres."));
    }

}
