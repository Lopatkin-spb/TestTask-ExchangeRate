package space.lopatkin.spb.testtask_exchangerate.utils;

import android.util.Log;
import space.lopatkin.spb.testtask_exchangerate.utils.xvlConverter.Valute;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import static space.lopatkin.spb.testtask_exchangerate.MainActivity.TAG_MY_LOGS;

public class Calculator {

    public String calculate(List<Valute> listValute, int position, String userInputString) {

        int nominalValute =
                Integer.parseInt(listValute.get(position).getNominal());
        String stringValueValute = listValute.get(position).getValue().replace(",", ".");
        Double valueValute = convertToDouble(stringValueValute);
        Double userInput = convertToDouble(userInputString);
        Double result = (valueValute / nominalValute) * userInput;
        String stringResult = convertToString(result);
        return stringResult;
    }

    private Double convertToDouble(String stringNumber) {
        String pattern = "######.####";
        Double doubleNumber = null;
        try {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            doubleNumber = decimalFormat.parse(stringNumber).doubleValue();
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
            Log.d(TAG_MY_LOGS, "--------------- error");
        }
        return doubleNumber;
    }

    private String convertToString(Double doubleNumber) {
        String pattern = "######.####";
        String stringNumber = null;
        try {
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            stringNumber = decimalFormat.format(doubleNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.d(TAG_MY_LOGS, "--------------- error");
        }
        return stringNumber;
    }
}
