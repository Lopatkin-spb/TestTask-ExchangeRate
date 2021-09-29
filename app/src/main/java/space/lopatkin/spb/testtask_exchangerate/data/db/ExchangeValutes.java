package space.lopatkin.spb.testtask_exchangerate.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import space.lopatkin.spb.testtask_exchangerate.utils.stateMachine.States;

@Entity(tableName = "table_exchange_valutes")
public class ExchangeValutes  {

    //поля (Entity, ColumnInfo, PrimaryKey) для базы данных
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    //внутри поля 5 переменных: (036/AUD/1/Австралийский доллар/50,8261) -> (NumCode/CharCode/Nominal/Name/Value)
    //было внутри поля 2 числа: (10/50,8261) -> (Nominal/Value)

    @ColumnInfo(name = "1")
    private String valute1;
    @ColumnInfo(name = "2")
    private String valute2;
    @ColumnInfo(name = "3")
    private String valute3;
    @ColumnInfo(name = "4")
    private String valute4;
    @ColumnInfo(name = "5")
    private String valute5;
    @ColumnInfo(name = "6")
    private String valute6;
    @ColumnInfo(name = "7")
    private String valute7;
    @ColumnInfo(name = "8")
    private String valute8;
    @ColumnInfo(name = "9")
    private String valute9;
    @ColumnInfo(name = "10")
    private String valute10;

    @ColumnInfo(name = "11")
    private String valute11;
    @ColumnInfo(name = "12")
    private String valute12;
    @ColumnInfo(name = "13")
    private String valute13;
    @ColumnInfo(name = "14")
    private String valute14;
    @ColumnInfo(name = "15")
    private String valute15;
    @ColumnInfo(name = "16")
    private String valute16;
    @ColumnInfo(name = "17")
    private String valute17;
    @ColumnInfo(name = "18")
    private String valute18;
    @ColumnInfo(name = "19")
    private String valute19;
    @ColumnInfo(name = "20")
    private String valute20;

    @ColumnInfo(name = "21")
    private String valute21;
    @ColumnInfo(name = "22")
    private String valute22;
    @ColumnInfo(name = "23")
    private String valute23;
    @ColumnInfo(name = "24")
    private String valute24;
    @ColumnInfo(name = "25")
    private String valute25;
    @ColumnInfo(name = "26")
    private String valute26;
    @ColumnInfo(name = "27")
    private String valute27;
    @ColumnInfo(name = "28")
    private String valute28;
    @ColumnInfo(name = "29")
    private String valute29;
    @ColumnInfo(name = "30")
    private String valute30;

    @ColumnInfo(name = "31")
    private String valute31;
    @ColumnInfo(name = "32")
    private String valute32;
    @ColumnInfo(name = "33")
    private String valute33;
    @ColumnInfo(name = "34")
    private String valute34;

    @Ignore
    public ExchangeValutes() {
    }

    @Ignore
    public ExchangeValutes(String date, String valute1, String valute2, String valute3, String valute4, String valute5, String valute6, String valute7, String valute8, String valute9, String valute10, String valute11, String valute12, String valute13, String valute14, String valute15, String valute16, String valute17, String valute18, String valute19, String valute20, String valute21, String valute22, String valute23, String valute24, String valute25, String valute26, String valute27, String valute28, String valute29, String valute30, String valute31, String valute32, String valute33, String valute34) {
        this.date = date;
        this.valute1 = valute1;
        this.valute2 = valute2;
        this.valute3 = valute3;
        this.valute4 = valute4;
        this.valute5 = valute5;
        this.valute6 = valute6;
        this.valute7 = valute7;
        this.valute8 = valute8;
        this.valute9 = valute9;
        this.valute10 = valute10;
        this.valute11 = valute11;
        this.valute12 = valute12;
        this.valute13 = valute13;
        this.valute14 = valute14;
        this.valute15 = valute15;
        this.valute16 = valute16;
        this.valute17 = valute17;
        this.valute18 = valute18;
        this.valute19 = valute19;
        this.valute20 = valute20;
        this.valute21 = valute21;
        this.valute22 = valute22;
        this.valute23 = valute23;
        this.valute24 = valute24;
        this.valute25 = valute25;
        this.valute26 = valute26;
        this.valute27 = valute27;
        this.valute28 = valute28;
        this.valute29 = valute29;
        this.valute30 = valute30;
        this.valute31 = valute31;
        this.valute32 = valute32;
        this.valute33 = valute33;
        this.valute34 = valute34;
    }

//    @Ignore //временно для проверки

