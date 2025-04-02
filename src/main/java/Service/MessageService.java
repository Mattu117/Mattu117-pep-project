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

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
}
