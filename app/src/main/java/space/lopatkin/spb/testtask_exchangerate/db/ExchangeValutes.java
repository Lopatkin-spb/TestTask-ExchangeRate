package space.lopatkin.spb.testtask_exchangerate.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_exchange_valutes")
public class ExchangeValutes {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "date")
    private String date;

    //внутри поля 2 числа: (10/50,8261) -> (Nominal/Value)
    @ColumnInfo(name = "036")
    private String numCode_036;
    @ColumnInfo(name = "944")
    private String numCode_944;
    @ColumnInfo(name = "826")
    private String numCode_826;
    @ColumnInfo(name = "051")
    private String numCode_051;
    @ColumnInfo(name = "933")
    private String numCode_933;
    @ColumnInfo(name = "975")
    private String numCode_975;
    @ColumnInfo(name = "986")
    private String numCode_986;
    @ColumnInfo(name = "348")
    private String numCode_348;
    @ColumnInfo(name = "344")
    private String numCode_344;
    @ColumnInfo(name = "208")
    private String numCode_208;

    @ColumnInfo(name = "840")
    private String numCode_840;
    @ColumnInfo(name = "978")
    private String numCode_978;
    @ColumnInfo(name = "356")
    private String numCode_356;
    @ColumnInfo(name = "398")
    private String numCode_398;
    @ColumnInfo(name = "124")
    private String numCode_124;
    @ColumnInfo(name = "417")
    private String numCode_417;
    @ColumnInfo(name = "156")
    private String numCode_156;
    @ColumnInfo(name = "498")
    private String numCode_498;
    @ColumnInfo(name = "578")
    private String numCode_578;
    @ColumnInfo(name = "985")
    private String numCode_985;

    @ColumnInfo(name = "946")
    private String numCode_946;
    @ColumnInfo(name = "960")
    private String numCode_960;
    @ColumnInfo(name = "702")
    private String numCode_702;
    @ColumnInfo(name = "972")
    private String numCode_972;
    @ColumnInfo(name = "949")
    private String numCode_949;
    @ColumnInfo(name = "934")
    private String numCode_934;
    @ColumnInfo(name = "860")
    private String numCode_860;
    @ColumnInfo(name = "980")
    private String numCode_980;
    @ColumnInfo(name = "203")
    private String numCode_203;
    @ColumnInfo(name = "752")
    private String numCode_752;

    @ColumnInfo(name = "756")
    private String numCode_756;
    @ColumnInfo(name = "710")
    private String numCode_710;
    @ColumnInfo(name = "410")
    private String numCode_410;
    @ColumnInfo(name = "392")
    private String numCode_392;

