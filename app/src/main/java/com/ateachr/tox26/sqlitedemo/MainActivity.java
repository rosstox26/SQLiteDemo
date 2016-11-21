package com.ateachr.tox26.sqlitedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button buttonRegister;
    private Button buttonDelete;
    private Button buttonCheckLogin;
    private Button buttonFindID;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextRegisterLogin;
    private EditText editTextRegisterPassword;
    private EditText editTextDeleteLogin;
    private EditText editTextGetIDLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonCheckLogin = (Button) findViewById(R.id.buttonCheckLogin);
        buttonFindID = (Button) findViewById(R.id.buttonFindID);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextRegisterLogin = (EditText) findViewById(R.id.editTextRegisterLogin);
        editTextRegisterPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        editTextDeleteLogin = (EditText) findViewById(R.id.editTextDeleteLogin);
        editTextGetIDLogin = (EditText) findViewById(R.id.editTextFindIDLogin);

        buttonRegister.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonCheckLogin.setOnClickListener(this);
        buttonFindID.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username;
        String password;
        LoginDB loginDB = new LoginDB(this);

        if (v == buttonRegister) {
            username = editTextRegisterLogin.getText().toString();
            password = editTextRegisterPassword.getText().toString();
            long insertid = loginDB.insertLogin(username, password);
            Toast.makeText(this, "Row ID Added: " + insertid, Toast.LENGTH_SHORT).show();
        } else if (v == buttonDelete) {
            username = editTextDeleteLogin.getText().toString();
            int deleteid = loginDB.deleteLogin(username);
            Toast.makeText(this, "Rows Deleted: " + deleteid, Toast.LENGTH_SHORT).show();
        } else if (v == buttonCheckLogin) {
            username = editTextLogin.getText().toString();
            password = editTextPassword.getText().toString();
            String correctPassword = loginDB.getPassword(username);
            if (password.equals(correctPassword)) {
                Toast.makeText(this, "Login Accepted for " + username + ", correct password: " + correctPassword, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Login Denied for " + username + ", correct password: " + correctPassword, Toast.LENGTH_SHORT).show();
            }
        } else if (v == buttonFindID) { //Check which row id does user belong to - test
            username = editTextGetIDLogin.getText().toString();
            int rowID = loginDB.getRowID(username);
            if (rowID != -1) {
                Toast.makeText(this, "Row ID for user: " + username + " is: " + rowID, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User Not Found in Database", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
