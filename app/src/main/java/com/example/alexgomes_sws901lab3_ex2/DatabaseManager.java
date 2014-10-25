package com.example.alexgomes_sws901lab3_ex2;

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
        db.execSQL(CREATE_PLAYER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseManager.class.getName(), "Upgrading from version " +
                oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Player");
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
    public void ModifyPlayer(Player player) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE " + PLAYER_TABLE+ " SET "+ PLAYER_COLUMNS[1] + " = '"+firstname+"' , "+PLAYER_COLUMNS[2]+
                " = '"+lastname+"' , "+PLAYER_COLUMNS[3]+" = '"+username+"' ,"+PLAYER_COLUMNS[4]+" = '"+password+"' WHERE "+
                PLAYER_COLUMNS[1]+" LIKE '"+firstname+"' AND "+PLAYER_COLUMNS[2]+" LIKE '"+lastname+"' AND "+PLAYER_COLUMNS[3]+" LIKE '"+
                username+"' AND "+PLAYER_COLUMNS[4]+" LIKE '"+password+"'";

        SQLiteStatement stmt = database.compileStatement(query);
        stmt.execute();

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(PLAYER_COLUMNS[1],player.getFirstName());
//        contentValues.put(PLAYER_COLUMNS[2],player.getLastName());
//        contentValues.put(PLAYER_COLUMNS[3],player.getUserName());
//        contentValues.put(PLAYER_COLUMNS[4],player.getPassword());
//        database.update(PLAYER_TABLE,contentValues,PLAYER_COLUMNS[1]+"=?",new String []{String.valueOf(player.getFirstName())});
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

}
