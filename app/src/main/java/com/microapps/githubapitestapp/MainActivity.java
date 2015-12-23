package com.microapps.githubapitestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] listOfLanguages = getResources().getStringArray(R.array.programming_languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, listOfLanguages);
        final AutoCompleteTextView programmingLanguageTextView = (AutoCompleteTextView)
                findViewById(R.id.languagesTextView);
        programmingLanguageTextView.setAdapter(adapter);

        Button getMostPopularReposButton = (Button) findViewById(R.id.loadMostPopularRepositoriesButton);
        getMostPopularReposButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String programmingLanguage = programmingLanguageTextView.getText().toString(); ;
                String  getMostPopularReposURL = "https://api.github.com/search/repositories?q=language:" + programmingLanguage +  "&sort=stars&order=desc";
            }
        });


    }
}
