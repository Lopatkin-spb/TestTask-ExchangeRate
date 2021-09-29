package space.lopatkin.spb.testtask_exchangerate.data.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_exchange_valutes")

public class Dates {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "date")
    private String date;


    public Dates(int id, String date) {
        this.id = id;
        this.date = date;
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


    @Override
    public String toString() {
        return "DAtes{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }
}
