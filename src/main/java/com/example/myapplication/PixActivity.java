package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class PixActivity extends AppCompatActivity {
private List<Menu> menuList = new ArrayList<Menu>();
private ListView listview;
private FragmentManager fm;
private Fragment ufragment;
private Fragment sfragment;
private Fragment mfragment;
private Fragment pfragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);
        fm=getSupportFragmentManager();

        initMenu();
        MenuAdapter adapter = new MenuAdapter(PixActivity.this,
                R.layout.list_layout, menuList);
        listview = (ListView) findViewById(R.id.ListView);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                 if(position==0){
                                     selectFragment(0);
                                 }if(position==1){
                                     selectFragment(1);
                                 }if (position==2){
                                     selectFragment(2);
                                 }if (position==3){
                                     selectFragment(3);
                                 }
                                 }
        });
    }


    private void initMenu() {
        Menu menu1 = new Menu("上传图片",
                R.drawable.upload);
        menuList.add(menu1);
        Menu menu2 = new Menu("单人识别",
                R.drawable.single);
        menuList.add(menu2);
        Menu menu3 = new Menu("多人识别",
                R.drawable.multiple);
        menuList.add(menu3);
        Menu menu4 = new Menu("测颜",
                R.drawable.pk);
        menuList.add(menu4);

    }
    private void selectFragment(int index){
        FragmentTransaction ts=fm.beginTransaction();
        hideFragment(ts);
        switch(index){
            case 0:
                if(ufragment==null)
                {ufragment=new UploadFragment();
                    ts.add(R.id.Frame,ufragment);}
                else {
                    ts.show(ufragment);
                    }
                break;
            case 1:
                if(sfragment==null)
                {sfragment=new SingleFragment();
                    ts.add(R.id.Frame,sfragment);}
                else {
                    ts.show(sfragment);
                }
                break;
            case 2:
                if(mfragment==null)
                {mfragment=new MutipleFragment();
                    ts.add(R.id.Frame,mfragment);}
                else {
                    ts.show(mfragment);
                }
                break;
            case 3:
                if(pfragment==null)
                {pfragment=new PKFragment();
                    ts.add(R.id.Frame,pfragment);}
                else {
                    ts.show(pfragment);
                }
                break;





        }
        ts.commit();
    }
    private void hideFragment(FragmentTransaction transaction){
        if (ufragment!= null) {
            transaction.hide(ufragment);
        }
        if (sfragment!= null) {
            transaction.hide(sfragment);
        }
        if (mfragment!= null) {
            transaction.hide(mfragment);
        }
        if (pfragment!= null) {
            transaction.hide(pfragment);
        }

    }


}
