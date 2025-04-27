package ca.nhd.devices;

import ca.nhd.comm.exceptions.CommError;
import ca.nhd.comm.NhdHttpClient;
import ca.nhd.devices.commandStandards.DeviceCommandsV1;

public class NhdHapticDevice implements DeviceCommandsV1 {
    private final NhdHttpClient nhdHttpClient = new NhdHttpClient();
    private int port = 7593;
    private final String ip;
    private final String baseUrl;

    public NhdHapticDevice(String ip, int port){
        this.port = port;
        this.ip = ip;
        this.baseUrl = "http://" + this.ip + ":" + this.port + "/";
    }

    public NhdHapticDevice(String ip){
        this.ip = ip;
        this.baseUrl = "http://" + this.ip + ":" + this.port + "/";
    }

    public boolean vibrate(int step) throws CommError {
        String response = nhdHttpClient.httpGet(this.baseUrl + "vibrate?val=" + step);

        return response.equals("Ok");
    }

    public int battery() throws CommError {
        String response = nhdHttpClient.httpGet(this.baseUrl + "battery");

        return Integer.parseInt(response);
    }

    public boolean reset() throws CommError {
        String response = nhdHttpClient.httpGet(this.baseUrl + "reset");

        return response.equals("Ok");
    }

    public String version() throws CommError {
        return nhdHttpClient.httpGet(this.baseUrl + "version");
    }

    public String copyright() throws CommError {
        return nhdHttpClient.httpGet(this.baseUrl + "copyright");
    }

    public String cat() throws CommError {
        return nhdHttpClient.httpGet(this.baseUrl + "cat");
    }
}
