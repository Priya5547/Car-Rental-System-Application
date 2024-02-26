package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
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
public class mycar extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    GridView listView;
    View view;
    String strid;
    ArrayList<String> id,o_id,name,model,rate,type,address,pincode,img,city;
    ProgressDialog progressDialog;
    public mycar() {
        // Required empty public constructor
    }
    view_car.DataPassListener mCallback;
    public interface DataPassListener{
        public void passData(String id,String o_id,String name,String model,String rate,String type,String address,String pincode,String img,String city);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (view_car.DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewcar, container, false);
        listView=view.findViewById(R.id.listview);
        sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        strid=sharedPreferences.getString("sid","");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://rideclub.000webhostapp.com/jigu/viewmycar.php?id="+strid;
        progressDialog = ProgressDialog.show(getActivity(), "", "Inserting...");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        progressDialog.dismiss();
                        try {
                            JSONArray array=new JSONArray(response);
                            name=new ArrayList();
                            o_id=new ArrayList();
                            model=new ArrayList();
                            rate=new ArrayList();
                            address=new ArrayList();
                            type=new ArrayList();
                            pincode=new ArrayList();
                            img=new ArrayList();
                            city=new ArrayList();
                            id=new ArrayList();
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject jsonObject=array.getJSONObject(i);
                                id.add(jsonObject.getString("id"));
                                o_id.add(jsonObject.getString("o_id"));
                                name.add(jsonObject.getString("name"));
                                model.add(jsonObject.getString("model"));
                                rate.add(jsonObject.getString("Rate"));
                                type.add(jsonObject.getString("Type"));
                                address.add(jsonObject.getString("Address"));
                                pincode.add(jsonObject.getString("Pincode"));
                                img.add(jsonObject.getString("img"));
                                city.add(jsonObject.getString("city"));
                            }
                            myadepter adapter=new myadepter(getContext(),name,city,model,rate,img);
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("error", error.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                2000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                mCallback.passData(id.get(position),o_id.get(position),name.get(position),model.get(position),rate.get(position),type.get(position),address.get(position),pincode.get(position),img.get(position),city.get(position));
            }
        });
        return view;
    }
}