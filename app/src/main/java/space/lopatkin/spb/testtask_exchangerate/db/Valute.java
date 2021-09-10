package space.lopatkin.spb.testtask_exchangerate.db;


import org.simpleframework.xml.*;

@Root(name = "Valute",strict = false)
public class Valute {

    @Element(name = "NumCode")
    private String numCode;
    @Element(name = "CharCode")
    private String charCode;
    @Element(name = "Nominal")
    private String nominal;
    @Element(name = "Name")
    private String name;
    @Element(name = "Value")
    private String value;
    private String date;


    public Valute() {
    }

    public Valute(String numCode, String charCode, String nominal, String name, String value) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public Valute(String numCode, String charCode, String nominal, String name, String value, String date) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.date = date;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "numCode='" + numCode + '\'' +
                ", charCode='" + charCode + '\'' +
                ", nominal='" + nominal + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

