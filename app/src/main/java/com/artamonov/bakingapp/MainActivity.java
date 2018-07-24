package com.artamonov.bakingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.artamonov.bakingapp.data.Recipes;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.ItemClickListener {

    public static final String TAG = "myLogs";
    public static String responseJSON;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.rvBaking);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        getJSONData(url);
    }

    private void getJSONData(String url) {

        if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Check your Internet connection or try later",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Log.i(TAG, "response: " + response.message());
                    if (response.isSuccessful()) {
                        responseJSON = response.body().string();

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              parseJSONRecipes(responseJSON);
                                          }
                                      }
                        );

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Response is not successful",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });


        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Please check your Internet connection",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void parseJSONRecipes(String responseJSON) {
        RecipesParser.parseJSONRecipes(responseJSON);
        populateRecipesImages(RecipesParser.recipesList);
        if (RecipesParser.recipesList != null) {
            RecipeRecyclerViewAdapter recipeRecyclerViewAdapter =
                    new RecipeRecyclerViewAdapter(MainActivity.this,
                            RecipesParser.recipesList, this);
            recyclerView.setAdapter(recipeRecyclerViewAdapter);
        }
    }

    private void populateRecipesImages(List<Recipes> recipesList) {
        for (int i = 0; i < recipesList.size(); i++) {

            switch (i) {
                case 0:
                    recipesList.get(i).setRecipeImage(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.nutellapie_blur, null));
                    break;
                case 1:
                    recipesList.get(1).setRecipeImage(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.brownies_blur, null));
                    break;
                case 2:
                    recipesList.get(i).setRecipeImage(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.yellowcake_blur, null));
                    break;
                case 3:
                    recipesList.get(i).setRecipeImage(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.cheesecake_blur, null));
                    break;
            }
        }

    }

    @Override
    public void onItemClick(int position) {
        RecipesParser.parseJSONIngredientsSteps(responseJSON, position);
        populateStepsThumbnails(RecipesParser.stepsList);
        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra(StepDetailFragment.ARG_ITEM_ID, position);
        startActivity(intent);

    }

    private void populateStepsThumbnails(List<Recipes> stepsList) {
        for (int i = 0; i < stepsList.size(); i++) {

            if (TextUtils.isEmpty(stepsList.get(i).getStepVideoUrl())) {
                stepsList.get(i).setStepThumbnail(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.nutellapie, null));
            }
        }
    }

}
