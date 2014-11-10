package com.example.alexgomes_sws901lab3_ex1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Alex on 11/9/2014.
 */
public class Publisher extends Activity {

    ListView publisherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publisher_list);

        publisherList = (ListView)findViewById(R.id.publisherList);

        DatabaseManager db = new DatabaseManager(Publisher.this);
        db.GetAllPublisher();

        ArrayAdapter<String> publisherListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, db.GetAllPublisher());
        publisherList.setAdapter(publisherListAdapter);
    }
}
