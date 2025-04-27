package ca.nhd.comm;

import ca.nhd.comm.exceptions.CommError;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NhdHttpClient {
    private final OkHttpClient client = new OkHttpClient();

    public String httpGet(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body() != null ? response.body().string() : "null";
            }
        } catch (Exception e) {
            throw new CommError("Error occured when communicating with device.", e);
        }
    }
}
