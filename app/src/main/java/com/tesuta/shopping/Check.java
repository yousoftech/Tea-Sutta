package com.tesuta.shopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tesuta.R;
import com.tesuta.delivary.Deliveryorderdetails;
import com.tesuta.delivary.MainActivity;
import com.tesuta.models.AllCart;
import com.tesuta.models.Sendmsg;
import com.tesuta.rest.Config;
import com.tesuta.rest.RestClient;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class Check extends AppCompatActivity {


    private Thread.UncaughtExceptionHandler handleAppCrash =
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {
                    Log.e("error", ex.toString());
                    RestClient.GitApiInterface service = RestClient.getClient();

                    Call<Sendmsg> call = service.sendcrashmail(Config.mem_string,ex.toString());
                    call.enqueue(new Callback<Sendmsg>() {
                        @Override
                        public void onResponse(Response<Sendmsg> response) {

                            Log.d("fgh", "Status Code = " + response.code());
                            if (response.isSuccess()) {
                                // request successful (status code 200, 201)
                                Sendmsg result = response.body();
                                if (result.getStatus().equals("success")) {
                                }
                            }
                        }
                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Thread.setDefaultUncaughtExceptionHandler(handleAppCrash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences sharedpreferences = getSharedPreferences("deliveryprf", MODE_PRIVATE);
        C0456b.f2467p = getSharedPreferences(C0456b.f2907a,0);
        String contact= C0456b.f2467p.getString("user_id", null);
        String delivery=sharedpreferences.getString("deliveryid",null);

        if(delivery != null){
            Intent i1 = new Intent(Check.this, Deliveryorderdetails.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i1);
            finish();
        }
        else if (contact != null)
        {
            Intent i1 = new Intent(Check.this,Splash_activity.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i1);
            finish();
        }
        else {


             SharedPreferences.Editor f2467p2;
            SharedPreferences.Editor f2467p1;
             SharedPreferences.Editor f2467p;
                String user_id1 = "1";
                String user_name1 = "Guest User";
                String user_contact1 = "";
                String user_address1 = "";

                C0456b.f2467p = getSharedPreferences(C0456b.f2907a,0);
                C0456b.f2467p1 = getSharedPreferences(C0456b.f2907a1,0);
                C0456b.f2467p2 = getSharedPreferences(C0456b.f2907a2,0);

                String f00p = C0456b.f2467p.getString("user_id",null);
                String f00p1 = C0456b.f2467p1.getString("user_info",null);
                String f00p2= C0456b.f2467p2.getString("user_address",null);
                f2467p = C0456b.f2467p.edit();
                f2467p1 = C0456b.f2467p1.edit();
                f2467p2 = C0456b.f2467p2.edit();
                f2467p.putString("user_id",user_id1);
                f2467p1.putString("user_info",user_name1+","+user_contact1);
                f2467p2.putString("user_address",user_address1);
                f2467p.commit();
                f2467p1.commit();
                f2467p2.commit();

            Intent i1 = new Intent(Check.this,Splash_activity.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i1);
            finish();

            /*Intent i1 = new Intent(Check.this,Login.class);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i1);
            finish();*/
        }
    }
}
