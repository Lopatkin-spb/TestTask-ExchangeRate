package space.lopatkin.spb.testtask_exchangerate.utils.xmlConverter;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import space.lopatkin.spb.testtask_exchangerate.domain.models.Valute;

import java.util.ArrayList;
import java.util.List;

@Root(name = "ValCurs", strict = false)
public class ValCurs {

    //поля (Root, ElementList, Attribute) для xmlConvertera
    @ElementList(entry = "Valute",inline = true)
    private List<Valute> listValutes;

    @Attribute(name = "Date")
    private String date;

    public ValCurs() {
    }


    public List<Valute> getListValutes() {
        return listValutes;
    }

    public void setListValutes(ArrayList<Valute> listValutes) {
        this.listValutes = listValutes;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ValCurs{" +
                "date=" + date +
                ", listValutes='" + listValutes + '\'' +
                '}';
    }
}
