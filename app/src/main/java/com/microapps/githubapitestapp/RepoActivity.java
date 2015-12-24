package com.microapps.githubapitestapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RepoActivity extends AppCompatActivity {
    ArrayList<String[]> contributors = new ArrayList<>();
    ArrayList<String[]> issues = new ArrayList<>();
    ContributorsListAdapter contributorsListAdapter;
    IssuesListAdapter issuesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        ListView contributorsListView = (ListView) findViewById(R.id.contributorsListView);
        ListView issuesListView = (ListView) findViewById(R.id.issuesListView);

        contributorsListAdapter = new ContributorsListAdapter(this, R.layout.contributor_list_item, contributors);
        issuesListAdapter = new IssuesListAdapter(this, R.layout.issue_list_item, issues);

        contributorsListView.setAdapter(contributorsListAdapter);
        issuesListView.setAdapter(issuesListAdapter);

        TextView nameTextView = (TextView) findViewById(R.id.repoNameTextView);
        nameTextView.setText(getIntent().getStringExtra("name"));

        TextView descriptionTextView = (TextView) findViewById(R.id.repoDescriptionTextView);
        descriptionTextView.setText(getIntent().getStringExtra("description"));

        String repoUrl = getIntent().getStringExtra("url"); //example url https://api.github.com/repos/FreeCodeCamp/FreeCodeCamp
        //            contributors        https://api.github.com/repos/elastic/elasticsearch/contributors?q=sort=contributions
//                 issues   https://api.github.com/repos/elastic/elasticsearch/issues?q=sort=created_at
        String getContributorsUrl = repoUrl + "/contributors?q=sort=contributions";
        String getIssuesUrl = repoUrl + "/issues?q=sort=created_at";


        WebTaskContributors task1 = new WebTaskContributors();
        task1.execute(getContributorsUrl);

        WebTaskIssues task2 = new WebTaskIssues();
        task2.execute(getIssuesUrl);

    }

    class WebTaskContributors extends AsyncTask<String, Void, String> {

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

            try {
                JSONArray jsonResults = new JSONArray(response);

                if (jsonResults.length() == 0) {
                    Toast.makeText(getApplicationContext(), "There are no contributors for this repository.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < 3; i++) {

                        JSONObject item = jsonResults.getJSONObject(i);

                        String[] contributor = new String[2];
                        contributor[0] = item.getString("login");
                        contributor[1] = String.valueOf(item.getInt("contributions"));
                        contributors.add(contributor);

                        Log.e("contributor name", contributor[0]);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            contributorsListAdapter.notifyDataSetChanged();

        }
    }

    class WebTaskIssues extends AsyncTask<String, Void, String> {

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

            try {
                JSONArray jsonResults = new JSONArray(response);

                if (jsonResults.length() == 0) {
                    Toast.makeText(getApplicationContext(), "There are no repositories for this language.", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < 3; i++) {

                        JSONObject item = jsonResults.getJSONObject(i);

                        String[] issue = new String[2];
                        issue[0] = item.getString("title");
                        issue[1] = item.getString("body");

                        issues.add(issue);

                        Log.e("issue name", issue[0]);

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            issuesListAdapter.notifyDataSetChanged();

        }

    }
}


