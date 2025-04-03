package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    // Constructor for Account Service without provided account
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    // Constructor with provided account
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        if(message.message_text.isEmpty()){
            return null;
        }
        else if(message.message_text.length() > 255){
            return null;
        }
        else if(!messageDAO.checkUser(message.getPosted_by())){
            return null;
        }
        else{
            return messageDAO.createMessage(message);
        }
    }

    // Gets all Messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // Gets a message by Id
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }
    
    // Deletes message
    public Message deleteMessage(int message_id){
        // Get message before deleting it to return it
        Message message  = this.getMessageById(message_id);
        messageDAO.deleteMessage(message_id);
        return message;
    }

    // Updates message
    public Message updateMessage(int message_id,String message_text){
        // Check if message is too long or blank
        if(message_text.length() > 255){
            return null;
        }
        else if(message_text.isBlank()){
            return null;
        }

        // Message won't update if there is no matching message_id
        messageDAO.updateMessage(message_id, message_text);

        // Get new message by message_id
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getAccountMessages(int account_id){
        return messageDAO.getAccountMessages(account_id);
    }
}
