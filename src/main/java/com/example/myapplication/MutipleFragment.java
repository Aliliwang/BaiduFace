package com.example.myapplication;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class MutipleFragment extends Fragment {
private ImageView photo;
private Button commit;
private String uploadFileName;
private byte[] fileBuf;
private Bitmap bitmap;
private String name;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getActivity(), "查无结果", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    int num=charnumber(name);
                    String[] strArr=name.split("\\#");
                    Bitmap bit=bitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Canvas canvas=new Canvas(bit);
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
                    paint.setTextSize(42.0f);
                    paint.setColor(Color.RED);
                    paint.setFakeBoldText(false);
                    for(int i=0;i<num;i++){
                    float x=Float.parseFloat(strArr[3*i+1]);
                    float y=Float.parseFloat(strArr[3*i+2]);
                    canvas.drawText(strArr[3*i],x,y,paint);}
                    photo.setImageBitmap(bit);
                    break;
            }
        }

        ;
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if(data==null)
                    return;
                handleSelect(data);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v=inflater.inflate(R.layout.mutiple_fragment, container, false);
        photo=(ImageView) v.findViewById(R.id.multiply);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                //进行sdcard的读写请求
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 1);
                } else {
                    openGallery(); //打开相册，进行选择
                }
            }

        });
        commit=(Button)v.findViewById(R.id.multiplycommit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try
                        {
                            Socket socket = new Socket("116.62.206.214", 9527);
                            OutputStream os=socket.getOutputStream();
                            String str="mutiply#";
                            os.write(str.getBytes());
                            os.flush();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                            byte[] bytes = baos.toByteArray();
                            os.write(bytes);
                            os.flush();
                            socket.shutdownOutput();

                            InputStream in=socket.getInputStream();
                            BufferedReader br=new BufferedReader(new InputStreamReader(in));
                            name=br.readLine();

                            os.close();
                            socket.close();



                        }catch(IOException e)
                        {
                            e.printStackTrace();

                        }
                        if(name.equals("error")) {
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                        else
                        {
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }


                    }
                }.start();
            }
        });



        return v;
    }


    private byte[] convertToBytes(InputStream inputStream) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        inputStream.close();
        return  out.toByteArray();
    }




    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {
                    Toast.makeText(getActivity(), "读相册的操作被拒绝", Toast.LENGTH_LONG).show();
                }
        }
    }




    private void openGallery() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }




    private void handleSelect(Intent intent) {
        Cursor cursor = null;
        Uri uri = intent.getData();
        cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
            uploadFileName = cursor.getString(columnIndex);

        }
        try {
            InputStream inputStream =getActivity().getContentResolver().openInputStream(uri);
            fileBuf=convertToBytes(inputStream);
            bitmap = BitmapFactory.decodeByteArray(fileBuf, 0, fileBuf.length);
            photo.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
    }
    private int charnumber(String str){
        int len=str.length();
        int num=0;
        for(int i=0;i<len;i++){
            if(str.charAt(i)=='#')
            {
              num++;
            }
        }

        return num/3;

    }



}
