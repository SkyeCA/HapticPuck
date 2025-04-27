package ca.nhd.osc;

import java.util.Date;
import java.util.List;

public class OSCMessageQueue {
    private final String queueName;
    private List<String> messages = List.of();
    private Date lastMessage;

    public OSCMessageQueue(String queueName){
        this.queueName = queueName;
    }

    public void addMessage(String message){
        this.messages.add(message);
        this.lastMessage = new Date();
    }

    public List<String> getMessages(){
        return this.messages;
    }

    public void clear(){
        this.messages.clear();
    }
}
