package ca.nhd;

import ca.nhd.comm.mdns.MdnsDeviceFinder;
import ca.nhd.comm.models.MdnsDevice;
import ca.nhd.comm.nhd.NhdHapticDeviceClient;
import ca.nhd.devices.HapticPuck;
import ca.nhd.devices.interfaces.IDevice;
import ca.nhd.osc.OSCController;
import ca.nhd.osc.OSCMessageQueue;
import com.illposed.osc.OSCMessageListener;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationStateManager {
    private final MdnsDeviceFinder mdnsDeviceFinder = new MdnsDeviceFinder();
    private final OSCController oscController = new OSCController(9001);
    @Getter
    private final List<IDevice> devices = new ArrayList<>();

    public ApplicationStateManager() throws IOException {

    }

    public void oscStart(){
        this.oscController.startListening();
    }

    public void oscStop(){
        this.oscController.stopListening();
    }

    public void findNetworkDevices() {
        List<MdnsDevice> availableDevices = mdnsDeviceFinder.find();

        for(MdnsDevice availableDevice:availableDevices){
            if(availableDevice.getApplication().equals("nhd")) {
                NhdHapticDeviceClient nhdHapticDeviceClient = new NhdHapticDeviceClient(availableDevice.getIpAddress(), availableDevice.getPort());

                if (availableDevice.getName().contains("hapticpuck-0001")) {
                    devices.add(new HapticPuck(nhdHapticDeviceClient));
                }
            }
        }
    }

    public void createListenerForDevice(IDevice device, String oscParameter){
        OSCMessageListener listener = oscMessageEvent -> device.getMessageQueue().addMessage((Float) oscMessageEvent.getMessage().getArguments().getFirst());
        this.oscController.addListener(oscParameter, listener);
    }

    public void loopDevices(){
        for(IDevice device : this.devices){
            device.loop();
        }
    }
}