    public ExchangeValutes(int id, String date, String valute1, String valute2, String valute3, String valute4, String valute5, String valute6, String valute7, String valute8, String valute9, String valute10, String valute11, String valute12, String valute13, String valute14, String valute15, String valute16, String valute17, String valute18, String valute19, String valute20, String valute21, String valute22, String valute23, String valute24, String valute25, String valute26, String valute27, String valute28, String valute29, String valute30, String valute31, String valute32, String valute33, String valute34) {
        this.id = id;
        this.date = date;
        this.valute1 = valute1;
        this.valute2 = valute2;
        this.valute3 = valute3;
        this.valute4 = valute4;
        this.valute5 = valute5;
        this.valute6 = valute6;
        this.valute7 = valute7;
        this.valute8 = valute8;
        this.valute9 = valute9;
        this.valute10 = valute10;
        this.valute11 = valute11;
        this.valute12 = valute12;
        this.valute13 = valute13;
        this.valute14 = valute14;
        this.valute15 = valute15;
        this.valute16 = valute16;
        this.valute17 = valute17;
        this.valute18 = valute18;
        this.valute19 = valute19;
        this.valute20 = valute20;
        this.valute21 = valute21;
        this.valute22 = valute22;
        this.valute23 = valute23;
        this.valute24 = valute24;
        this.valute25 = valute25;
        this.valute26 = valute26;
        this.valute27 = valute27;
        this.valute28 = valute28;
        this.valute29 = valute29;
        this.valute30 = valute30;
        this.valute31 = valute31;
        this.valute32 = valute32;
        this.valute33 = valute33;
        this.valute34 = valute34;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValute1() {
        return valute1;
    }

    public void setValute1(String valute1) {
        this.valute1 = valute1;
    }

    public String getValute2() {
        return valute2;
    }

    public void setValute2(String valute2) {
        this.valute2 = valute2;
    }

    public String getValute3() {
        return valute3;
    }

    public void setValute3(String valute3) {
        this.valute3 = valute3;
    }

    public String getValute4() {
        return valute4;
    }

    public void setValute4(String valute4) {
        this.valute4 = valute4;
    }

    public String getValute5() {
        return valute5;
    }

    public void setValute5(String valute5) {
        this.valute5 = valute5;
    }

    public String getValute6() {
        return valute6;
    }

    public void setValute6(String valute6) {
        this.valute6 = valute6;
    }

    public String getValute7() {
        return valute7;
    }

    public void setValute7(String valute7) {
        this.valute7 = valute7;
    }

    public String getValute8() {
        return valute8;
    }

    public void setValute8(String valute8) {
        this.valute8 = valute8;
    }

    public String getValute9() {
        return valute9;
    }

    public void setValute9(String valute9) {
        this.valute9 = valute9;
    }

    public String getValute10() {
        return valute10;
    }

    public void setValute10(String valute10) {
        this.valute10 = valute10;
    }

    public String getValute11() {
        return valute11;
    }

    public void setValute11(String valute11) {
        this.valute11 = valute11;
    }

    public String getValute12() {
        return valute12;
    }

    public void setValute12(String valute12) {
        this.valute12 = valute12;
    }

    public String getValute13() {
        return valute13;
    }

    public void setValute13(String valute13) {
        this.valute13 = valute13;
    }

    public String getValute14() {
        return valute14;
    }

    public void setValute14(String valute14) {
        this.valute14 = valute14;
    }

    public String getValute15() {
        return valute15;
    }

    public void setValute15(String valute15) {
        this.valute15 = valute15;
    }

    public String getValute16() {
        return valute16;
    }

    public void setValute16(String valute16) {
        this.valute16 = valute16;
    }

    public String getValute17() {
        return valute17;
    }

    public void setValute17(String valute17) {
        this.valute17 = valute17;
    }

    public String getValute18() {
        return valute18;
    }

    public void setValute18(String valute18) {
        this.valute18 = valute18;
    }

    public String getValute19() {
        return valute19;
    }

    public void setValute19(String valute19) {
        this.valute19 = valute19;
    }

    public String getValute20() {
        return valute20;
    }

    public void setValute20(String valute20) {
        this.valute20 = valute20;
    }

    public String getValute21() {
        return valute21;
    }

    public void setValute21(String valute21) {
        this.valute21 = valute21;
    }

    public String getValute22() {
        return valute22;
    }

    public void setValute22(String valute22) {
        this.valute22 = valute22;
    }

    public String getValute23() {
        return valute23;
    }

    public void setValute23(String valute23) {
        this.valute23 = valute23;
    }

    public String getValute24() {
        return valute24;
    }

    public void setValute24(String valute24) {
        this.valute24 = valute24;
    }

    public String getValute25() {
        return valute25;
    }

    public void setValute25(String valute25) {
        this.valute25 = valute25;
    }

    public String getValute26() {
        return valute26;
    }

    public void setValute26(String valute26) {
        this.valute26 = valute26;
    }

    public String getValute27() {
        return valute27;
    }

    public void setValute27(String valute27) {
        this.valute27 = valute27;
    }

    public String getValute28() {
        return valute28;
    }

    public void setValute28(String valute28) {
        this.valute28 = valute28;
    }

    public String getValute29() {
        return valute29;
    }

    public void setValute29(String valute29) {
        this.valute29 = valute29;
    }

    public String getValute30() {
        return valute30;
    }

    public void setValute30(String valute30) {
        this.valute30 = valute30;
    }

    public String getValute31() {
        return valute31;
    }

    public void setValute31(String valute31) {
        this.valute31 = valute31;
    }

    public String getValute32() {
        return valute32;
    }

    public void setValute32(String valute32) {
        this.valute32 = valute32;
    }

    public String getValute33() {
        return valute33;
    }

    public void setValute33(String valute33) {
        this.valute33 = valute33;
    }

    public String getValute34() {
        return valute34;
    }

    public void setValute34(String valute34) {
        this.valute34 = valute34;
    }


    @Override
    public String toString() {
        return "ExchangeValutes{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", valute1='" + valute1 + '\'' +
                ", valute2='" + valute2 + '\'' +
                ", valute3='" + valute3 + '\'' +
                ", valute4='" + valute4 + '\'' +
                ", valute5='" + valute5 + '\'' +
                ", valute6='" + valute6 + '\'' +
                ", valute7='" + valute7 + '\'' +
                ", valute8='" + valute8 + '\'' +
                ", valute9='" + valute9 + '\'' +
                ", valute10='" + valute10 + '\'' +
                ", valute11='" + valute11 + '\'' +
                ", valute12='" + valute12 + '\'' +
                ", valute13='" + valute13 + '\'' +
                ", valute14='" + valute14 + '\'' +
                ", valute15='" + valute15 + '\'' +
                ", valute16='" + valute16 + '\'' +
                ", valute17='" + valute17 + '\'' +
                ", valute18='" + valute18 + '\'' +
                ", valute19='" + valute19 + '\'' +
                ", valute20='" + valute20 + '\'' +
                ", valute21='" + valute21 + '\'' +
                ", valute22='" + valute22 + '\'' +
                ", valute23='" + valute23 + '\'' +
                ", valute24='" + valute24 + '\'' +
                ", valute25='" + valute25 + '\'' +
                ", valute26='" + valute26 + '\'' +
                ", valute27='" + valute27 + '\'' +
                ", valute28='" + valute28 + '\'' +
                ", valute29='" + valute29 + '\'' +
                ", valute30='" + valute30 + '\'' +
                ", valute31='" + valute31 + '\'' +
                ", valute32='" + valute32 + '\'' +
                ", valute33='" + valute33 + '\'' +
                ", valute34='" + valute34 + '\'' +
                '}';
    }
}
