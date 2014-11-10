package com.example.alexgomes_sws901lab3_ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Splash_screen extends Activity {

    Button splash_screen_btnAddPlayer,splash_screen_btnAddGame,splash_screen_btnViewPlayer,splash_screen_btnPublisher,
            splash_screen_btnDeveloper,splash_screen_btnGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        splash_screen_btnAddPlayer = (Button)findViewById(R.id.splash_screen_btnAddPlayer);
        splash_screen_btnAddGame = (Button) findViewById(R.id.splash_screen_btnAddGame);
        splash_screen_btnViewPlayer = (Button)findViewById(R.id.splash_screen_btnViewPlayer);
        splash_screen_btnPublisher = (Button)findViewById(R.id.splash_screen_btnPublisher);
        splash_screen_btnDeveloper = (Button)findViewById(R.id.splash_screen_btnDeveloper);
        splash_screen_btnGenre = (Button)findViewById(R.id.splash_screen_btnGenre);

        splash_screen_btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash_screen.this, Player.class);
                startActivity(intent);
            }
        });
        splash_screen_btnViewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash_screen.this, ViewPlayer.class);
                startActivity(intent);
            }
        });
        splash_screen_btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Splash_screen.this, GameList.class);
                startActivity(intent);
            }
        });
        splash_screen_btnPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseManager db = new DatabaseManager(Splash_screen.this);
                Intent intent  = new Intent(Splash_screen.this, PublisherDeveloperGenre.class);
                intent.putExtra("activityTitle","Publisher");
                intent.putExtra("ListOfPublisherDeveloperGenre",db.GetAllPublisher());
                startActivity(intent);
            }
        });
        splash_screen_btnDeveloper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseManager db = new DatabaseManager(Splash_screen.this);
                Intent intent = new Intent(Splash_screen.this,PublisherDeveloperGenre.class);
                intent.putExtra("activityTitle","Developer");
                intent.putExtra("ListOfPublisherDeveloperGenre",db.GetAllDeveloper());
                startActivity(intent);
            }
        });
        splash_screen_btnGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseManager db = new DatabaseManager(Splash_screen.this);
                Intent intent = new Intent(Splash_screen.this,PublisherDeveloperGenre.class);
                intent.putExtra("activityTitle","Genre");
                intent.putExtra("ListOfPublisherDeveloperGenre",db.GetAllGenre());
                startActivity(intent);
            }
        });

    }


}
