package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIChatClientService {

    private final ChatClient autoWallE;

    public AIChatClientService(ChatClient.Builder autoWallE) {
        this.autoWallE = autoWallE.build();
    }

     private String PromptGen(ChatClient chatClient, String prompt) {
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    public String salute(){
        String prompt = """
                Te llamas Auto, eres el timón IA de Wall-e
                Saludame como este personaje
                """;

        return PromptGen(autoWallE,prompt);
    }


    public String userMessageHandler(String userInput){

        String prompt = """
                Te llamas Auto, eres el timón IA de Wall-e.
                Actúa como este personaje para responder:
                """ + userInput;
        return PromptGen(autoWallE,prompt);
    }
}
