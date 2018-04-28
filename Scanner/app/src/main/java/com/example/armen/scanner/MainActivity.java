package com.example.armen.scanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    private final static int REQUEST_CODE = 100;
    private final static int PERMISSION_REQUEST  = 200;
    private FloatingActionButton scanBtn;
    private FloatingActionButton btnGet;
    private RecyclerView recyclerView;
    private ArrayList<String> list;
//    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            list = savedInstanceState.getStringArrayList("list");
        }else {
            list = new ArrayList<>();

        }
        
        recyclerView = findViewById(R.id.recycler);
        final HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dbHelper = DBHelper.newInstance(this);
        scanBtn =  findViewById(R.id.btn_scanner);
        btnGet = findViewById(R.id.btn_history);
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list1 = dbHelper.getQRCode();
                if(list1.size() > 0) {
                    list.clear();
                    list.addAll(list1);

                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("list", list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Log.i("ssssssssss", "resultCode == RESULT_OK && requestCode == REQUEST_CODE");
            if(data != null) {
                final String barcode = data.getStringExtra("barcode");
                dbHelper.addQRCode(barcode);
            }
        }
    }
}