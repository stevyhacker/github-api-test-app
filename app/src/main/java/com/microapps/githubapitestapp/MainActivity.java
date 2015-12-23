package com.microapps.githubapitestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] LANGUAGES = new String[]{
                "C++", "Java", "JavaScript", "Python", "Ruby"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, LANGUAGES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.languagesTextView);
        textView.setAdapter(adapter);


    }
}
