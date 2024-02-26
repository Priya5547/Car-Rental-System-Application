package com.example.cdmi.login;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
public class add_car extends Fragment {
    public add_car() {
        // Required empty public constructor
    }
    String strid;
    EditText carname,address,pincode;
    RadioButton petrol,diessel;
    Button button;
    String[] arr,arrcity;
    ImageView imageView;
    Bitmap bitmap;
    Spinner city;
    String[] choose={"Camera","Gallery"};
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    String strCarname,straddress,strPincode,strimg="",strcity,strrate,strmodel,strtype;
    Spinner type;
    HashMap hashMap;
    View view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText rate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add_car, container, false);
        carname = view.findViewById(R.id.editText2);
        address = view.findViewById(R.id.editText);
        pincode =view. findViewById(R.id.editText1);
        rate = view.findViewById(R.id.rate);
        petrol =view. findViewById(R.id.petrol);
        diessel = view.findViewById(R.id.diesel);
        button = view.findViewById(R.id.btn);
        hashMap=new HashMap();
        imageView=view.findViewById(R.id.imageView);
        sharedPreferences=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        strid=sharedPreferences.getString("sid","");
        arr = getResources().getStringArray(R.array.model);
        type = view.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, R.id.text, arr);
        type.setAdapter(adapter);
        arrcity = getResources().getStringArray(R.array.city);
        city = view.findViewById(R.id.city);
        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), R.layout.spinner_item, R.id.text, arrcity);
        city.setAdapter(adapter2);
        if((ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                !=PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)
                        !=PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        builder=new AlertDialog.Builder(getActivity());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Choose From..");
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0)
                        {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePictureIntent, 1);
                        }
                        if(i==1)
                        {
                            Intent intent =new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent,2);
                        }
                    }

                });
                builder.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                strCarname = carname.getText().toString();
                straddress = address.getText().toString();
                strPincode = pincode.getText().toString();
                strmodel = type.getSelectedItem().toString();
                strrate = rate.getText().toString();
                if (petrol.isChecked()) {
                    strtype = "petrol";
                }
                else {
                    strtype = "diesel";
                }
                strcity = city.getSelectedItem().toString();
                if(strCarname.isEmpty())
                {
                    Toast.makeText(getContext(), "enter carname", Toast.LENGTH_SHORT).show();
                }
                else if (straddress.isEmpty())
                {
                    Toast.makeText(getContext(), "enter address", Toast.LENGTH_SHORT).show();
                }
                else if (strPincode.isEmpty())
                {
                    Toast.makeText(getContext(), "enter pincode", Toast.LENGTH_SHORT).show();
                }
                else if(strimg.isEmpty())
                {
                    Toast.makeText(getContext(), "enter image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url = "https://rideclub.000webhostapp.com/jigu/addcar.php";
                    progressDialog=ProgressDialog.show(getActivity(),"","Inserting...");
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response",response);
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    carname.setText("");
                                    rate.setText("");
                                    address.setText("");
                                    pincode.setText("");
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

                            hashMap.put("carname", strCarname);
                            hashMap.put("model", strmodel);
                            hashMap.put("rate", strrate);
                            hashMap.put("type", strtype);
                            hashMap.put("address", straddress);
                            hashMap.put("pincode", strPincode);
                            hashMap.put("city",strcity);
                            hashMap.put("image",strimg);
                            hashMap.put("oid",strid);
                            return hashMap;
                        }
                    };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                0,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);
                }
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            strimg=Base64.encodeToString(b, Base64.DEFAULT);
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte [] b=baos.toByteArray();
                strimg=Base64.encodeToString(b, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}