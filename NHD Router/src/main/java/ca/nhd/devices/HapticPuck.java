package ca.nhd.devices;

import ca.nhd.comm.nhd.NhdHapticDeviceClient;
import ca.nhd.devices.interfaces.IDevice;
import ca.nhd.osc.OSCMessageQueue;

import java.util.Date;

public class HapticPuck implements IDevice {
    @SuppressWarnings("FieldCanBeLocal")
    private final int SCALAR = 100;
    @SuppressWarnings("FieldCanBeLocal")
    private final int MIN_LEVEL = 0;
    private final OSCMessageQueue<Float> messageQueue;
    private final NhdHapticDeviceClient nhdHapticDeviceClient;
    private int currentStep = 0;
    private String deviceName = "Unnamed Device";

    public HapticPuck(String deviceName, NhdHapticDeviceClient nhdHapticDeviceClient, OSCMessageQueue<Float> messageQueue){
        this.deviceName = deviceName;
        this.nhdHapticDeviceClient = nhdHapticDeviceClient;
        this.messageQueue = messageQueue;
    }

    public HapticPuck(NhdHapticDeviceClient nhdHapticDeviceClient, OSCMessageQueue<Float> messageQueue){
        this.nhdHapticDeviceClient = nhdHapticDeviceClient;
        this.messageQueue = messageQueue;
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
}
