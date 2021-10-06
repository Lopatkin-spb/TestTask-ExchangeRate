package space.lopatkin.spb.testtask_exchangerate.utils;

import io.reactivex.Single;
import retrofit2.http.GET;
import space.lopatkin.spb.testtask_exchangerate.domain.models.ValCurs;

public interface CbrApi {

        //конечная точка входа(ретрофит)
        @GET("/scripts/XML_daily.asp")
        Single<ValCurs> getAllValutes();


//        http://www.cbr.ru/scripts/XML_daily.asp
//        http://www.cbr.ru/scripts/XML_daily.asp

//        @GET("/public/v1/users/123/todos.xml")
//        Single<TestValCurs> getAllTest2();

//        @GET("/scripts/XML_dynamic.asp?date_req1=02/03/2001&date_req2=13/03/2001&VAL_NM_RQ=R01235")
//        Single<TestValCurs> getAllTest3();


}
