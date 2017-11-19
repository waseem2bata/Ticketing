package com.waseem.ticketing.activity.UserApp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.waseem.ticketing.activity.UserApp.app.AppConfig;
import com.waseem.ticketing.activity.UserApp.app.AppController;
import com.waseem.ticketing.activity.UserApp.helper.SessionManager;
import com.waseem.ticketing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by waseem on 2/15/2017.
 */

public class UsersPage extends Activity {



    private Userdata ud;
    private SessionManager session;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<taskItem> Taskitems;

    private ProgressDialog pDialog;
    private TextView namev;
    private TextView tasktotal;
    private int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Taskitems = new ArrayList<>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);





        session = new SessionManager(getApplicationContext());




        String id = Userdata.getInstance(this).getTAG_UID();
        String email = Userdata.getInstance(this).getTag_Email();
        String uName = Userdata.getInstance(this).getTag_Tokeen();
        namev = (TextView) findViewById(R.id.name);
        tasktotal = (TextView) findViewById(R.id.totaltasks);
        namev.setText(uName);
        GetTasks(id,email);





    }

    private void GetTasks(final String Id,final String email){
        StringRequest postRequest = new StringRequest(Request.Method.POST, AppConfig.URL_GetTask,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("tasks");

                            for (i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                taskItem item = new taskItem(
                                        o.getString("task"),o.getString("taskname")
                                );
                                Taskitems.add(item);
                                tasktotal.setText("Total tasks is : "+Integer.toString(i+1));

                            }
                            adapter = new adaptertask(Taskitems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id", String.valueOf(Id));
                params.put("user",email);
                return params;

            }

        };
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void logoutUser() {
        session.setLogin(false);


        Intent intent = new Intent(UsersPage.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}

