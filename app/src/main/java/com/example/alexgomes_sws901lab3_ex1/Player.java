package com.example.alexgomes_sws901lab3_ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alex on 10/15/2014.
 */
public class Player extends Activity implements TextWatcher {

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    Button btnAddPlayer;
    Button btnViewPlayer;

    TextView txtFirstname,txtLastName,txtUserName,txtPassword;

    DatabaseManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        database = new DatabaseManager(getBaseContext());

        btnAddPlayer = (Button)findViewById(R.id.btnAddPlayer);
        btnViewPlayer = (Button)findViewById(R.id.splash_screen_btnViewPlayer);
        txtFirstname = (TextView)findViewById(R.id.firstName);
        txtLastName = (TextView)findViewById(R.id.lastName);
        txtUserName = (TextView)findViewById(R.id.username);
        txtPassword = (TextView)findViewById(R.id.password);
        btnAddPlayer.setEnabled(false);

        txtFirstname.addTextChangedListener(this);
        txtLastName.addTextChangedListener(this);
        txtUserName.addTextChangedListener(this);
        txtPassword.addTextChangedListener(this);


        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = txtFirstname.getText().toString();
                lastName = txtLastName.getText().toString();
                userName = txtUserName.getText().toString();
                password = txtPassword.getText().toString();

                database.AddPlayer(new Player(firstName,lastName,userName,password));
                Toast.makeText(getApplicationContext(),"Record was added",Toast.LENGTH_SHORT).show();
            }
        });

        btnViewPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Player.this, ViewPlayer.class);
                startActivity(intent);
            }
        });
    }

    //text firstname Validation

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if( txtFirstname.getText().toString().length() == 0 )
            txtFirstname.setError( "First name field is required!" );

        if( txtLastName.getText().toString().length() == 0 )
            txtLastName.setError( "Last name field is required!" );

        if( txtUserName.getText().toString().length() == 0 )
            txtUserName.setError("Username field is required!");

        if( txtPassword.getText().toString().length() == 0 )
            txtPassword.setError( "Password field is required!" );

        if(txtFirstname.getText().toString().length() != 0 &&
                txtLastName.getText().toString().length() != 0 &&
                txtUserName.getText().toString().length() != 0&&
                txtPassword.getText().toString().length() != 0){
            btnAddPlayer.setEnabled(true);
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {

    }


    @Override
    public String toString() {
        return "Player [First Name = " + firstName+ ", Last Name = " + lastName + ", Username = " + userName+",Password = "+password+ "]";
    }

    public Player(){}

    public Player(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
