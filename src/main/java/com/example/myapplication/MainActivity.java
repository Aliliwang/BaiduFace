package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mbuttonlog;
    private Button mbuttonreg;
    private EditText meduser;
    private EditText medpwd;
    private static final int WRITE_PERMISSION = 0x01;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //String ss=msg.getData().getString("key");
                    Toast.makeText(MainActivity.this, R.string.logok, Toast.LENGTH_SHORT).show();
                    Intent intents = new Intent(MainActivity.this, PixActivity.class);
                    startActivity(intents);
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, R.string.loger, Toast.LENGTH_SHORT).show();
                    break;


            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbuttonlog = (Button) findViewById(R.id.log);
        mbuttonreg = (Button) findViewById(R.id.reg);

        mbuttonlog.setOnClickListener(this);
        mbuttonreg.setOnClickListener(this);

        meduser = (EditText) findViewById(R.id.user);
        medpwd = (EditText) findViewById(R.id.pwd);

       // requestWritePermission();


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log:
                String account = meduser.getText().toString();
                String pwd = medpwd.getText().toString();
                Log log = new Log(account, pwd, handler);
                log.start();
                break;
            case R.id.reg:
                Intent intent = new Intent(MainActivity.this, RegisteActivity.class);
                startActivity(intent);
                break;

        }

    }

   /* public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == WRITE_PERMISSION) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();

                finish();

            }

        }

    }

    private void requestWritePermission() {

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);

        }
    }*/


}