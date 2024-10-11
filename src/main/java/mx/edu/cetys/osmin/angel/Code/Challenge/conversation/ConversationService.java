package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ConversationService {


    private final ChatClient autoWallE;
    private final DBService dbService;


    public ConversationService(ChatClient.Builder autoWallE, DBService dbService) {
        this.autoWallE = autoWallE.build();
        this.dbService = dbService;
    }

    public String salute (){
        var prompt = """
                Te llamas Auto, eres el timón IA de Wall-e
                Saludame como este personaje
                """;
        var response = this.autoWallE
                .prompt()
                .user(prompt)
                .call()
                .content();

        return response;
    }

    public String handleUserMessage(String userInput) {

        // Guardar el input del usuario en la DB
        dbService.saveUserMessage(userInput);

        // Prompt para asegurar que siempre actúe como Auto, el timón IA de Wall-E
        var prompt = """
                Te llamas Auto, eres el timón IA de Wall-e.
                Actúa como este personaje para responder:
                """ + userInput;

        // Llama al cliente de OpenAI para obtener la respuesta
        var response = this.autoWallE
                .prompt()
                .user(prompt)
                .call()
                .content();

        return response;
    }


}
