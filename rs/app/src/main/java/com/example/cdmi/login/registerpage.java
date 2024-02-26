package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
public  class registerpage extends AppCompatActivity {
    ProgressDialog progressDialog;
    Spinner spinner;
    EditText e1,e2,e3;
    RadioButton male,female,cabowner,cabuser;
    String[] arr;
    String name,email,mobileno,gender,city,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        e1=findViewById(R.id.editText3);
        e2=findViewById(R.id.editText4);
        e3=findViewById(R.id.editText5);
        male=findViewById(R.id.radioButton);
        female=findViewById(R.id.radioButton2);
        cabowner=findViewById(R.id.radioButton3);
        cabuser=findViewById(R.id.radioButton4);
        arr=getResources().getStringArray(R.array.city);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.spin,R.id.text,arr);
        spinner.setAdapter(adapter);
    }
    public void data_save(View view) {
        name=e1.getText().toString();
        email=e2.getText().toString();
        mobileno=e3.getText().toString();
        if(male.isChecked())
        {
            gender="male";
        }
        else
        {
            gender="female";
        }
        city=spinner.getSelectedItem().toString();
        if(cabowner.isChecked())
        {
            type="cabowner";
        }
        else
        {
            type="cabuser";
        }

        if(name.isEmpty())
        {
            Toast.makeText(this, "enter name", Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty())
        {
            Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
        }
        else if(mobileno.isEmpty())
        {
            Toast.makeText( this, "enter mobileno", Toast.LENGTH_SHORT).show();
        }
        else
        {
            progressDialog=ProgressDialog.show(this,"","Inserting...");
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="https://rideclub.000webhostapp.com/jigu/regi.php?name="+name+"&email="+email+"&mobilno="+mobileno+"&gender="+gender+"&city="+city+"&type="+type;
            Log.d("url",url);
           StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d("response",response);
                            progressDialog.dismiss();
                            Intent intent=new Intent(registerpage.this,loginpage.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error",error.getMessage());
                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }
    }
}

