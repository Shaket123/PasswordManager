package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class search extends AppCompatActivity {

    private  SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<model> dataholder=new ArrayList<>();
    private  adapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final String name=getIntent().getStringExtra("name");
        final String image=getIntent().getStringExtra("image");
        searchView=findViewById(R.id.searchview);
        recyclerView=findViewById(R.id.rec);

        Cursor cursor=new dbhelper(this).getdata();
        while (cursor.moveToNext()){
            model obj=new model(cursor.getString(0),cursor.getString(1));
            dataholder.add(obj);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new adapter(this,dataholder));
        adp=new adapter(getApplicationContext(),dataholder,name,image);
        recyclerView.setAdapter(adp);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adp.getFilter().filter(newText);
                return false;
            }
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.botn);
        bottomNavigationView.setSelectedItemId(R.id.item2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item1:
                        Intent intent2 = new Intent(search.this,mainpage.class);
                        intent2.putExtra("name",name);
                        intent2.putExtra("image",image);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.item2:
                        //search
                        return true;


                    case R.id.item3:
                        Intent intent3 = new Intent(search.this,profile.class);
                        intent3.putExtra("name",name);
                        intent3.putExtra("image",image);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;
                    //
                    case R.id.item4:
                        Intent intent4 = new Intent(search.this,addnewpassword.class);
                        intent4.putExtra("name",name);
                        intent4.putExtra("image",image);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }
}