package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConverstionController {


    private final ConversationService conversationService;

    public ConverstionController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/salute")
    public Map<String, String> salute() {
        return Map.of("salute", conversationService.salute());
    }


    @PostMapping("/talk")
    public Map<String, String> talk(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");

        // Verifica si el mensaje excede los 50 caracteres
        if (userInput == null || userInput.length() > 50) {
            return Map.of("error", "El mensaje no debe exceder los 50 caracteres.");
        }

        // Usa el servicio para manejar la lógica de negocio
        String response = conversationService.handleUserMessage(userInput);
        return Map.of("response", response);
    }


}
