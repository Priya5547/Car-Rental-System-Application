package com.example.cdmi.login;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;
public class booknow extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String sid,soid,srate,suid;
    int finalrate;
    Button pickupdate,time,dropdate,paynow;
    View view;
    TextView pickuptext,textView2,droptext,rr;
    Calendar myCalendar;
    String date1,date2,dayDifference;
     String paymentAmount;
    ProgressDialog progressDialog;
    HashMap hashMap;
    changelistener mCallback;
    public interface changelistener{
        public void change_method();
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallback = (changelistener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }
    public booknow()
    {
    }
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_booknow, container, false);
        myCalendar = Calendar.getInstance();
        hashMap=new HashMap();
        pickupdate=view.findViewById(R.id.pickupdate);
        time=view.findViewById(R.id.time);
        pickuptext=view.findViewById(R.id.pickuptext);
        textView2=view.findViewById(R.id.text2);
        dropdate=view.findViewById(R.id.dropdate);
        droptext=view.findViewById(R.id.droptext);
        paynow=view.findViewById(R.id.paynow);
        rr=view.findViewById(R.id.rr);
        sharedPreferences = getActivity().getSharedPreferences("mypref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        suid = sharedPreferences.getString("sid", "");
        Intent intent = new Intent(getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getContext().startService(intent);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickuptext.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "enter picupdate", Toast.LENGTH_SHORT).show();
                }
                else if (droptext.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "enter dropdate", Toast.LENGTH_SHORT).show();
                }
                else if (textView2.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "enter time", Toast.LENGTH_SHORT).show();
                }
                else {

                    finalrate = Integer.parseInt(srate) * Integer.parseInt(dayDifference);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Final Rate is:" + finalrate);
                    builder.setMessage("Do u want to Cotinue For Payment?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getPayment();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();
                }
            }
        });
        pickupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        date1=sdf.format(myCalendar.getTime());
                        pickuptext.setText(date1);
                    }

                };
                new DatePickerDialog(getContext(), datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        dropdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        date2=sdf.format(myCalendar.getTime());
                        droptext.setText(date2);
                        Date d1;
                        Date d2;
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
                        //Setting dates
                        try {
                            d1 = dates.parse(date1);
                            d2 = dates.parse(date2);
                            //Comparing dates
                            long difference = Math.abs(d1.getTime() - d2.getTime());
                            long differenceDates = difference / (24 * 60 * 60 * 1000);
                            //Convert long to String
                             dayDifference = Long.toString(differenceDates);
                            Log.e("HERE","HERE: " + dayDifference);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                };
                new DatePickerDialog(getContext(), datepicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
                int minute = myCalendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textView2.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        return view;
    }
    private void getPayment() {
        //Getting the amount from editText
        paymentAmount = String.valueOf(finalrate);
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", " Cab Fees",
                PayPalPayment.PAYMENT_INTENT_SALE);
        //Creating Paypal Payment activity intent
        Intent intent = new Intent(getContext(), PaymentActivity.class);
        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {
            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                //if confirmation is not null
                if (confirm != null) {
                    //Getting the payment details
                    String paymentDetails = confirm.toJSONObject().toString();
                    Log.i("paymentExample", paymentDetails);
                    try {
                        JSONObject jsonObject=new JSONObject(paymentDetails);
                        JSONObject jsonObject1=jsonObject.getJSONObject("response");
                        final String time=jsonObject1.getString("create_time");
                        final  String pid=jsonObject1.getString("id");
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            String url = "https://rideclub.000webhostapp.com/jigu/payment.php";
                            progressDialog = ProgressDialog.show(getActivity(), "", "Inserting...");
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("response", response);
                                            progressDialog.dismiss();
                                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setMessage("Payment Successfully");
                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    builder.setCancelable(true);
                                                    mCallback.change_method();
                                                }
                                            });
                                            builder.show();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                              //      Log.d("error", error.getMessage());
                                }
                            })
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError
                                {
                                    hashMap.put("time", time);
                                    hashMap.put("pid", pid);
                                    hashMap.put("id", sid);
                                    hashMap.put("soid", soid);
                                    hashMap.put("rate", String.valueOf(finalrate));
                                    hashMap.put("uid",suid);
                                    hashMap.put("picupdate",pickuptext.getText().toString());
                                    hashMap.put("dropdate",droptext.getText().toString());
                                    hashMap.put("picuptime",textView2.getText().toString());
                                    Log.d("rate", String.valueOf(hashMap));
                                    return hashMap;
                                }
//                                @Override
//                                public Map<String, String> getHeaders() throws AuthFailureError {
//                                    HashMap<String, String> headers = new HashMap<String, String>();
//                                    headers.put("Content-Type", "application/json; charset=utf-8");
//                                    return headers;
//                                }
                            };
                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    0,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the RequestQueue.
                            queue.add(stringRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null)
        {
            sid=args.getString("id");
            soid=args.getString("o_id");
            srate=args.getString("rate");
            rr.setText("Rate = "+srate+" per day");
        }
    }
}