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
        ApplicationStateManager asm = new ApplicationStateManager();

        asm.findNetworkDevices();
        asm.createListenerForDevice(asm.getDevices().getFirst(), "/avatar/parameters/VFH/Zone/Touch/Headpats/Others");
        asm.oscStart();

        while(true){
            asm.loopDevices();
        }
    }
}
