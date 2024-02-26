package com.example.cdmi.login;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
public class track_order extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    ImageView imageView;
    View view;
    Spinner spinner;
    ArrayList<String> id,o_id,cid,name,model,rate,type,time,address,pincode,img,city;
    ListView l;
    String arr[]={"ALL","PENDING","ACCEPTED","CANCELED"};
    //0-pending 1-accept 2-cancel 4-all
    public track_order() {
    }
    DataPassListener mCallback;
    public interface DataPassListener{
        public void passData(String id,String o_id,String cid,String name,String model,String rate,String type,String time,String address,String pincode,String img,String city);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallback = (track_order.DataPassListener) context;
        }
        catch (ClassCastException e)
        {
           // throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
      }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_your_order, container, false);
        spinner=view.findViewById(R.id.spinner);
        ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arr);
        spinner.setAdapter(adapter);
        l=view.findViewById(R.id.list);
        sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
       // get(4);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("pos=",position+"");
                if(position==0)
                {
                    get(4);
                }
                else if(position==1)
                {
                    get(0);
                }
                else if(position==2)
                {
                    get(1);
                }
                else if(position==3)
                {
                    get(2);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }
    void get(int no)
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://rideclub.000webhostapp.com/jigu/vieworder.php?id="+no;
        Log.d("url",url);
        progressDialog= ProgressDialog.show(getActivity(),"","Getting...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        progressDialog.dismiss();
                        try {
                            JSONArray array=new JSONArray(response);
                            name=new ArrayList();
                            o_id=new ArrayList();
                            cid=new ArrayList();
                            model=new ArrayList();
                            rate=new ArrayList();
                            address=new ArrayList();
                            type=new ArrayList();
                            time=new ArrayList();
                            pincode=new ArrayList();
                            img=new ArrayList();
                            city=new ArrayList();
                            id=new ArrayList();
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject jsonObject=array.getJSONObject(i);
                                id.add(jsonObject.getString("id"));
                                o_id.add(jsonObject.getString("o_id"));
                                cid.add(jsonObject.getString("cid"));
                                name.add(jsonObject.getString("name"));
                                model.add(jsonObject.getString("model"));
                                rate.add(jsonObject.getString("Rate"));
                                type.add(jsonObject.getString("Type"));
                                time.add(jsonObject.getString("time"));
                                address.add(jsonObject.getString("Address"));
                                pincode.add(jsonObject.getString("Pincode"));
                                img.add(jsonObject.getString("img"));
                                city.add(jsonObject.getString("city"));
                            }
                            ordercar1 adapter=new ordercar1(getContext(),name,model,rate,img);
                            l.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
    }
        });
        queue.add(stringRequest);
    }
}