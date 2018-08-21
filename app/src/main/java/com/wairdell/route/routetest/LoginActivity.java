package com.wairdell.route.routetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wairdell.route.annotation.Route;

@Route("login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
