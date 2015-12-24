package com.microapps.githubapitestapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView programmingLanguageTextView;
    ArrayList<RepoItem> repoItems;
    ListView mostPopularReposListView;
    RepoListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        programmingLanguageTextView = (AutoCompleteTextView)
                findViewById(R.id.languagesTextView);
        mostPopularReposListView = (ListView) findViewById(R.id.mostPopularReposListView);

        String[] listOfLanguages = getResources().getStringArray(R.array.programming_languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listOfLanguages);
        programmingLanguageTextView.setAdapter(adapter);

        repoItems = new ArrayList<>();

        listAdapter = new RepoListAdapter(this, R.layout.repo_list_item, repoItems);
        mostPopularReposListView.setAdapter(listAdapter);

        Button getMostPopularReposButton = (Button) findViewById(R.id.loadMostPopularRepositoriesButton);
        getMostPopularReposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetworkStatus(getApplicationContext())) {
                    String programmingLanguage = programmingLanguageTextView.getText().toString();
                    if (programmingLanguage.contains("#")) {
                        programmingLanguage = programmingLanguage.replace("#", "sharp");
                    }
                    if (programmingLanguage.contains("++")) {
                        programmingLanguage = programmingLanguage.replace("++", "pp");
                    }

//            contributors        https://api.github.com/repos/elastic/elasticsearch/contributors?q=sort=contributions
//                 issues   https://api.github.com/repos/elastic/elasticsearch/issues?q=sort=created_at
                    String getMostPopularReposURL = "https://api.github.com/search/repositories?q=language:" + programmingLanguage + "&sort=stars&order=desc";

                    WebTask task = new WebTask();
                    task.execute(getMostPopularReposURL);

                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mostPopularReposListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RepoItem repoItem = repoItems.get(position);

                Intent intent = new Intent(MainActivity.this, RepoActivity.class);

                intent.putExtra("name", repoItem.name);
                intent.putExtra("description", repoItem.description);
                intent.putExtra("url", repoItem.url);
                startActivity(intent);

            }
        });

    }

    class WebTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... url) {
            try {
                return run(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }

        }

        public String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            OkHttpClient client;
            client = new OkHttpClient();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }


        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            repoItems.clear();

            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonResults = jsonResponse.getJSONArray("items");

                if (jsonResults.length() == 0) {
                    Toast.makeText(getApplicationContext(), "There are no repositories for this language.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < jsonResults.length(); i++) {

                        JSONObject item = jsonResults.getJSONObject(i);

                        RepoItem repo = new RepoItem();
                        repo.name = item.getString("name");
                        repo.description = item.getString("description");
                        repo.contributors_url = item.getString("contributors_url");
                        repo.issues_url = item.getString("issues_url");
                        repo.language = item.getString("language");
                        repo.url = item.getString("url");

                        repoItems.add(repo);

                        Log.e("repoitem name", repo.name);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            listAdapter.notifyDataSetChanged();

        }

    }


    public Boolean checkNetworkStatus(Context context) {
        Boolean status = false;

        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conManager != null) {
            NetworkInfo ni = conManager.getActiveNetworkInfo();
            if (ni != null && ni.isConnected()) {
                status = true;
            } else {
                status = false;
            }
        }

        return status;
    }


}
