package space.lopatkin.spb.testtask_exchangerate.utils;

import android.net.Uri;
import android.util.Log;
import okhttp3.*;
import org.xmlpull.v1.XmlPullParserException;
import space.lopatkin.spb.testtask_exchangerate.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private OkHttpClient client;
    private XmlParser xmlParser = new XmlParser();
    private List listData;

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

    //получение от сервера обьектов
    public List getData(String http)
            throws XmlPullParserException, IOException {

        listData = new ArrayList();
        InputStream stream = null;
        try {
            String url = Uri.parse(http)
                    .buildUpon()
                    //параметры запроса
                    //какой формат хотим получить
                    .appendQueryParameter("format", "xml")
                    .build().toString();

            stream = getResponse(url).body().byteStream();
            listData = xmlParser.parse(stream);

        } catch (IOException ioe) {
            listData = null;
//            Log.d(MainActivity.TAG, "ошибка загрузки данных", ioe);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return listData;
    }
}
