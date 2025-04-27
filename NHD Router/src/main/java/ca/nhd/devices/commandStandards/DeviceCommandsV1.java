package ca.nhd.devices.commandStandards;

import ca.nhd.comm.exceptions.CommError;

public interface DeviceCommandsV1 {
    boolean vibrate(int step) throws CommError;
    int battery() throws CommError;
    boolean reset() throws CommError;
    String version() throws CommError;
    String copyright() throws CommError;
    String cat() throws CommError;
}
