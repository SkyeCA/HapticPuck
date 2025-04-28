package ca.nhd.comm.interfaces;

import ca.nhd.comm.exceptions.CommError;
import ca.nhd.comm.models.DeviceCompatibility;

public interface IDeviceCommandsV1 {
    boolean vibrate(int step) throws CommError;
    int battery() throws CommError;
    boolean reset() throws CommError;
    String version() throws CommError;
    String copyright() throws CommError;
    String cat() throws CommError;
    DeviceCompatibility compatibility() throws CommError;
}
