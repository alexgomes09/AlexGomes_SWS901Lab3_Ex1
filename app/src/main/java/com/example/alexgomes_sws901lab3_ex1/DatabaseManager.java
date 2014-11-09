package com.example.alexgomes_sws901lab3_ex1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by Alex on 10/15/2014.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "socialNetworking.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE Player ("+"player_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+"firstname VARCHAR, "+"lastname VARHCAR, "+"username VARCHAR, "+"password VARCHAR)";
        String CREATE_GAMELIST_TABLE = "CREATE TABLE GameList ("+"game_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+"game_name VARCHAR, "+"game_description VARCHAR, "+"publisher_id INTEGER, "+"developer_id INTEGER, "+"genre_id INTEGER,"+"FOREIGN KEY(publisher_id) REFERENCES Publisher(publisher_id),"+" FOREIGN KEY(developer_id) REFERENCES Developer(developer_id),"+"FOREIGN KEY(genre_id)REFERENCES Genre(genre_id))";
        String CREATE_PUBLISHER_TABLE = "CREATE TABLE Publisher ("+"publisher_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+"publisher_name VARCHAR, "+"website VARCHAR)";
        String CREATE_DEVELOPER_TABLE = "CREATE TABLE Developer ("+"developer_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+"developer_name VARCHAR)";
        String CREATE_GENRE_TABLE = "CREATE TABLE Genre ("+"genre_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , "+"description VARCHAR)";
        String CREATE_PLAYER_AND_GAME_TABLE= "CREATE TABLE PlayerAndGame("+"player_game_id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL ,"+"player_id INTEGER, "+"game_id INTEGER, "+"score INTEGER,"+"date_time VARCHAR, "+" FOREIGN KEY(player_id) REFERENCES Player(player_id),"+" FOREIGN KEY(game_id) REFERENCES GameList(game_id))";

        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_PUBLISHER_TABLE);
        db.execSQL(CREATE_DEVELOPER_TABLE);
        db.execSQL(CREATE_GENRE_TABLE);
        db.execSQL(CREATE_GAMELIST_TABLE);
        db.execSQL(CREATE_PLAYER_AND_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseManager.class.getName(), "Upgrading from version " +
                oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Player");
        db.execSQL("DROP TABLE IF EXISTS GameList");
        this.onCreate(db);
    }

    /**
     * CRUD operations (create "add", read "get", update, delete) player + get all player + delete all player
     */

    // Player table name
    private static final String PLAYER_TABLE = "Player";
    // Player Table Columns names
    private static final String player_id = "player_id";
    private static final String firstname = "firstname";
    private static final String lastname = "lastname";
    private static final String username= "username";
    private static final String password= "password";

    private static final String[] PLAYER_COLUMNS ={player_id,firstname,lastname,username,password};

    public void AddPlayer(Player player){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(firstname,player.getFirstName());
        values.put(lastname,player.getLastName());
        values.put(username,player.getUserName());
        values.put(password,player.getPassword());

        db.insert(PLAYER_TABLE,null,values);
        Log.d("addPlayer",player.toString());
        db.close();
    }

    public Player GetPlayer(String firstName, String lastName){
        SQLiteDatabase db = this.getReadableDatabase();

        String getPlayerQuery = "SELECT * FROM "+PLAYER_TABLE+" WHERE "+PLAYER_COLUMNS[1]+" = '"+firstName+"' AND "+PLAYER_COLUMNS[2]+" = '"+lastName+"'";
        Cursor cursor = db.rawQuery(getPlayerQuery,null);

        if(cursor !=null){
            cursor.moveToFirst();
        }
        Player player = new Player();
        player.setFirstName(cursor.getString(1));
        player.setLastName(cursor.getString(2));
        player.setUserName(cursor.getString(3));
        player.setPassword(cursor.getString(4));
        return player;
    }

    public String[] GetAllPlayer() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT  * FROM " + PLAYER_TABLE;
        Cursor cursor = database.rawQuery(query,null);

        String[] array = new String[cursor.getCount()];
        int i=0;
        while (cursor.moveToNext()){
            String fname = cursor.getString(cursor.getColumnIndex("firstname"));
            String lname = cursor.getString(cursor.getColumnIndex("lastname"));
            array[i] = fname +" "+ lname;
            i++;
        }
        return array;
    }
    public void ModifyPlayer(Player player, String whereFirstNameIs) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYER_COLUMNS[1], player.getFirstName());
        contentValues.put(PLAYER_COLUMNS[2],player.getLastName());
        contentValues.put(PLAYER_COLUMNS[3],player.getUserName());
        contentValues.put(PLAYER_COLUMNS[4],player.getPassword());
        database.update(PLAYER_TABLE,contentValues,PLAYER_COLUMNS[1]+"=?",new String[]{String.valueOf(whereFirstNameIs)});
        database.close();
    }

    public void DeletePlayer(String playerName){
        SQLiteDatabase database = this.getWritableDatabase();

        String fName = null;
        String lName = null;

        String data = playerName;
        final String[] splitPlayerName = data.split("\\s+");

        for(int i = 0; i < splitPlayerName.length; i = i + 2){
            fName = splitPlayerName[i];
            System.out.println(fName+", ");
        }
        for(int j = 1; j < splitPlayerName.length; j = j + 2){
            lName = splitPlayerName[j];
            System.out.println(lName+", ");
        }

        String query = "delete from "+PLAYER_TABLE+" where "+PLAYER_COLUMNS[1]+
                " IN ('"+fName+"') and "+PLAYER_COLUMNS[2]+
                " IN ('"+lName+"')";

        SQLiteStatement stmt = database.compileStatement(query);
        stmt.execute();
    }
    /*
    * CRUD for GAME LIST TABLE;
    */

    // GAME LIST TABLE
    private static final String GAMELIST_TABLE = "GameList";
    // Game LIST column names
    private static final String gameID = "game_id";
    private static final String gameName = "game_name";
    private static final String gameDescription = "game_description";
    private static final String publisherID= "publisher_id";
    private static final String developerID= "developer_id";
    private static final String genreID= "genre_id";

    private static final String[] GAMELIST_COLUMN ={gameID,gameName,gameDescription,publisherID,developerID,genreID};

    public void AddGame(GameList gameList){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GAMELIST_COLUMN[1],gameList.getGameName());
        cv.put(GAMELIST_COLUMN[2],gameList.getGameDescription());
        cv.put(GAMELIST_COLUMN[3],gameList.getPublisherID());
        cv.put(GAMELIST_COLUMN[4],gameList.getDeveloperID());
        cv.put(GAMELIST_COLUMN[5],gameList.getGenreID());

        database.insert(GAMELIST_TABLE,null,cv);
        database.close();

    }

    public GameList GetGame(String gameName){
        SQLiteDatabase db = this.getReadableDatabase();

        String getGameQuery = "SELECT * FROM "+GAMELIST_TABLE+" WHERE "+GAMELIST_COLUMN[1]+" = '"+gameName+"'";
        Cursor cursor = db.rawQuery(getGameQuery,null);

        if(cursor !=null){
            cursor.moveToFirst();
        }
        GameList game = new GameList();
        game.setGameName(cursor.getString(1));
        game.setGameDescription(cursor.getString(2));
        game.setPublisherID(cursor.getInt(3));
        game.setDeveloperID(cursor.getInt(4));
        game.setGenreID(cursor.getInt(5));
        return game;
    }

    public String[] GetAllGame() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT  * FROM " + GAMELIST_TABLE;
        Cursor cursor = database.rawQuery(query,null);

        String[] array = new String[cursor.getCount()];
        int i=0;
        while (cursor.moveToNext()){
            String gameName = cursor.getString(cursor.getColumnIndex(GAMELIST_COLUMN[1]));
            array[i] = gameName ;
            i++;
        }
        return array;
    }

    public void ModifyGame(GameList gameList, String whereGameis) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GAMELIST_COLUMN[1], gameList.getGameName());
        contentValues.put(GAMELIST_COLUMN[2],gameList.getGameDescription());
        contentValues.put(GAMELIST_COLUMN[3],gameList.getPublisherID());
        contentValues.put(GAMELIST_COLUMN[4],gameList.getDeveloperID());
        contentValues.put(GAMELIST_COLUMN[4],gameList.getGenreID());
        database.update(GAMELIST_TABLE,contentValues,GAMELIST_COLUMN[1]+"=?",new String[]{String.valueOf(whereGameis)});
        database.close();
    }

    public void DeleteGame(String gameName){
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "delete from "+GAMELIST_TABLE+" where "+GAMELIST_COLUMN[1]+" = '"+gameName+"'";

        SQLiteStatement stmt = database.compileStatement(query);
        stmt.execute();
    }
}
