package ca.nhd;

import ca.nhd.devices.NhdHapticDevice;
import com.illposed.osc.*;

public class Program {
    public static void main(String... args) throws Exception {
        OSCController oscController = new OSCController(9001);
        OSCMessageListener listener = oscMessageEvent -> System.out.println("Message received: " + oscMessageEvent.getMessage().getAddress());

        oscController.addListener("/message/receiving", listener);
        oscController.startListening();

        NhdHapticDevice nhdHapticDevice = new NhdHapticDevice("192.168.0.232");
    }
}
