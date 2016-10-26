package com.wpmac.mzeht.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.wpmac.mzeht.GitHubService;
import com.wpmac.mzeht.R;
import com.wpmac.mzeht.pojo.PostQueryInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    @BindView(R.id.retrofitget)
    Button getGetRetrofitButton;

    @BindView(R.id.retrofitpost)
    Button postGetRetrofitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.retrofitget)
    void retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> repos = service.listRepos("octocat");
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("APP", response.body().source().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @OnClick(R.id.retrofitpost)
    void retrofitPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService apiService = retrofit.create(GitHubService.class);
        Call<PostQueryInfo> call = apiService.search("yuantong", "500379523313");
        call.enqueue(new Callback<PostQueryInfo>() {
            @Override
            public void onResponse(Call<PostQueryInfo> call, Response<PostQueryInfo> response) {
                Log.e("APP", response.body().getNu());
            }

            @Override
            public void onFailure(Call<PostQueryInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
