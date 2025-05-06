package ca.nhd.devices;

import ca.nhd.comm.nhd.NhdHapticDeviceClient;
import ca.nhd.devices.interfaces.IDevice;
import ca.nhd.osc.OSCMessageQueue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class HapticPuck implements IDevice {
    @SuppressWarnings("FieldCanBeLocal")
    private final int SCALAR = 100;
    @SuppressWarnings("FieldCanBeLocal")
    private final int MIN_LEVEL = 0;
    private final NhdHapticDeviceClient nhdHapticDeviceClient;
    @Setter
    @Getter
    private OSCMessageQueue<Float> messageQueue = new OSCMessageQueue<>();
    @Getter
    private String deviceName = "Unnamed Device";
    private int currentStep = 0;

    public HapticPuck(String deviceName, NhdHapticDeviceClient nhdHapticDeviceClient){
        this.deviceName = deviceName;
        this.nhdHapticDeviceClient = nhdHapticDeviceClient;
    }

    public HapticPuck(NhdHapticDeviceClient nhdHapticDeviceClient){
        this.nhdHapticDeviceClient = nhdHapticDeviceClient;
    }

    public void loop(){
        Date now = new Date();
        int newStep;

        if((now.getTime() - messageQueue.getLastMessageDate().getTime()) / 1000 < 400){
            return;
        }

        if(this.messageQueue.getMessageCount() == 0 || this.messageQueue.getMessageCount() < 10){
            newStep = 0;
        } else {
            float sum = 0;
            for (float d : this.messageQueue.getMessages()) {
                sum += ((d * SCALAR) / 5) + MIN_LEVEL;
            }
            newStep = (int) Math.floor(sum);
        }

        if(newStep != this.currentStep){
            System.out.println("Set step:" + newStep);
            this.nhdHapticDeviceClient.vibrate(newStep);
            this.currentStep = newStep;
        }

        this.messageQueue.clear();
    }

    @Override
    public String getName() {
        return this.deviceName;
    }

    @Override
    public String getIp() {
        return this.nhdHapticDeviceClient.getIp();
    }

    @Override
    public int getPort() {
        return this.nhdHapticDeviceClient.getPort();
    }
}
