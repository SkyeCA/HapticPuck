package ca.nhd;

import ca.nhd.comm.mdns.MdnsDeviceFinder;
import ca.nhd.comm.models.MdnsDevice;
import ca.nhd.comm.nhd.NhdHapticDeviceClient;
import ca.nhd.devices.HapticPuck;
import ca.nhd.devices.interfaces.IDevice;
import ca.nhd.osc.OSCController;
import ca.nhd.osc.OSCMessageQueue;
import com.illposed.osc.*;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String... args) throws Exception {
        MdnsDeviceFinder mdnsDeviceFinder = new MdnsDeviceFinder();
        OSCController oscController = new OSCController(9001);

        List<MdnsDevice> availableDevices = mdnsDeviceFinder.find();
        List<IDevice> devices = new ArrayList<>();
        OSCMessageQueue<Float> headpatMessageQueue = new OSCMessageQueue<>("headpatQueue");

        for(MdnsDevice availableDevice:availableDevices){
            if(availableDevice.getApplication().equals("nhd")) {
                NhdHapticDeviceClient nhdHapticDeviceClient = new NhdHapticDeviceClient(availableDevice.getIpAddress(), availableDevice.getPort());

                if (availableDevice.getName().contains("hapticpuck-0001")) {
                    devices.add(new HapticPuck(nhdHapticDeviceClient, headpatMessageQueue));
                }
            }
        }

        OSCMessageListener headpatListener = oscMessageEvent -> headpatMessageQueue.addMessage((Float) oscMessageEvent.getMessage().getArguments().getFirst());

        oscController.addListener("/avatar/parameters/VFH/Zone/Touch/Headpats/Others", headpatListener);
        oscController.startListening();

        while(true){
            for(IDevice device : devices){
                device.loop();
            }
        }
    }
}
