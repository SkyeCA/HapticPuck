package ca.nhd.comm.nhd;

import ca.nhd.comm.exceptions.CommError;
import ca.nhd.comm.interfaces.IDeviceCommandsV1;
import ca.nhd.comm.models.DeviceCompatibility;
import lombok.Getter;

public class NhdHapticDeviceClient implements IDeviceCommandsV1 {
    private final SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
    @Getter
    private int port = 7593;
    @Getter
    private final String ip;
    private final String baseUrl;

    public NhdHapticDeviceClient(String ip, int port){
        this.port = port;
        this.ip = ip;
        this.baseUrl = "http://" + this.ip + ":" + this.port + "/";
    }

    public NhdHapticDeviceClient(String ip){
        this.ip = ip;
        this.baseUrl = "http://" + this.ip + ":" + this.port + "/";
    }

    public boolean vibrate(int step) throws CommError {
        String response = simpleHttpClient.httpGet(this.baseUrl + "vibrate?val=" + step);

        return response.equals("Ok");
    }

    public int battery() throws CommError {
        String response = simpleHttpClient.httpGet(this.baseUrl + "battery");

        return Integer.parseInt(response);
    }

    public boolean reset() throws CommError {
        String response = simpleHttpClient.httpGet(this.baseUrl + "reset");

        return response.equals("Ok");
    }

    public String version() throws CommError {
        return simpleHttpClient.httpGet(this.baseUrl + "version");
    }

    public String copyright() throws CommError {
        return simpleHttpClient.httpGet(this.baseUrl + "copyright");
    }

    public String cat() throws CommError {
        return simpleHttpClient.httpGet(this.baseUrl + "cat");
    }

    public DeviceCompatibility compatibility() throws CommError {
        String response = simpleHttpClient.httpGet(this.baseUrl + "compatibility");
        String[] responseParts = response.split("/");
        return new DeviceCompatibility(responseParts[0], responseParts[1]);
    }
}
