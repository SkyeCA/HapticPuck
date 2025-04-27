package ca.nhd;

import com.illposed.osc.OSCMessageEvent;
import com.illposed.osc.OSCMessageListener;
import com.illposed.osc.messageselector.JavaRegexAddressMessageSelector;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.OSCPortIn;

import java.io.IOException;
import java.util.concurrent.Callable;

public class OSCController {
    private static final JavaRegexAddressMessageSelector GLOBAL_LISTENER_PATTERN = new JavaRegexAddressMessageSelector(".*");
    private final OSCPortIn receiver;

    public OSCController(int port) throws IOException {
        this.receiver = new OSCPortIn(port);
    }

    public void startListening(){
        this.receiver.startListening();
    }

    public void stopListening(){
        this.receiver.stopListening();
    }

    public void addListener(String selector, OSCMessageListener listener) {
        receiver.getDispatcher().addListener(new OSCPatternAddressMessageSelector(selector), listener);
    }

    public void addGlobalListener(OSCMessageListener listener) {
        receiver.getDispatcher().addListener(GLOBAL_LISTENER_PATTERN, listener);
    }
}
