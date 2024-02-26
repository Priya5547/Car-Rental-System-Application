package com.example.cdmi.login;
import android.app.ProgressDialog;
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
public class cnt_us extends Fragment
{
    public cnt_us()
    {
        // Required empty public constructor
    }
    EditText name,email,contact,message;
    Button button;
    String[] arr;
    ProgressDialog progressDialog;
    String strname,stremail,strcontact,strmsg;
    HashMap hashMap;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cnt_us, container, false);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        contact = view.findViewById(R.id.contact);
        message = view.findViewById(R.id.message);
        button = view.findViewById(R.id.submit);
        hashMap = new HashMap();
       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname = name.getText().toString();
                stremail = email.getText().toString();
                strcontact = contact.getText().toString();
                strmsg = message.getText().toString();
                if (strname.isEmpty()) {
                    Toast.makeText(getContext(), "enter name", Toast.LENGTH_SHORT).show();
                } else if (stremail.isEmpty()) {
                    Toast.makeText(getContext(), "enter email", Toast.LENGTH_SHORT).show();
                } else if (strcontact.isEmpty()) {
                    Toast.makeText(getContext(), "enter contact", Toast.LENGTH_SHORT).show();
                } else if (strmsg.isEmpty()) {
                    Toast.makeText(getContext(), "enter message", Toast.LENGTH_SHORT).show();
                } else {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url = "https://rideclub.000webhostapp.com/jigu/contact_us.php";
                    progressDialog = ProgressDialog.show(getActivity(), "", "Inserting...");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", response);
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    name.setText("");
                                    email.setText("");
                                    contact.setText("");
                                    message.setText("");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.d("error", error.getMessage());
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            hashMap.put("name", strname);
                            hashMap.put("email", stremail);
                            hashMap.put("contact", strcontact);
                            hashMap.put("message", strmsg);
                            return hashMap;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        });
        return view;
    }
}