@Ignore
    public ExchangeValutes() {
    }

    @Ignore
    public ExchangeValutes(String date,
                           String numCode_036, String numCode_944,
                           String numCode_826, String numCode_051,
                           String numCode_933, String numCode_975,
                           String numCode_986, String numCode_348,
                           String numCode_344, String numCode_208,
                           String numCode_840, String numCode_978,
                           String numCode_356, String numCode_398,
                           String numCode_124, String numCode_417,
                           String numCode_156, String numCode_498,
                           String numCode_578, String numCode_985,
                           String numCode_946, String numCode_960,
                           String numCode_702, String numCode_972,
                           String numCode_949, String numCode_934,
                           String numCode_860, String numCode_980,
                           String numCode_203,

                           String numCode_752, String numCode_756,
                           String numCode_710, String numCode_410,
                           String numCode_392) {
        this.date = date;
        this.numCode_036 = numCode_036;
        this.numCode_944 = numCode_944;
        this.numCode_826 = numCode_826;
        this.numCode_051 = numCode_051;
        this.numCode_933 = numCode_933;
        this.numCode_975 = numCode_975;
        this.numCode_986 = numCode_986;
        this.numCode_348 = numCode_348;
        this.numCode_344 = numCode_344;
        this.numCode_208 = numCode_208;
        this.numCode_840 = numCode_840;
        this.numCode_978 = numCode_978;
        this.numCode_356 = numCode_356;
        this.numCode_398 = numCode_398;
        this.numCode_124 = numCode_124;
        this.numCode_417 = numCode_417;
        this.numCode_156 = numCode_156;
        this.numCode_498 = numCode_498;
        this.numCode_578 = numCode_578;
        this.numCode_985 = numCode_985;
        this.numCode_946 = numCode_946;
        this.numCode_960 = numCode_960;
        this.numCode_702 = numCode_702;
        this.numCode_972 = numCode_972;
        this.numCode_949 = numCode_949;
        this.numCode_934 = numCode_934;
        this.numCode_860 = numCode_860;
        this.numCode_980 = numCode_980;
        this.numCode_203 = numCode_203;

        this.numCode_752 = numCode_752;
        this.numCode_756 = numCode_756;
        this.numCode_710 = numCode_710;
        this.numCode_410 = numCode_410;
        this.numCode_392 = numCode_392;
    }

    public ExchangeValutes(int id,
                           String date,
                           String numCode_036, String numCode_944,
                           String numCode_826, String numCode_051,
                           String numCode_933, String numCode_975,
                           String numCode_986, String numCode_348,
                           String numCode_344, String numCode_208,
                           String numCode_840, String numCode_978,
                           String numCode_356, String numCode_398,
                           String numCode_124, String numCode_417,
                           String numCode_156, String numCode_498,
                           String numCode_578, String numCode_985,
                           String numCode_946, String numCode_960,
                           String numCode_702, String numCode_972,
                           String numCode_949, String numCode_934,
                           String numCode_860, String numCode_980,
                           String numCode_203,

                           String numCode_752, String numCode_756,
                           String numCode_710, String numCode_410,
                           String numCode_392) {
        this.id = id;
        this.date = date;
        this.numCode_036 = numCode_036;
        this.numCode_944 = numCode_944;
        this.numCode_826 = numCode_826;
        this.numCode_051 = numCode_051;
        this.numCode_933 = numCode_933;
        this.numCode_975 = numCode_975;
        this.numCode_986 = numCode_986;
        this.numCode_348 = numCode_348;
        this.numCode_344 = numCode_344;
        this.numCode_208 = numCode_208;
        this.numCode_840 = numCode_840;
        this.numCode_978 = numCode_978;
        this.numCode_356 = numCode_356;
        this.numCode_398 = numCode_398;
        this.numCode_124 = numCode_124;
        this.numCode_417 = numCode_417;
        this.numCode_156 = numCode_156;
        this.numCode_498 = numCode_498;
        this.numCode_578 = numCode_578;
        this.numCode_985 = numCode_985;
        this.numCode_946 = numCode_946;
        this.numCode_960 = numCode_960;
        this.numCode_702 = numCode_702;
        this.numCode_972 = numCode_972;
        this.numCode_949 = numCode_949;
        this.numCode_934 = numCode_934;
        this.numCode_860 = numCode_860;
        this.numCode_980 = numCode_980;
        this.numCode_203 = numCode_203;

        this.numCode_752 = numCode_752;
        this.numCode_756 = numCode_756;
        this.numCode_710 = numCode_710;
        this.numCode_410 = numCode_410;
        this.numCode_392 = numCode_392;
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

    public String getNumCode_036() {
        return numCode_036;
    }

    public void setNumCode_036(String numCode_036) {
        this.numCode_036 = numCode_036;
    }

    public String getNumCode_944() {
        return numCode_944;
    }

    public void setNumCode_944(String numCode_944) {
        this.numCode_944 = numCode_944;
    }

    public String getNumCode_826() {
        return numCode_826;
    }

    public void setNumCode_826(String numCode_826) {
        this.numCode_826 = numCode_826;
    }

    public String getNumCode_051() {
        return numCode_051;
    }

    public void setNumCode_051(String numCode_051) {
        this.numCode_051 = numCode_051;
    }

    public String getNumCode_933() {
        return numCode_933;
    }

    public void setNumCode_933(String numCode_933) {
        this.numCode_933 = numCode_933;
    }

    public String getNumCode_975() {
        return numCode_975;
    }

    public void setNumCode_975(String numCode_975) {
        this.numCode_975 = numCode_975;
    }

    public String getNumCode_986() {
        return numCode_986;
    }

    public void setNumCode_986(String numCode_986) {
        this.numCode_986 = numCode_986;
    }

    public String getNumCode_348() {
        return numCode_348;
    }

    public void setNumCode_348(String numCode_348) {
        this.numCode_348 = numCode_348;
    }

    public String getNumCode_344() {
        return numCode_344;
    }

    public void setNumCode_344(String numCode_344) {
        this.numCode_344 = numCode_344;
    }

    public String getNumCode_208() {
        return numCode_208;
    }

    public void setNumCode_208(String numCode_208) {
        this.numCode_208 = numCode_208;
    }

    public String getNumCode_840() {
        return numCode_840;
    }

    public void setNumCode_840(String numCode_840) {
        this.numCode_840 = numCode_840;
    }

    public String getNumCode_978() {
        return numCode_978;
    }

    public void setNumCode_978(String numCode_978) {
        this.numCode_978 = numCode_978;
    }

    public String getNumCode_356() {
        return numCode_356;
    }

    public void setNumCode_356(String numCode_356) {
        this.numCode_356 = numCode_356;
    }

    public String getNumCode_398() {
        return numCode_398;
    }

    public void setNumCode_398(String numCode_398) {
        this.numCode_398 = numCode_398;
    }

    public String getNumCode_124() {
        return numCode_124;
    }

    public void setNumCode_124(String numCode_124) {
        this.numCode_124 = numCode_124;
    }

    public String getNumCode_417() {
        return numCode_417;
    }

    public void setNumCode_417(String numCode_417) {
        this.numCode_417 = numCode_417;
    }

    public String getNumCode_156() {
        return numCode_156;
    }

    public void setNumCode_156(String numCode_156) {
        this.numCode_156 = numCode_156;
    }

    public String getNumCode_498() {
        return numCode_498;
    }

    public void setNumCode_498(String numCode_498) {
        this.numCode_498 = numCode_498;
    }

    public String getNumCode_578() {
        return numCode_578;
    }

    public void setNumCode_578(String numCode_578) {
        this.numCode_578 = numCode_578;
    }

    public String getNumCode_985() {
        return numCode_985;
    }

    public void setNumCode_985(String numCode_985) {
        this.numCode_985 = numCode_985;
    }

    public String getNumCode_946() {
        return numCode_946;
    }

    public void setNumCode_946(String numCode_946) {
        this.numCode_946 = numCode_946;
    }

    public String getNumCode_960() {
        return numCode_960;
    }

    public void setNumCode_960(String numCode_960) {
        this.numCode_960 = numCode_960;
    }

    public String getNumCode_702() {
        return numCode_702;
    }

    public void setNumCode_702(String numCode_702) {
        this.numCode_702 = numCode_702;
    }

    public String getNumCode_972() {
        return numCode_972;
    }

    public void setNumCode_972(String numCode_972) {
        this.numCode_972 = numCode_972;
    }

    public String getNumCode_949() {
        return numCode_949;
    }

    public void setNumCode_949(String numCode_949) {
        this.numCode_949 = numCode_949;
    }

    public String getNumCode_934() {
        return numCode_934;
    }

    public void setNumCode_934(String numCode_934) {
        this.numCode_934 = numCode_934;
    }

    public String getNumCode_860() {
        return numCode_860;
    }

    public void setNumCode_860(String numCode_860) {
        this.numCode_860 = numCode_860;
    }

    public String getNumCode_980() {
        return numCode_980;
    }

    public void setNumCode_980(String numCode_980) {
        this.numCode_980 = numCode_980;
    }

    public String getNumCode_203() {
        return numCode_203;
    }

    public void setNumCode_203(String numCode_203) {
        this.numCode_203 = numCode_203;
    }

    public String getNumCode_752() {
        return numCode_752;
    }

    public void setNumCode_752(String numCode_752) {
        this.numCode_752 = numCode_752;
    }

    public String getNumCode_756() {
        return numCode_756;
    }

    public void setNumCode_756(String numCode_756) {
        this.numCode_756 = numCode_756;
    }

    public String getNumCode_710() {
        return numCode_710;
    }

    public void setNumCode_710(String numCode_710) {
        this.numCode_710 = numCode_710;
    }

    public String getNumCode_410() {
        return numCode_410;
    }

    public void setNumCode_410(String numCode_410) {
        this.numCode_410 = numCode_410;
    }

    public String getNumCode_392() {
        return numCode_392;
    }

    public void setNumCode_392(String numCode_392) {
        this.numCode_392 = numCode_392;
    }

    @Override
    public String toString() {
        return "ExchangeValutes{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", numCode_036='" + numCode_036 + '\'' +
                ", numCode_944='" + numCode_944 + '\'' +
                ", numCode_826='" + numCode_826 + '\'' +
                ", numCode_051='" + numCode_051 + '\'' +
                ", numCode_933='" + numCode_933 + '\'' +
                ", numCode_975='" + numCode_975 + '\'' +
                ", numCode_986='" + numCode_986 + '\'' +
                ", numCode_348='" + numCode_348 + '\'' +
                ", numCode_344='" + numCode_344 + '\'' +
                ", numCode_208='" + numCode_208 + '\'' +
                ", numCode_840='" + numCode_840 + '\'' +
                ", numCode_978='" + numCode_978 + '\'' +
                ", numCode_356='" + numCode_356 + '\'' +
                ", numCode_398='" + numCode_398 + '\'' +
                ", numCode_124='" + numCode_124 + '\'' +
                ", numCode_417='" + numCode_417 + '\'' +
                ", numCode_156='" + numCode_156 + '\'' +
                ", numCode_498='" + numCode_498 + '\'' +
                ", numCode_578='" + numCode_578 + '\'' +
                ", numCode_985='" + numCode_985 + '\'' +
                ", numCode_946='" + numCode_946 + '\'' +
                ", numCode_960='" + numCode_960 + '\'' +
                ", numCode_702='" + numCode_702 + '\'' +
                ", numCode_972='" + numCode_972 + '\'' +
                ", numCode_949='" + numCode_949 + '\'' +
                ", numCode_934='" + numCode_934 + '\'' +
                ", numCode_860='" + numCode_860 + '\'' +
                ", numCode_980='" + numCode_980 + '\'' +
                ", numCode_203='" + numCode_203 + '\'' +

                ", numCode_752='" + numCode_752 + '\'' +
                ", numCode_756='" + numCode_756 + '\'' +
                ", numCode_710='" + numCode_710 + '\'' +
                ", numCode_410='" + numCode_410 + '\'' +
                ", numCode_392='" + numCode_392 + '\'' +
                '}';
    }
}
