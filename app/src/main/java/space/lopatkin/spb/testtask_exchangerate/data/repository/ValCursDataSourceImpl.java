package space.lopatkin.spb.testtask_exchangerate.data.repository;

import io.reactivex.Single;
import space.lopatkin.spb.testtask_exchangerate.data.network.CbrApi;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ValCurs;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ValCursDataSource;

public class ValCursDataSourceImpl implements ValCursDataSource {

    private final CbrApi mCbrApi;

    public ValCursDataSourceImpl(CbrApi cbrApi) {
        mCbrApi = cbrApi;
    }

    @Override
    public Single<ValCurs> getValCurs() {
        return mCbrApi.getValCurs();
    }
}
