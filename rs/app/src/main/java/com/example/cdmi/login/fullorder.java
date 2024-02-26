package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static android.content.Context.MODE_PRIVATE;
public class fullorder extends Fragment {
    TextView  pid, name, mobileno,model,Pincode, city, amount, picupdate, dropdate, picuptime;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView img;
    View view;
    ProgressDialog progressDialog;
    Button accept,cancel;
    String status,id;
    public fullorder() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fullorder, container, false);
        sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        view = inflater.inflate(R.layout.fragment_fullorder, container, false);
        img = view.findViewById(R.id.img);
        name = view.findViewById(R.id.name);
        //id = view.findViewById(R.id.id);
        //oid = view.findViewById(R.id.oid);
        //uid = view.findViewById(R.id.uid);
        amount = view.findViewById(R.id.rate);
        accept=view.findViewById(R.id.accept);
        cancel=view.findViewById(R.id.cancel);
        //cid = view.findViewById(R.id.cid);
        pid = view.findViewById(R.id.p_id);
        city = view.findViewById(R.id.city);
        mobileno = view.findViewById(R.id.mobileno);
        picupdate = view.findViewById(R.id.picupdate);
        dropdate = view.findViewById(R.id.dropdate);
        picuptime = view.findViewById(R.id.picuptime);
        mobileno = view.findViewById(R.id.mobileno);
        //time = view.findViewById(R.id.time);
        //status = view.findViewById(R.id.status);
        //emailid = view.findViewById(R.id.emailid);
        //typeuser = view.findViewById(R.id.typeuser);
        model = view.findViewById(R.id.model);
        //Rate = view.findViewById(R.id.Rate);
        //Type = view.findViewById(R.id.Type);
        //Address = view.findViewById(R.id.Address);
        Pincode = view.findViewById(R.id.Pincode);
        sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Bundle args = getArguments();
        if (args != null) {
            status=args.getString("status");
            id= args.getString("id");
            name.setText("Name=" + args.getString("name"));
            //id.setText("Id=" + args.getString("id"));
            //oid.setText("O_id=" + args.getString("oid"));
            //uid.setText("U_id=" + args.getString("uid"));
            pid.setText("Payment_id=" + args.getString("pid"));
            //cid.setText("C_id=" + args.getString("cid"));
            mobileno.setText("Mobile_no=" + args.getString("mobileno"));
            amount.setText("Amount=" + args.getString("amount"));
            city.setText("City=" + args.getString("city"));
            picupdate.setText("Picup_date=" + args.getString("picupdate"));
            dropdate.setText("Dropdate=" + args.getString("dropdate"));
            picuptime.setText("Pickup_time=" + args.getString("picuptime"));
            //time.setText("Time=" + args.getString("time"));
            //status.setText("Status=" + args.getString("status"));
            //emailid.setText("Emailid=" + args.getString("emailid"));
            //typeuser.setText("Typeuser=" + args.getString("typeuser"));
            //model.setText("Model=" + args.getString("model"));
            //Rate.setText("Rate=" + args.getString("Rate"));
            //Type.setText("Type=" + args.getString("Type"));
            //Address.setText("Address=" + args.getString("Address"));
            Pincode.setText("Pincode=" + args.getString("Pincode"));
            String imgname = "https://rideclub.000webhostapp.com/jigu/cab_image/" + args.getString("img");
            Picasso.get().load(imgname).into(img);
            if(status.equals("1"))
            {
                accept.setText("Accepted");
                accept.setClickable(false);
            }
            if(status.equals("2"))
            {
                cancel.setText("Canceled");
                cancel.setClickable(false);
            }
            //get id ,status
            //update payment set status=$status where id=$id
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url = "https://rideclub.000webhostapp.com/jigu/changestatus.php?id="+id+"&status="+1; ;
                    Log.d("url",url);
                    progressDialog= ProgressDialog.show(getActivity(),"","Getting...");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response",response);
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            //  Log.d("error", error.getMessage());
                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            2000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url = "https://rideclub.000webhostapp.com/jigu/changestatus.php?id="+id+"&status="+2; ;
                    Log.d("url",url);
                    progressDialog= ProgressDialog.show(getActivity(),"","Getting...");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response",response);
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.d("error", error.getMessage());
                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            2000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            });
        }
        return view;
    }
}