package com.tusi.qdcloudcontrol.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.tusi.qdcloudcontrol.R;
import com.tusi.qdcloudcontrol.jni.getroadinfo;

public class TestActivity extends AppCompatActivity {

    public static getroadinfo in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int test = new getroadinfo().java2c();
        Log.e("getroading",String.valueOf(test));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own aactifon:" + test , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }



}
