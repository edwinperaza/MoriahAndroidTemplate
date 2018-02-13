package cl.moriahdp.moriahandroidtemplate.utils.data;

/**
 * Created by edwinperaza on 2/13/18.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.0.13:8000/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
