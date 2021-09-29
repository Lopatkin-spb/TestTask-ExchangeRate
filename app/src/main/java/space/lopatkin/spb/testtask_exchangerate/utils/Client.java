package space.lopatkin.spb.testtask_exchangerate.utils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Client {

    public static final String SERVER_URL = "https://www.cbr.ru/";
    public static final String SERVER_URL_TEST = "https://www.w3schools.com";
    public static final String SERVER_URL_TEST_2 = "https://gorest.co.in";

    //    https://gorest.co.in/public/v1/users/123/todos.xml
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private static OkHttpClient getOkHttp() {
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    //нужен для интерцепторов
                    .client(getOkHttp()) //внутрь клиент окххтп3
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
//        Log.d(TAG, "--------------client getRetrofit end");
        return retrofit;
    }

    private static CbrApi api;

    public static CbrApi getApiService() {
        if (api == null) {
            api = getRetrofit().create(CbrApi.class);
        }
        return api;
    }


}
