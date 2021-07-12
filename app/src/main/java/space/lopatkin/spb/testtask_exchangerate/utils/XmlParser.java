package space.lopatkin.spb.testtask_exchangerate.utils;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import space.lopatkin.spb.testtask_exchangerate.model.Valute;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    public static final String nameSpaces = null;
    private static final String TAG_VALCURS = "ValCurs";
    private static final String TAG_DATE = "Date";
    private static final String TAG_VALUTE = "Valute";
    private static final String TAG_NUMCODE = "NumCode";
    private static final String TAG_CHARCODE = "CharCode";
    private static final String TAG_NOMINAL = "Nominal";
    private static final String TAG_NAME = "Name";
    private static final String TAG_VALUE = "Value";

    //екземпляр синтаксического анализатора
    public List parse(InputStream in)
            throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            return readFeed(parser);

        } finally {
            in.close();
        }
    }

    //отбор нужных главных тегов
    private List readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_VALCURS);

        String dateVal = getDateVal(parser);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TAG_VALUTE)) {
                entries.add(readEntry(parser,dateVal));
            } else {
                skip(parser);
            }
        }
        return entries;
    }


    private String getDateVal(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        String tag = parser.getName();
        String date=null;
        if (tag.equals(TAG_VALCURS)) {
            date = parser.getAttributeValue(nameSpaces, TAG_DATE);
        } else {
            skip(parser);
        }
        return date;
    }


    private Valute readEntry(XmlPullParser parser,String date)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_VALUTE);

        String numcodeVal = null;
        String charcodeVal = null;
        String nominalVal = null;
        String nameVal = null;
        String valueVal = null;
        String dateVal = date;


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            if (name.equals(TAG_NUMCODE)) {
                numcodeVal = readNumcodeVal(parser);
            } else if (name.equals(TAG_CHARCODE)) {
                charcodeVal = readCharcodeVal(parser);
            } else if (name.equals(TAG_NOMINAL)) {
                nominalVal = readNominalVal(parser);
            } else if (name.equals(TAG_NAME)) {
                nameVal = readNameVal(parser);
            } else if (name.equals(TAG_VALUE)) {
                valueVal = readValueVal(parser);
            } else {
                skip(parser);
            }
        }
        return new Valute(numcodeVal, charcodeVal, nominalVal, nameVal, valueVal, dateVal);
    }

    private String readNumcodeVal(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_NUMCODE);
        String numcodeVal = readResult(parser);
        parser.require(XmlPullParser.END_TAG, nameSpaces, TAG_NUMCODE);

        return numcodeVal;
    }


    private String readCharcodeVal(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_CHARCODE);
        String charcodeVal = readResult(parser);
        parser.require(XmlPullParser.END_TAG, nameSpaces, TAG_CHARCODE);

        return charcodeVal;
    }


    private String readNominalVal(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_NOMINAL);
        String nominalVal = readResult(parser);
        parser.require(XmlPullParser.END_TAG, nameSpaces, TAG_NOMINAL);

        return nominalVal;
    }


    private String readNameVal(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_NAME);
        String nameVal = readResult(parser);
        parser.require(XmlPullParser.END_TAG, nameSpaces, TAG_NAME);

        return nameVal;
    }


    private String readValueVal(XmlPullParser parser)
            throws XmlPullParserException, IOException {

        parser.require(XmlPullParser.START_TAG, nameSpaces, TAG_VALUE);
        String valueVal = readResult(parser);
        parser.require(XmlPullParser.END_TAG, nameSpaces, TAG_VALUE);

        return valueVal;
    }

    private String readResult(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
            }
        }
    }
}