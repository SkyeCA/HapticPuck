package ca.nhd.osc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OSCMessageQueue<T> {
    private List<T> messages = new ArrayList<>();
    private Date lastMessage = new Date();

    public void addMessage(T messageValue){
        this.messages.add(messageValue);
        this.lastMessage = new Date();
    }

    public List<T> getMessages(){
        return this.messages;
    }

    public int getMessageCount(){
        return this.messages.size();
    }

    public Date getLastMessageDate(){
        return this.lastMessage;
    }

    public void clear(){
        this.messages.clear();
    }
}
