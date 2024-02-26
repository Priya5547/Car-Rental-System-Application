package com.example.cdmi.login;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
public class chnge_password extends Fragment {
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    HashMap hashMap;
    EditText old,newp,confirm;
    Button chng;
    View view;
    String sold,snew,sconfirm;
    String id,oldpassword;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate( R.layout.cngpswd, container, false );
        old=view.findViewById( R.id.old );
        newp=view.findViewById( R.id.newp );
        confirm=view.findViewById( R.id.confirm );
        chng=view.findViewById( R.id.chng );
        hashMap=new HashMap(  );
        sharedPreferences=getActivity().getSharedPreferences( "mypref",MODE_PRIVATE );
        editor=sharedPreferences.edit();
        id=sharedPreferences.getString( "sid","" );
        oldpassword=sharedPreferences.getString( "spassword","" );
        chng.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sold=old.getText().toString();
                snew=newp.getText().toString();
                sconfirm=confirm.getText().toString();
                if(sold.isEmpty())
                {
                    Toast.makeText( getActivity(), "enter old password", Toast.LENGTH_SHORT ).show();
                }
                else  if(snew.isEmpty())
                {
                    Toast.makeText( getActivity(), "enter old password", Toast.LENGTH_SHORT ).show();
                }
                else  if(sconfirm.isEmpty())
                {
                    Toast.makeText( getActivity(), "enter old password", Toast.LENGTH_SHORT ).show();
                }
                else if(!snew.equals( sconfirm ))
                {
                    Toast.makeText(getActivity() , "enter same password", Toast.LENGTH_SHORT ).show();
                }
               else if(!oldpassword.equals( sold ))
                {
                    Toast.makeText( getActivity(), "old password is wrong", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    String url ="https://rideclub.000webhostapp.com/jigu/chngpassword.php";
                    progressDialog=progressDialog.show(getContext(),"","updating...");
// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d( "response", response );
                                    progressDialog.dismiss();
                                    Toast.makeText( getContext(), response, Toast.LENGTH_SHORT ).show();
                                    editor.clear();
                                    editor.commit();
                                    Intent intent=new Intent( getContext(),loginpage.class );
                                    startActivity( intent );
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.d("error",error.getMessage());
                            progressDialog.dismiss();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            hashMap.put( "password",sconfirm );
                            hashMap.put( "id",id );
                            return hashMap;
                        }
                    };
                    queue.add(stringRequest);
                }
            }
        } );
        return view;
    }
}