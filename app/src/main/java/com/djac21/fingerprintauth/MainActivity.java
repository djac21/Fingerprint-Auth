package com.djac21.fingerprintauth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.djac21.fingerprintauth.API.APIClient;
import com.djac21.fingerprintauth.API.APIInterface;
import com.djac21.fingerprintauth.Adapter.NewsAdapter;
import com.djac21.fingerprintauth.Models.NewsModel;
import com.djac21.fingerprintauth.Models.NewsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "212c1dceeac8453d99337f0062e998f3";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    public static final String PREFS_NAME = "sharedPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean dialogShown = settings.getBoolean("dialogShown", false);
        if (!dialogShown) {
            aboutDialog();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("dialogShown", true).apply();
        }

        if (API_KEY.isEmpty()) {
            Toast.makeText(MainActivity.this, "No API key", Toast.LENGTH_LONG).show();
            return;
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new GetData().execute();
            }
        });

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

            Call<NewsResponse> call = apiInterface.getNews(API_KEY);
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if (response.isSuccessful()) {
                        List<NewsModel> news = response.body().getResults();
                        recyclerView.setAdapter(new NewsAdapter(news, R.layout.card_view, getApplicationContext()));
                    }
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Error!")
                            .setCancelable(false)
                            .setMessage("The was an error on displaying the data, please be sure that you have internet access")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new GetData().execute();
                                }
                            });
                    builder.create().show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void aboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Welcome!")
                .setMessage(R.string.about_dialog)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            aboutDialog();
        } else if (id == R.id.action_refresh) {
            new GetData().execute();
        }
        return super.onOptionsItemSelected(item);
    }
}