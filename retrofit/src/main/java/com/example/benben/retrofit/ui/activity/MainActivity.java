package com.example.benben.retrofit.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.benben.retrofit.Constant;
import com.example.benben.retrofit.R;
import com.example.benben.retrofit.model.MoveBean;
import com.example.benben.retrofit.net.api.MoveApi;
import com.example.benben.retrofit.ui.BaseActivity;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    FloatingActionButton fab;

    String moveName = "海底总动员";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                getMove(moveName);
                break;
        }
    }

    private void getMove(final String moveName) {
        OkHttpClient client = createHttpClient();
        Call<MoveBean> call;
        MoveApi moveApi;
        Retrofit retrofit;
        retrofit = new Retrofit
                .Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        moveApi = retrofit.create(MoveApi.class);

        call = moveApi.getMoveDetail(Constant.KEY, moveName, null);

        call.enqueue(new Callback<MoveBean>() {
            @Override
            public void onResponse(Call<MoveBean> call, Response<MoveBean> response) {
                MoveBean moveBean = response.body();
                Log.i("jam", "========================================" + moveBean.getReason());
                Log.i("jam", "========================================" + moveBean.getResultcode());
                Log.i("jam", "========================================" + moveBean.getResult().size());
                Log.i("jam", "========================================" + moveBean.getResult().get(0).getActors());
                Log.i("jam", "========================================" + moveBean.getResult().get(0).getAlso_known_as());
                Log.i("jam", "========================================" + moveBean.getResult().get(0).getCountry());
                Log.i("jam", "========================================" + moveBean.getResult().get(0).getFilm_locations());
                Log.i("jam", "========================================" + moveBean.getResult().get(0).getLanguage());
            }

            @Override
            public void onFailure(Call<MoveBean> call, Throwable t) {
                Log.i("jam", "========================================" + t.getMessage());
            }
        });
    }

    private OkHttpClient createHttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.d(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

}
