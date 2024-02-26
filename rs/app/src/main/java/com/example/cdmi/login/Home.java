package com.example.cdmi.login;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
public class Home extends AppCompatActivity  implements view_car.DataPassListener,Cab_fullView.paynowlistener,booknow.changelistener,your_order.DataPassListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView tname,temail;
    View view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String name,email,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation);
        sharedPreferences=getSharedPreferences("mypref",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.black));
        view=navigationView.getHeaderView(0);
        tname=view.findViewById(R.id.name);
        temail=view.findViewById(R.id.email);
        name=sharedPreferences.getString("sname","");
        email=sharedPreferences.getString("semail","");
        type=sharedPreferences.getString("suser","");
        if(type.equals("cabuser"))
        {
            navigationView.getMenu().findItem(R.id.add).setVisible(false);
            navigationView.getMenu().findItem(R.id.mycar).setVisible(false);
            navigationView.getMenu().findItem(R.id.urorder).setVisible(false);
        }
        if(type.equals("cabowner"))
        {
            navigationView.getMenu().findItem(R.id.trckorder).setVisible(false);
        }
        tname.setText(name);
        temail.setText(email);
        getSupportFragmentManager().beginTransaction().replace(R.id.linear,new view_car()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                if(item.getItemId()==R.id.logout)
                {
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(Home.this,loginpage.class);
                    startActivity(intent);
                    finish();
                }
                if(item.getItemId()==R.id.edit)
                {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linear,new update()).commit();
                }
                if(item.getItemId()==R.id.add)
                {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linear,new add_car()).commit();
                }
                if(item.getItemId()==R.id.chng)
                {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linear,new chnge_password()).commit();
                }
                if(item.getItemId()==R.id.viewcar)
                {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linear,new view_car()).commit();
                }
                if(item.getItemId()==R.id.mycar)
                {
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.linear,new mycar()).commit();
                }
                if(item.getItemId()==R.id.cntus)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.linear,new cnt_us()).commit();
                }
                if(item.getItemId()==R.id.trckorder)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.linear,new track_order()).commit();
                }
                if(item.getItemId()==R.id.urorder)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.linear,new your_order()).commit();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
    @Override
    public void  passData(String id,String o_id,String name,String model,String rate,String type,String address,String pincode,String img,String city) {
        Cab_fullView fragmentB = new Cab_fullView ();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("o_id", o_id);
        args.putString("name", name);
        args.putString("model", model);
        args.putString("rate", rate);
        args.putString("type", type);
        args.putString("address", address);
        args.putString("pincode", pincode);
        args.putString("img", img);
        args.putString("city", city);
        fragmentB .setArguments(args);
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.linear, fragmentB )
                .commit();
    }
    @Override
    public void passpaynowdata(String id, String o_id, String rate) {
        booknow fragmentB = new booknow ();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("o_id", o_id);
        args.putString("rate", rate);
        fragmentB .setArguments(args);
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.linear, fragmentB )
                .commit();
    }
    @Override
    public void change_method() {
        track_order fragmentB = new track_order();
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.linear, fragmentB )
                .commit();
    }
    @Override
    public void passorderData(String id, String uid,String oid, String cid, String pid, String time, String status, String emailid, String typeuser, String model, String Rate, String Type, String Address, String Pincode, String name, String mobileno, String city, String amount, String picupdate, String dropdate, String img, String picuptime) {
        fullorder fragmentB = new fullorder();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("oid",oid);
        args.putString("uid", uid);
        args.putString("cid", cid);
        args.putString("amount", amount);
        args.putString("pid", pid);
        args.putString("time", time);
        args.putString("status", status);
        args.putString("city", city);
        args.putString("picupdate", picupdate);
        args.putString("dropdate", dropdate);
        args.putString("picuptime", picuptime);
        args.putString("name", name);
        args.putString("emailid", emailid);
        args.putString("mobileno", mobileno);
        args.putString("typeuser", typeuser);
        args.putString("model", model);
        args.putString("Rate", Rate);
        args.putString("Type", Type);
        args.putString("Address", Address);
        args.putString("Pincode", Pincode);
        args.putString("img", img);
        fragmentB .setArguments(args);
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.linear, fragmentB )
                .commit();
    }
}