package com.example.alexgomes_sws901lab3_ex1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alex on 10/16/2014.
 */
public class ViewPlayer extends Activity {

    ListView playerListView;
    public CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_player);

        playerListView = (ListView) findViewById(R.id.playerList);

        adapter = new CustomAdapter(this);
        playerListView.setAdapter(adapter);

        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DatabaseManager database = new DatabaseManager(ViewPlayer.this);
                LayoutInflater li = LayoutInflater.from(ViewPlayer.this);
                View promptsView = li.inflate(R.layout.edit_player_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewPlayer.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText firstName = (EditText) promptsView.findViewById(R.id.promptFirstName);
                final EditText lastName = (EditText) promptsView.findViewById(R.id.promptLastName);
                final EditText userName= (EditText) promptsView.findViewById(R.id.promptUserName);
                final EditText password = (EditText) promptsView.findViewById(R.id.promptPassword);
                String playerName = adapterView.getItemAtPosition(i).toString();

                String[] firstLastName= playerName.split("\\s+");
                final Player player = database.GetPlayer(firstLastName[0],firstLastName[1]);

                firstName.setText(firstLastName[0]);
                lastName.setText(firstLastName[1]);
                userName.setText(player.getUserName());
                password.setText(player.getPassword());

                final String whereFirstNameIs = firstLastName[0].toString();

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        String fName = firstName.getText().toString();
                                        String lName = lastName.getText().toString();
                                        String userN = userName.getText().toString();
                                        String pass = password.getText().toString();
                                        player.setFirstName(fName);
                                        player.setLastName(lName);
                                        player.setUserName(userN);
                                        player.setPassword(pass);
                                        database.ModifyPlayer(player,whereFirstNameIs);
                                        Toast.makeText(ViewPlayer.this, "Player Modified", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
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

        deletePlayer = (ImageButton) view.findViewById(R.id.deletePlayer);
        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.DeletePlayer(getItem(position).toString());
                notifyDataSetChanged();
                view.postInvalidate();
                Toast.makeText(context,"Player Removed",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}



