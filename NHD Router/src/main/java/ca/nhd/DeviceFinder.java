package ca.nhd;

import ca.nhd.comm.mdns.MdnsDeviceFinder;
import ca.nhd.comm.models.MdnsDevice;
import jakarta.inject.Inject;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class DeviceFinder {


    public static void main(String... args) throws Exception {
        MdnsDeviceFinder x = new MdnsDeviceFinder();
        for(MdnsDevice i: x.find()){
            System.out.println(i.getName() + ":" + i.getIpAddress() + ":" + i.getApplication() + ":" + i.getPort());
        }

    }
}
