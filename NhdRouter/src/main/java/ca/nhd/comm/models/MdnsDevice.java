package ca.nhd.comm.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MdnsDevice {
    private String name;
    private String application;
    private String ipAddress;
    private int port;
    //private DeviceCompatibility deviceCompatibility;
}
