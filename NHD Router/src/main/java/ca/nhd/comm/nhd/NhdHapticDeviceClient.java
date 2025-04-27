package ca.nhd.comm.nhd;

import ca.nhd.comm.exceptions.CommError;
import ca.nhd.comm.inf.IDeviceCommandsV1;

public class NhdHapticDeviceClient implements IDeviceCommandsV1 {
    private final SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
    private int port = 7593;
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
}
