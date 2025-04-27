package ca.nhd.osc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OSCMessageQueue<T> {
    private final String queueName;
    private List<T> messages = new ArrayList<>();
    private Date lastMessage;

    public OSCMessageQueue(String queueName){
        this.queueName = queueName;
    }

    public void addMessage(T messageValue){
        this.messages.add(messageValue);
        this.lastMessage = new Date();
    }

    public List<T> getMessages(){
        return this.messages;
    }

    public void clear(){
        this.messages.clear();
    }
}
