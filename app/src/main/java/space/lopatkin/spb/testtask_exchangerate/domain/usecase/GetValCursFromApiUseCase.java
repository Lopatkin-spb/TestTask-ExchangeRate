package space.lopatkin.spb.testtask_exchangerate.domain.usecase;

import io.reactivex.Single;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ValCurs;
import space.lopatkin.spb.testtask_exchangerate.domain.repository.ValCursDataSource;

public class GetValCursFromApiUseCase {


    //публичная функция возвращает список валют
    // тут будет выбор откуда приходят данные: из бд или с апи
    // запрос приходит из вьюМодел и уходит в датаСоурс

    //важный факт- IF можно делать тут , но в дате уже нельзя

    //если метод ничего не возвращает то нужно тут сделать
    // проверку на наличие входных данных

    private final ValCursDataSource mNetworkDataSource;

    public GetValCursFromApiUseCase(ValCursDataSource valCursDataSource) {
        mNetworkDataSource = valCursDataSource;
    }


    public Single<ValCurs> execute() {
        return mNetworkDataSource.getValCurs();
    }

}
