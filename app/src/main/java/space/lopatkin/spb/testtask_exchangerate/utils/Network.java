package space.lopatkin.spb.testtask_exchangerate.utils;

import android.net.Uri;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Network {
    private Client client = new Client();
    private XmlParser xmlParser = new XmlParser();
    private List listExchangeRates;


    //получение от сервера обьектов
    public List getExchangeRates(String http)
            throws XmlPullParserException, IOException {

        listExchangeRates = new ArrayList();
        InputStream stream = null;
        try {
            String url = Uri.parse(http)
                    .buildUpon()
                    //параметры запроса
                    //какой формат хотим получить
                    .appendQueryParameter("format", "xml")
                    .build().toString();

            stream = client.getResponse(url).body().byteStream();
            listExchangeRates = xmlParser.parse(stream);

        } catch (IOException ioe) {
            listExchangeRates = null;
//            Log.d(MainActivity.TAG, "ошибка загрузки данных", ioe);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return listExchangeRates;
    }
}
