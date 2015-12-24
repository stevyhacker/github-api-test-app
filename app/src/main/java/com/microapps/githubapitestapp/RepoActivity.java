package com.microapps.githubapitestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RepoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        TextView nameTextView = (TextView) findViewById(R.id.repoNameTextView);
        nameTextView.setText(getIntent().getStringExtra("name"));

        TextView descriptionTextView = (TextView) findViewById(R.id.repoDescriptionTextView);
        descriptionTextView.setText(getIntent().getStringExtra("description"));


    }
}
