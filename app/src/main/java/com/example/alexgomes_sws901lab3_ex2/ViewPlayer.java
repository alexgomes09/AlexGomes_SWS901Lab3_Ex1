package com.example.alexgomes_sws901lab3_ex2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Alex on 10/16/2014.
 */
public class ViewPlayer extends Activity {

    ListView playerListView;
    Button btnDeletePlayer;
    boolean refreshPage = false;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_player);

        playerListView = (ListView) findViewById(R.id.playerList);
        btnDeletePlayer = (Button)findViewById(R.id.btnDeletePlayer);

        adapter = new CustomAdapter(this);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetInvalidated();
                playerListView.setAdapter(adapter);

            }
        });

    }

}
class CustomAdapter extends BaseAdapter{

    Context context;
    DatabaseManager database;
    TextView playerList;
    String[] players;
    ImageButton deletePlayer;
    ViewPlayer viewPlayer;


    public CustomAdapter(Context c){
        context = c;
        database = new DatabaseManager(context);
        players = database.GetAllPlayer();
    }

    @Override
    public int getCount() {
        return database.GetAllPlayer().length;
    }

    @Override
    public Object getItem(int i) {
        return database.GetAllPlayer()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        final int position = i;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.single_player_list,viewGroup,false);
        playerList = (TextView) view.findViewById(R.id.playerList);
        playerList.setText(players[position]);

        viewPlayer = new ViewPlayer();

        deletePlayer = (ImageButton) view.findViewById(R.id.deletePlayer);
        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.DeletePlayer(getItem(position).toString());
            }
        });

        return view;
    }
}


