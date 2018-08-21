package com.wairdell.route.routetest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wairdell.route.RouteCenter;
import com.wairdell.route.annotation.Route;

import java.time.Instant;

@Route
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteCenter.startActivity(MainActivity.this, "login", null);
            }
        });

    }
}
