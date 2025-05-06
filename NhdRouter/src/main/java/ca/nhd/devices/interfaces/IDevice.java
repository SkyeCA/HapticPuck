package ca.nhd.devices.interfaces;

import ca.nhd.osc.OSCMessageQueue;

public interface IDevice {
    void loop();
    String getName();
    String getIp();
    int getPort();
    OSCMessageQueue<Float> getMessageQueue();
}
