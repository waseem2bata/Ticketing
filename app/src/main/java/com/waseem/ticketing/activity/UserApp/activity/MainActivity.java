package com.waseem.ticketing.activity.UserApp.activity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.waseem.ticketing.R;
import com.waseem.ticketing.activity.UserApp.app.AppConfig;
import com.waseem.ticketing.activity.UserApp.helper.SessionManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

import static android.R.attr.port;


public class MainActivity extends AppCompatActivity {

    private int sec = 1000;
    private SessionManager session;
    KenBurnsView image;
    boolean trs;
    NetworkInfo netinfo;
    ConnectivityManager cm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        session = new SessionManager(getApplicationContext());
        Checkforlog();

        //    image = (KenBurnsView) findViewById(R.id.testss);
    }

   public  void Checkforlog (){
       Handler handler = new Handler();
       handler.postDelayed(new Runnable() {
           public void run() {
               if (!(session.isLoggedIn())) {
                   logoutUser();
               }
               else {
                   Intent intent = new Intent(MainActivity.this, UsersPage.class);
                   startActivity(intent);
                   finish();
               }
           }
       }, 1000);

}

private void logoutUser() {
        session.setLogin(false);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
