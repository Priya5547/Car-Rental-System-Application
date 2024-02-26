package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class loginpage extends AppCompatActivity {
    TextView forgot,register;
    EditText e1,e2;
    RadioButton cabowner,cabuser;
    JSONObject jsonObject;
    String name,email,status,type;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String sid,sname,semail,spassword,smobile,sgender,scity,suser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        cabowner=findViewById(R.id.radioButton6);
        cabuser=findViewById(R.id.radioButton5);
        forgot=findViewById(R.id.forgot);
        register=findViewById(R.id.register);
       sharedPreferences=getSharedPreferences("mypref",MODE_PRIVATE);
       editor=sharedPreferences.edit();
       if(sharedPreferences.contains("sid"))
       {
           Intent intent=new Intent(loginpage.this,Home.class);
           startActivity(intent);
           finish();
       }
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginpage.this,forgot.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginpage.this,registerpage.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void login_fun(View view) {
        String stremail,strpass;
        stremail=e1.getText().toString();
        strpass=e2.getText().toString();
        if(cabowner.isChecked())
        {
            type="cabowner";
        }
        else
        {
            type="cabuser";
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://rideclub.000webhostapp.com/jigu/login.php?email="+stremail+"&password="+strpass+"&type="+type;
        Log.d("url",url);
        final ProgressDialog p=ProgressDialog.show(this,"Logging...","");
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        p.dismiss();
                        try {
                            jsonObject=new JSONObject(response);
                            status=jsonObject.getString("status");
                            if(status.equals("true"))
                            {
                                JSONObject jsonObject1=jsonObject.getJSONObject("0");
                                sid=jsonObject1.getString("id");
                                sname=jsonObject1.getString("name");
                                semail=jsonObject1.getString("emailid");
                                spassword=jsonObject1.getString("password");
                                smobile=jsonObject1.getString("mobileno");
                                sgender=jsonObject1.getString("gender");
                                scity=jsonObject1.getString("city");
                                suser=jsonObject1.getString("typeuser");
                                editor.putString("sid",sid);
                                editor.putString("sname",sname);
                                editor.putString("semail",semail);
                                editor.putString("spassword",spassword);
                                editor.putString("smobile",smobile);
                                editor.putString("sgender",sgender);
                                editor.putString("scity",scity);
                                editor.putString("suser",suser);
                                editor.commit();
                                Intent intent=new Intent(loginpage.this,Home.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(loginpage.this,"Invalid User and Password",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                p.dismiss();
                Toast.makeText(loginpage.this,""+error,Toast.LENGTH_SHORT).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}