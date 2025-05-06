package ca.nhd.comm.mdns;

import ca.nhd.comm.models.MdnsDevice;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MdnsDeviceFinder {
    private final JmDNS mdns;

    public MdnsDeviceFinder() throws IOException {
        this.mdns = JmDNS.create();
    }

    public List<MdnsDevice> find() {
        System.out.println("Looking for devices via MDNS.");
        List<MdnsDevice> devices = new ArrayList<>();
        ServiceInfo[] networkDevices = mdns.list("_nhd._tcp.local.");

        for (ServiceInfo networkDevice : networkDevices) {
            devices.add(MdnsDevice.builder()
                    .name(networkDevice.getName())
                    .port(networkDevice.getPort())
                    .ipAddress(networkDevice.getInet4Address().getHostAddress())
                    .application(networkDevice.getApplication())
                    .build()
            );
        }

        System.out.println("Found " + devices.size() + " haptic device(s) via MDNS.");
        return devices;
    }
}
