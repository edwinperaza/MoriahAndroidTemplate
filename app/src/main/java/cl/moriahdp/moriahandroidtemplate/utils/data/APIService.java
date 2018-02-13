package cl.moriahdp.moriahandroidtemplate.utils.data;

import cl.moriahdp.moriahandroidtemplate.main.modelObject.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by edwinperaza on 2/13/18.
 */

public interface APIService {

    @GET("api/1.0/countries/")
    Call<Country> getCountries(@Header("Authorization") String token);

}
