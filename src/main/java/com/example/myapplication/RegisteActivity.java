package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisteActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register;
    private Button regcancel;
    private EditText reguser;
    private EditText regpwd;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //String ss=msg.getData().getString("key");
                    Toast.makeText(RegisteActivity.this, R.string.regsussces, Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(RegisteActivity.this, MainActivity.class);
                   // startActivity(intent);
                    break;
                case 2:
                    Toast.makeText(RegisteActivity.this, R.string.regerror, Toast.LENGTH_SHORT).show();
                    break;


            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);

        register = (Button) findViewById(R.id.ok);
        regcancel = (Button) findViewById(R.id.cancel);

        register.setOnClickListener(this);
        regcancel.setOnClickListener(this);

        reguser = (EditText) findViewById(R.id.reguser);
        regpwd = (EditText) findViewById(R.id.regpwd);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                String account = reguser.getText().toString();
                String pwd = regpwd.getText().toString();
                Regist reg = new Regist(account, pwd, handler);
                reg.start();
                break;
            case R.id.cancel:
                Intent intent = new Intent(RegisteActivity.this, MainActivity.class);
                startActivity(intent);
                break;


        }


    }

}