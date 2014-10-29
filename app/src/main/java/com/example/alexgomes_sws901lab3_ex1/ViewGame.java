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
 * Created by Alex on 10/25/2014.
 */
public class ViewGame extends Activity {

    ListView gameListView;
    public CustomViewGameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_game_list);

        gameListView = (ListView) findViewById(R.id.listViewGame);

        adapter = new CustomViewGameAdapter(this);
        gameListView.setAdapter(adapter);

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DatabaseManager database = new DatabaseManager(ViewGame.this);
                LayoutInflater li = LayoutInflater.from(ViewGame.this);
                View promptsView = li.inflate(R.layout.edit_player_prompt, null);

                findViewById(R.id.text)

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewGame.this);

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
                                        Toast.makeText(ViewGame.this, "Game Modified", Toast.LENGTH_SHORT).show();
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
class CustomViewGameAdapter extends BaseAdapter {

    Context context;
    DatabaseManager database;
    TextView gameList;
    String[] games;
    ImageButton deleteGame;

    public CustomViewGameAdapter(Context c){
        context = c;
        database = new DatabaseManager(context);
        games = database.GetAllGame();
    }

    @Override
    public int getCount() {
        return database.GetAllGame().length;
    }

    @Override
    public Object getItem(int i) {
        return database.GetAllGame()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, final ViewGroup viewGroup) {
        final int position = i;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.single_game_list,viewGroup,false);
        gameList = (TextView) view.findViewById(R.id.gameName);
        gameList.setText(games[position]);

        deleteGame = (ImageButton) view.findViewById(R.id.deleteGame);
        deleteGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.DeleteGame(getItem(position).toString());
                notifyDataSetChanged();
                view.postInvalidate();
                Toast.makeText(context,"Game Removed",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}

