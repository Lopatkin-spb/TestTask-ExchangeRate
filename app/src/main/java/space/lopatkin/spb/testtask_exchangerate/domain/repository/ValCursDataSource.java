package space.lopatkin.spb.testtask_exchangerate.domain.repository;

import io.reactivex.Single;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ValCurs;

public interface ValCursDataSource {

    Single<ValCurs> getValCurs();

}
