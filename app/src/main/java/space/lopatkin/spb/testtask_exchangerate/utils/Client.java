package space.lopatkin.spb.testtask_exchangerate.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Client {
    private OkHttpClient client;

    //взаимодействие с сервером
    public Response getResponse(String UrlSpec) throws IOException {
        client = new OkHttpClient();
        //zapros
        Request request = new Request.Builder()
                .url(UrlSpec)
                .build();
        //otvet
        Response response = client.newCall(request).execute();
        return response;
    }
}
