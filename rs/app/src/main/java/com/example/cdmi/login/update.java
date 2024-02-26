package com.example.cdmi.login;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class update extends Fragment
{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText name, email, phnoe;
    Button update;
    HashMap hashMap;
    String strname, stremail, strphone;
    ProgressDialog progressDialog;
    View view;
    String id, sname, semail, sphone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_update, container, false);

        sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        id = sharedPreferences.getString("sid", "");
        sname = sharedPreferences.getString("sname", "");
        sphone = sharedPreferences.getString("smobile", "");
        semail = sharedPreferences.getString("semail", "");

        Log.d("ID",id);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phnoe = view.findViewById(R.id.phno);
        update = view.findViewById(R.id.btnupdate);
        hashMap = new HashMap();

        name.setText(sname);
        email.setText(semail);
        phnoe.setText(sphone);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stremail = email.getText().toString();
                strname = name.getText().toString();
                strphone = phnoe.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url = "https://rideclub.000webhostapp.com/jigu/update.php";

                progressDialog = progressDialog.show(getContext(), "", "updating...");

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                             public void onResponse(String response)
                            {
                                Log.d("response", response);
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.getMessage());
                        progressDialog.dismiss();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        hashMap.put("name", strname);
                        hashMap.put("email", stremail);
                        hashMap.put("phone", strphone);
                        hashMap.put("id", id);

                        return hashMap;
                    }
                };
                queue.add(stringRequest);
            }
        });
        return view;
    }


}