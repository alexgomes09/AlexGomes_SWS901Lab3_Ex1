package com.example.alexgomes_sws901lab3_ex2;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alex on 10/16/2014.
 */
public class ViewPlayer extends Activity {

    ListView playerListView;
    ImageButton deletePlayer;
    Button btnDeletePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_player);

        playerListView = (ListView) findViewById(R.id.playerList);
        deletePlayer = (ImageButton) findViewById(R.id.deletePlayer);
        btnDeletePlayer = (Button)findViewById(R.id.btnDeletePlayer);

        playerListView.setAdapter(new CustomAdapter(this));

    }
}
class CustomAdapter extends BaseAdapter{

    DatabaseManager database;
    Context context;
    TextView playerList;
    String[] players = database.GetAllPlayer();

    public CustomAdapter(Context c){
        context = c;
//        players = database.GetAllPlayer();

        for (int i = 0; i < players.length; i++) {
            String player = players[i];
        }
    }

    @Override
    public int getCount() {
        return players.length;
    }

    @Override
    public Object getItem(int i) {
        return players[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.single_player_list,viewGroup,false);
        playerList = (TextView) view.findViewById(R.id.playerList);
        playerList.setText(players[i]);

        return view;
    }
}


