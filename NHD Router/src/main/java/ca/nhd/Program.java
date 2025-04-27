package ca.nhd;

import ca.nhd.devices.NhdHapticDevice;
import ca.nhd.osc.OSCController;
import ca.nhd.osc.OSCMessageQueue;
import com.illposed.osc.*;

public class Program {
    public static void main(String... args) throws Exception {
        OSCController oscController = new OSCController(9001);
        OSCMessageQueue headpatMessageQueue = new OSCMessageQueue("headpatQueue");
        OSCMessageListener headpatListener = oscMessageEvent -> headpatMessageQueue.addMessage(oscMessageEvent.getMessage().toString());

        oscController.addListener("/avatar/parameters/VFH/Zone/Touch/Headpats/Others", headpatListener);
        oscController.startListening();

        NhdHapticDevice nhdHapticDevice = new NhdHapticDevice("192.168.0.232");
    }
}
