package ca.nhd.comm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeviceCompatibility {
    private String deviceModel;
    private String protocol;
}
