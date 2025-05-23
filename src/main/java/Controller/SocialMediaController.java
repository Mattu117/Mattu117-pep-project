package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;

import java.net.ConnectException;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages",this::getAccountMessagesHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void postRegisterAccountHandler(Context context) throws JsonProcessingException{
  
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);

        // User Account Service registerAccount
        Account registeredAccount = accountService.registerAccount(account);

        // Determine wheter registered account was a success
        if(registeredAccount != null){
            context.json(mapper.writeValueAsString(registeredAccount));
        }
        else{
            context.status(400);
        }
        
    }

    private void postLoginHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);

        // User Account Service Login
        Account loginAccount = accountService.loginAccount(account);

        if(loginAccount != null){
            context.json(mapper.writeValueAsString(loginAccount));
        }
        else{
            context.status(401);
        }
    }

    // Posting messages
    private void postMessagesHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);

        Message messageCreated = messageService.createMessage(message);
        
        if(messageCreated != null){
            context.json(mapper.writeValueAsString(messageCreated));
        }
        else{
            context.status(400);
        }
    }

    // Gets all messages
    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    // Gets message by Id
    private void getMessageByIdHandler(Context context){
        Message message = messageService.getMessageById(Integer.valueOf(context.pathParam("message_id")));

        if(message != null){
            context.json(message);
        }
    }

    private void deleteMessageHandler(Context context){
        Message message = messageService.deleteMessage(Integer.valueOf(context.pathParam("message_id")));
        if(message != null){
            context.json(message);
        }
    }

    private void patchMessageByIdHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message_text = mapper.readValue(context.body(), Message.class);

        Message updatedMessage = messageService.updateMessage(Integer.valueOf(context.pathParam("message_id")),message_text.getMessage_text());
        
        if(updatedMessage != null){
            context.json(mapper.writeValueAsString(updatedMessage));
        }
        else{
            context.status(400);
        }
    }

    private void getAccountMessagesHandler(Context context){
        List<Message> messages = messageService.getAccountMessages(Integer.valueOf(context.pathParam("account_id")));
        context.json(messages);
    }


}