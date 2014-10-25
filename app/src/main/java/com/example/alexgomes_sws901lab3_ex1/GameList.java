package com.example.alexgomes_sws901lab3_ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Alex on 10/25/2014.
 */
public class GameList extends Activity{

    EditText txtGameName,txtGameDescription,txtPublisherID,txtDeveloperID,txtGenreID;
    Button btnAddGame,btnViewGame;
    DatabaseManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_list);
        database = new DatabaseManager(GameList.this);

        txtGameName = (EditText)findViewById(R.id.gameName);
        txtGameDescription = (EditText)findViewById(R.id.gameDescription);
        txtPublisherID = (EditText)findViewById(R.id.publisherID);
        txtDeveloperID = (EditText)findViewById(R.id.developerID);
        txtGenreID = (EditText)findViewById(R.id.genreID);

        btnAddGame = (Button)findViewById(R.id.btnAddGame);
        btnViewGame = (Button)findViewById(R.id.btnViewGame);

        btnAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameName,gameDescription;
                int publisherID,developerID,genreID;

                gameName = txtGameName.getText().toString();
                gameDescription = txtGameDescription.getText().toString();
                publisherID = Integer.parseInt(txtPublisherID.getText().toString());
                developerID = Integer.parseInt(txtDeveloperID.getText().toString());
                genreID = Integer.parseInt(txtGenreID.getText().toString());

                database.AddGame(new GameList(gameName,gameDescription,publisherID,developerID,genreID));
                Toast.makeText(getApplicationContext(), "Record was added", Toast.LENGTH_SHORT).show();
            }
        });
        btnViewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameList.this, ViewGame.class);
                startActivity(intent);
            }
        });


    }

    String gameName;
    String gameDescription;
    int publisherID;
    int developerID;
    int genreID;

    public GameList(){}

    public GameList(String gamename, String gameDescription, int publisherID, int developerID, int genreID) {
        this.gameName = gamename;
        this.gameDescription = gameDescription;
        this.publisherID = publisherID;
        this.developerID = developerID;
        this.genreID = genreID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }

    public int getDeveloperID() {
        return developerID;
    }

    public void setDeveloperID(int developerID) {
        this.developerID = developerID;
    }

    public int getGenreID() {
        return genreID;
    }

    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }
}
