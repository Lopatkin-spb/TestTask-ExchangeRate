package space.lopatkin.spb.testtask_exchangerate.domain.repository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import space.lopatkin.spb.testtask_exchangerate.data.local.Dates;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;

import java.util.List;

public interface ExchangeValutesDataSource {

    //1) копируем все запросы из FactDao ()

    Completable insertExchangeValutes(ExchangeValutes valutes);

    Single<ExchangeValutes> getLastExchangeValutes();

    Completable deleteAllExchangeValutes();

    Flowable<List<Dates>> getAllDates();




}
