package com.example.cdmi.login;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import static android.content.Context.MODE_PRIVATE;
public class Cab_fullView extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View view;
    ImageView img;
    TextView name,model,type,rate,address,pincode,city;
    Button button;
    paynowlistener mCallback;
    String sid,soid,srate,stype;
    public interface paynowlistener{
        public void passpaynowdata(String id,String o_id,String rate);
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mCallback = (paynowlistener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_cab_full_view, container, false);
        img=view.findViewById(R.id.img);
        name=view.findViewById(R.id.name);
        model=view.findViewById(R.id.model);
        type=view.findViewById(R.id.type);
        rate=view.findViewById(R.id.rate);
        address=view.findViewById(R.id.address);
        pincode=view.findViewById(R.id.pincode);
        city=view.findViewById(R.id.city);
        button=view.findViewById(R.id.booknow);
        sharedPreferences=getActivity().getSharedPreferences("mypref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        stype=sharedPreferences.getString("suser","");
        if(stype.equals("cabowner"))
        {
            button.setVisibility(View.INVISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mCallback.passpaynowdata(sid,soid,srate);
            }
        });
        return view;
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
            name.setText(args.getString("name"));
            model.setText("Model="+args.getString("model"));
            type.setText("Type="+args.getString("type"));
            rate.setText("Rate="+args.getString("rate"));
            address.setText("Address="+args.getString("address"));
            pincode.setText("Pincode="+args.getString("pincode"));
            city.setText("City="+args.getString("city"));
            String imgname="https://rideclub.000webhostapp.com/jigu/cab_image/"+args.getString("img");
            Picasso.get().load(imgname).into(img);
        }
    }
}