package ca.nhd;

import ca.nhd.comm.nhd.NhdHapticDeviceClient;
import ca.nhd.devices.HapticPuck;
import ca.nhd.devices.IDevice;
import ca.nhd.osc.OSCController;
import ca.nhd.osc.OSCMessageQueue;
import com.illposed.osc.*;

import java.util.List;

public class Program {
    public static void main(String... args) throws Exception {
        OSCController oscController = new OSCController(9001);
        OSCMessageQueue<Float> headpatMessageQueue = new OSCMessageQueue<>("headpatQueue");
        OSCMessageListener headpatListener = oscMessageEvent -> headpatMessageQueue.addMessage((Float) oscMessageEvent.getMessage().getArguments().getFirst());

        oscController.addListener("/avatar/parameters/VFH/Zone/Touch/Headpats/Others", headpatListener);
        oscController.startListening();

        NhdHapticDeviceClient nhdHapticDeviceClient = new NhdHapticDeviceClient("192.168.0.232");
        HapticPuck hapticPuck = new HapticPuck(nhdHapticDeviceClient, headpatMessageQueue);
        List<IDevice> devices = List.of(hapticPuck);

        while(true){
            for(IDevice device : devices){
                device.loop();
            }
        }
    }
}
