package com.waseem.ticketing.activity.UserApp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.waseem.ticketing.R;
import com.waseem.ticketing.activity.UserApp.app.AppConfig;
import com.waseem.ticketing.activity.UserApp.app.AppController;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by waseem on 6/13/2017.
 */


public class AddTask  extends AppCompatActivity {

    private EditText tasktitle, taskdetaisl;
    private ProgressDialog pDialog;
    private Bundle b;
    private String Email;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtaskl);
        TextView test = (TextView) findViewById(R.id.testt);
        tasktitle = (EditText) findViewById(R.id.tasktitle);
        taskdetaisl = (EditText) findViewById(R.id.taskdetails);
        Button send = (Button) findViewById(R.id.sendtaskbutton);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        b = getIntent().getExtras();
        Email = b.getString("text");
        Log.d("sss",Email);

        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                sendSinglePush(Email);
                storetask(Email);
            }

        });
    }
    private void sendSinglePush(final String e) {
        final String title = tasktitle.getText().toString();
        final String message = taskdetaisl.getText().toString();


        pDialog.setMessage("Sending Push");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        Toast.makeText(AddTask.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);
                params.put("email",e);
                Log.d("s",params.toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private  void storetask(final String e){
        final String message = taskdetaisl.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_ADDTASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(AddTask.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("nn",error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("task", message);
                params.put("email",e);
                params.put("id", id);

                Log.d("dddd",params.toString());

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
