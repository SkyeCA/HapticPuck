package ca.nhd;

import ca.nhd.comm.nhd.NhdHapticDeviceClient;
import ca.nhd.osc.OSCController;
import ca.nhd.osc.OSCMessageQueue;
import com.illposed.osc.*;

public class Program {
    public static void main(String... args) throws Exception {
        OSCController oscController = new OSCController(9001);
        OSCMessageQueue<Float> headpatMessageQueue = new OSCMessageQueue<>("headpatQueue");
        OSCMessageListener headpatListener = oscMessageEvent -> headpatMessageQueue.addMessage((Float) oscMessageEvent.getMessage().getArguments().getFirst());

        oscController.addListener("/avatar/parameters/VFH/Zone/Touch/Headpats/Others", headpatListener);
        oscController.startListening();

        NhdHapticDeviceClient nhdHapticDeviceClient = new NhdHapticDeviceClient("192.168.0.232");

        while(true){
            if(!headpatMessageQueue.getMessages().isEmpty()){
                System.out.println(headpatMessageQueue.getMessages().getFirst());
            }
        }
    }
}
