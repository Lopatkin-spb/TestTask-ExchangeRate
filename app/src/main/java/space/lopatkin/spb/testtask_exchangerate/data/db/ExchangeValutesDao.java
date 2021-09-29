package space.lopatkin.spb.testtask_exchangerate.data.db;

import androidx.room.*;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

@Dao
public interface ExchangeValutesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertExchangeValutes(ExchangeValutes valutes);

    @Query("SELECT * FROM table_exchange_valutes ORDER BY date DESC LIMIT 1")
//    @Query("select * from table_exchange_valutes ORDER BY date ASC LIMIT 1")
    Flowable<ExchangeValutes> getLastExchangeValutes();

    @Query("DELETE FROM table_exchange_valutes")
    Completable deleteAllExchangeValutes();

    //-----------------------------------------------------------------------

    @Update
    Completable updateExchangeValutes(ExchangeValutes valutes);


    @Query("SELECT id, date FROM table_exchange_valutes ORDER BY date DESC")
    Flowable<List<Dates>> getAllDates();




//    @Query("UPDATE table_exchange_valutes SET ALL WHERE date = :date")
//    void updateRow(String date);

//    @Transaction





}
