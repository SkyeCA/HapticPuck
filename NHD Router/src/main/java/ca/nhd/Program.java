package ca.nhd;

import com.illposed.osc.*;
import com.illposed.osc.messageselector.JavaRegexAddressMessageSelector;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.OSCPort;
import com.illposed.osc.transport.OSCPortIn;
import com.illposed.osc.transport.OSCPortInBuilder;

import java.io.IOException;

public class Program {
    public static void main(String... args) throws IOException {
        OSCPortIn receiver = new OSCPortIn(OSCPort.defaultSCOSCPort());
        OSCMessageListener listener = new OSCMessageListener() {
            @Override
            public void acceptMessage(OSCMessageEvent oscMessageEvent) {
                System.out.println("Message received: " + oscMessageEvent.getMessage().getAddress());
            }
        };

        // Listen to all
        // receiver.getDispatcher().addListener(new JavaRegexAddressMessageSelector(".*"), listener);
        // Listen for specific
        receiver.getDispatcher().addListener(new OSCPatternAddressMessageSelector("/message/receiving"), listener);
        receiver.startListening();
    }
}
