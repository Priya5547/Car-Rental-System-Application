package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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
public class forgot extends AppCompatActivity {
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap;
    EditText email;
    RadioButton cabuser,cabowner;
    Button forgot;
    View view;
    String semil,stype;
    String id,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fgtpswd);
        email=findViewById(R.id.editText7);
        forgot = findViewById(R.id.button3);
        cabuser=findViewById(R.id.cabuser);
        cabowner=findViewById(R.id.cabowner);
        hashMap = new HashMap();
        forgot.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v)
            {
                semil = email.getText().toString();
                if(cabowner.isChecked())
                {
                    type="cabowner";
                }
                else
                {
                    type="cabuser";
                }
                if (semil.isEmpty())
                {
                    Toast.makeText(forgot.this, "enter your email", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Log.d("email",semil+"\n"+type);
                    RequestQueue queue = Volley.newRequestQueue(forgot.this);
                    String url = "https://rideclub.000webhostapp.com/jigu/frgpwd.php?email="+semil+"&type="+type;
                    progressDialog = progressDialog.show(forgot.this, "", "updating...");
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", response);
                                    progressDialog.dismiss();
                                    Toast.makeText(forgot.this, response, Toast.LENGTH_SHORT).show();
                                    if(response.equals("password sent successfully"))
                                    {
                                        Intent intent = new Intent(forgot.this, loginpage.class);
                                        startActivity(intent);
                                    }
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                    queue.add(stringRequest);
                }
            }
        });
    }
}