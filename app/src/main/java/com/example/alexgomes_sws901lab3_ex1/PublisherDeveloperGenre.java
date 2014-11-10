package com.example.alexgomes_sws901lab3_ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Alex on 11/9/2014.
 */
public class PublisherDeveloperGenre extends Activity {

    ListView publisher_developer_genre_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_developer_genre_list);

        publisher_developer_genre_list = (ListView)findViewById(R.id.publisher_developer_genre_list);

        Intent intent = getIntent();

        String[] listOfPublisherDeveloperGenres = intent.getStringArrayExtra("ListOfPublisherDeveloperGenre");

        setTitle(intent.getStringExtra("activityTitle"));

        ArrayAdapter<String> publisherListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfPublisherDeveloperGenres);
        publisher_developer_genre_list.setAdapter(publisherListAdapter);
    }
}
