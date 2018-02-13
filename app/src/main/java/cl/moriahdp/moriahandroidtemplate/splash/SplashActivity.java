package cl.moriahdp.moriahandroidtemplate.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cl.moriahdp.moriahandroidtemplate.R;
import cl.moriahdp.moriahandroidtemplate.main.modelObject.Country;
import cl.moriahdp.moriahandroidtemplate.utils.data.APIService;
import cl.moriahdp.moriahandroidtemplate.utils.data.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edwinperaza on 2/12/18.
 */

public class SplashActivity extends AppCompatActivity {

    private APIService mAPIService;
    private Button btnCountries;
    private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VybmFtZSI6ImFkbWluQGFkbWluLmNvbSIsImV4cCI6MTU1MDAzMTY2MiwiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20iLCJvcmlnX2lhdCI6MTUxODQ5NTY2Mn0.7qKlel_sd49_3G_BF7JsVmpy6BI-Uc7-MMfIRehX0kY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnCountries = findViewById(R.id.btn_get_countries);

        mAPIService = ApiUtils.getAPIService();

        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAPIService.getCountries("jwt " + token).enqueue(new Callback<Country>() {
                    @Override
                    public void onResponse(Call<Country> call, Response<Country> response) {

                        if (response.isSuccessful()) {
                            //showResponse(response.body().toString());
                            Log.d("SplashActivity", "post submitted to API." + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Country> call, Throwable t) {
                        Log.e("SplashActivity", "Unable to submit post to API.");
                    }
                });
            }
        });
    }
}
