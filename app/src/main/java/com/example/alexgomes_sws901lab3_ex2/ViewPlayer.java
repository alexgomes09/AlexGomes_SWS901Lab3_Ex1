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
    String[] singlePlayer;
    Button btnDeletePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_player);

        playerListView = (ListView) findViewById(R.id.playerList);
        btnDeletePlayer = (Button)findViewById(R.id.btnDeletePlayer);

        final DatabaseManager database = new DatabaseManager(getBaseContext());
        singlePlayer = database.GetAllPlayer();

        List

    }
}
class CustomAdapter extends BaseAdapter{

    TextView playerList;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.single_player_list, null);
        playerList = (TextView) view.findViewById(R.id.playerList);
        playerList.setText(list1[position]); **// Problem in this line**

        return view;
    }
}

