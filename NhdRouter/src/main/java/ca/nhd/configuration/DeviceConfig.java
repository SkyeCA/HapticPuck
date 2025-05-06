package ca.nhd.configuration;

public class DeviceConfig {
    public static void loadDevices(){
        new ObjectMapper().readValue(new ClassPathResource("./defaults/myjson.json").getFile(), Pojo.class);
    }
}
