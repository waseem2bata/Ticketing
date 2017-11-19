package com.waseem.ticketing.activity.UserApp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.waseem.ticketing.R;
import com.waseem.ticketing.activity.UserApp.app.AppConfig;
import com.waseem.ticketing.activity.UserApp.app.AppController;
import com.waseem.ticketing.activity.UserApp.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by waseem on 2/18/2017.
 */

public class UsersManager extends AppCompatActivity {



    private static final String TAG = UsersManager.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private  List<listItem> listitems;

    public Context context = this;

    private ProgressDialog pDialog;
    private FloatingActionButton fab_register;
    private SessionManager session;
    private EditText fullname;
    private EditText email;
    private EditText password;
    private EditText test;
    public String token;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());


        token = FirebaseInstanceId.getInstance().getToken();
        test = (EditText) findViewById(R.id.test);




        fab_register = (FloatingActionButton) findViewById(R.id.fabregister);

        fab_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.register);
                dialog.setTitle("Title...");

                fullname = (EditText) dialog.findViewById(R.id.Fullname);
                email = (EditText) dialog.findViewById(R.id.email);
                password = (EditText) dialog.findViewById(R.id.paswordreg);






                Button dialogButton = (Button) dialog.findViewById(R.id.confirm);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        assignstrings(dialog);
                        listitems.clear();

                    }
                });

                dialog.show();
                 Button no = (Button) dialog.findViewById(R.id.no);

                no.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        listitems.clear();
                        makeJsonArrayRequest();
                    }
                });
            }
        });

        listitems = new ArrayList<>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        makeJsonArrayRequest();
    }

    private void assignstrings(Dialog d){
        String fname = fullname.getText().toString().trim();
        String femail = email.getText().toString().trim();
        String fpassword = password.getText().toString().trim();
        if (!fname.isEmpty() && !femail.isEmpty() && !fpassword.isEmpty()) {
            registerUser(fname, femail, fpassword);
            d.dismiss();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void makeJsonArrayRequest() {

        showpDialog();
        listitems.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                AppConfig.URL_Displau,
                new Response.Listener<String>(){
                    @Override
                    public  void onResponse(String s){
                        hidepDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("users");

                            for (int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                listItem item = new listItem(
                                        o.getString("name"),
                                        o.getString("email")
                                );
                                listitems.add(item);
                            }
                            adapter = new MyAdapter(listitems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new  Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError vollyerror){
                    Toast.makeText(getApplicationContext(),vollyerror.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void registerUser(final String name, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showpDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hidepDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String Tokem = user.getString("token");
                        // Inserting row in users table


                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        makeJsonArrayRequest();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                Log.d("s",params.toString());
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}