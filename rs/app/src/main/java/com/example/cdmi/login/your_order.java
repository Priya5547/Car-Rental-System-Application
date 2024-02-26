package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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
import static android.content.Context.MODE_PRIVATE;
public class your_order extends Fragment
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    ArrayList<String> id,uid,oid,cid,pid,name,mobileno,city,amount,time,status,emailid,typeuser,model,Rate,Type,Address,Pincode,picupdate,dropdate,picuptime,img;
    View view;
    ImageView imageView;
    Button button;
    ListView l;
    String id1;
    public your_order() {
        }
    DataPassListener mCallback;
    public interface DataPassListener{
        public void passorderData(String id, String uid, String cid, String oid, String p_id, String name, String time, String status, String emailid, String typeuser, String model, String Rate, String Type, String Address, String Pincode, String mobileno, String city, String amount, String picupdate, String dropdate, String img, String picuptime);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            ///throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_order, container, false);
        l=view.findViewById(R.id.list);
        sharedPreferences=getActivity().getSharedPreferences( "mypref",MODE_PRIVATE );
        editor=sharedPreferences.edit();
        id1=sharedPreferences.getString( "sid","" );
        get();
        return view;
    }
    void get()
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://rideclub.000webhostapp.com/jigu/viewallorder.php?uid="+id1;
        Log.d("url",url);
        progressDialog= ProgressDialog.show(getActivity(),"","Getting...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONArray(response);
                            id = new ArrayList();
                            uid = new ArrayList();
                            oid = new ArrayList();
                            cid = new ArrayList();
                            pid = new ArrayList();
                            name = new ArrayList();
                            mobileno = new ArrayList();
                            city = new ArrayList();
                            amount = new ArrayList();
                            picupdate = new ArrayList();
                            dropdate = new ArrayList();
                            picuptime = new ArrayList();
                            img = new ArrayList();
                            time = new ArrayList();
                            status = new ArrayList();
                            emailid = new ArrayList();
                            typeuser = new ArrayList();
                            model = new ArrayList();
                            Rate = new ArrayList();
                            Type = new ArrayList();
                            Address = new ArrayList();
                            Pincode = new ArrayList();
                            for (int i = 0; i < array.length(); i++)
                            {
                                JSONObject jsonObject = array.getJSONObject(i);
                                id.add(jsonObject.getString("id"));
                                oid.add(jsonObject.getString("oid"));
                                uid.add(jsonObject.getString("uid"));
                                cid.add(jsonObject.getString("cid"));
                                pid.add(jsonObject.getString("pid"));
                                name.add(jsonObject.getString("name"));
                                mobileno.add(jsonObject.getString("mobileno"));
                                city.add(jsonObject.getString("city"));
                                amount.add(jsonObject.getString("amount"));
                                picupdate.add(jsonObject.getString("picupdate"));
                                dropdate.add(jsonObject.getString("dropdate"));
                                picuptime.add(jsonObject.getString("picuptime"));
                                time.add(jsonObject.getString("time"));
                                status.add(jsonObject.getString("status"));
                                emailid.add(jsonObject.getString("emailid"));
                                typeuser.add(jsonObject.getString("typeuser"));
                                model.add(jsonObject.getString("model"));
                                Rate.add(jsonObject.getString("Rate"));
                                Type.add(jsonObject.getString("Type"));
                                Address.add(jsonObject.getString("Address"));
                                Pincode.add(jsonObject.getString("Pincode"));
                                img.add(jsonObject.getString("img"));
                            }
                            ordercar1 adapter=new ordercar1(getContext(),name,amount,city,img);
                            l.setAdapter(adapter);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
//                Log.d("error", error.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                mCallback.passorderData(id.get(position),uid.get(position),oid.get(position),cid.get(position), pid.get(position), time.get(position),status.get(position), emailid.get(position), typeuser.get(position),model.get(position),Rate.get(position),Type.get(position),Address.get(position),Pincode.get(position),name.get(position),mobileno.get(position),city.get(position),amount.get(position),picupdate.get(position),dropdate.get(position),img.get(position),picuptime.get(position));
            }
        });
    }
}