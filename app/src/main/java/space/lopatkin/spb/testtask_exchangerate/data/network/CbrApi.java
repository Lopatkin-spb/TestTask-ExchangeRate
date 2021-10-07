package space.lopatkin.spb.testtask_exchangerate.data.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ValCurs;

public interface CbrApi {

    //конечная точка входа(ретрофит)
    @GET("/scripts/XML_daily.asp")
    Single<ValCurs> getValCurs();

}
