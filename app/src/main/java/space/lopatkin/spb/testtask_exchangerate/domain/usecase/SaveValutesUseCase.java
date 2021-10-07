package space.lopatkin.spb.testtask_exchangerate.domain.usecase;

import io.reactivex.Completable;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;

public class SaveValutesUseCase {

    private final ExchangeValutesDataSource mDataSource;


    public SaveValutesUseCase(ExchangeValutesDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Completable execute(ExchangeValutes exchangeValutes) {
        return mDataSource.insertExchangeValutes(exchangeValutes);


//        if (exchangeValutes.getDate().isEmpty()) {
////            mDataSource
////                    .insertExchangeValutes(exchangeValutes);
//            return false;
//        } else {
//            return true;
//        }
    }


}
