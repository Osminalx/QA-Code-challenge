package mx.edu.cetys.osmin.angel.Code.Challenge.conversation;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


@Service
public class ConversationService {
    private final DBService dbService;
    private final AIChatClientService  aiChatClientService;


    public ConversationService(DBService dbService, AIChatClientService aiChatClientService) {

        this.dbService = dbService;
        this.aiChatClientService = aiChatClientService;
    }


    public String salute() {
        return aiChatClientService.salute();
    }

    public String handleUserMessage(String userInput) {
        dbService.saveUserMessage(userInput);

        return aiChatClientService.userMessageHandler(userInput);
    }


}
