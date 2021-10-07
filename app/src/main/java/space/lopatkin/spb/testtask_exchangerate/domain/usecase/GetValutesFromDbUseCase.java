package space.lopatkin.spb.testtask_exchangerate.domain.usecase;

import io.reactivex.Single;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ExchangeValutesDataSource;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ExchangeValutes;

public class GetValutesFromDbUseCase {

    private final ExchangeValutesDataSource mDataSource;

    public GetValutesFromDbUseCase(ExchangeValutesDataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    public Single<ExchangeValutes> execute() {
        return mDataSource
                .getLastExchangeValutes();
    }


}
