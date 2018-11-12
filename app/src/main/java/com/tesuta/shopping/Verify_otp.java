package com.tesuta.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tesuta.R;
import com.tesuta.models.AllContactCheck;
import com.tesuta.rest.Config;
import com.tesuta.rest.RestClient;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.Random;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class Verify_otp extends AppCompatActivity implements View.OnClickListener {

    Button btn_verifyotp,btn_resend;
    EditText edt_verifyotp;
    String mem_contact,mem_otp;
    TextView edt_message,resend;
    int i=0;
    StringBuffer otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        Intent i1 = getIntent();
        mem_contact = i1.getStringExtra("mem_contact");
        mem_otp = i1.getStringExtra("mem_otp");
        setTitle("Verify +91 - "+mem_contact);
        btn_resend=(Button)findViewById(R.id.btn_resendotp);
        btn_resend.setVisibility(View.INVISIBLE);
        resend=(TextView)findViewById(R.id.resend);
        edt_message = (TextView) findViewById(R.id.edt_message) ;
        edt_message.setText(mem_contact);
        edt_verifyotp = (EditText) findViewById(R.id.edt_verifyotp) ;
        btn_verifyotp = (Button) findViewById(R.id.btn_verifyotp);
        btn_verifyotp.setOnClickListener(this);
        startTimer();
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                String s2 = messageText.substring(0,6);
                edt_verifyotp.setText(s2);
            }
        });
        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random rnd = new Random();
                otp = new StringBuffer("" + rnd.nextInt(999999));
                if (otp.length() <= 5) {
                    for (int i = otp.length(); i <= 5; i++) {
                        otp.insert(0, "0");
                    }
                }
                String finalotp= otp.toString();
                callCheckContactData(finalotp,mem_contact);

            }
        });
    }

    public void startTimer()
    {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                resend.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittextacti
            }

            public void onFinish() {

                resend.setText( "Resend Otp" );
                btn_resend.setVisibility(View.VISIBLE);
                resend.setVisibility(View.INVISIBLE);
            }

        }.start();
    }

    public void callCheckContactData(final String finalotp, final String contact) {
        final DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.showNow();
        RestClient.GitApiInterface service = RestClient.getClient();
        Call<AllContactCheck> call = service.getAllContactCheck(Config.mem_string,contact,finalotp);
        call.enqueue(new Callback<AllContactCheck>() {
            @Override
            public void onResponse(Response<AllContactCheck> response) {
                mDilatingDotsProgressBar.hideNow();
                Log.d("test", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    AllContactCheck result = response.body();
                    Log.d("fgh", result.getStatus());
                    if (result.getStatus().equals("success")) {

                        //  Toast.makeText(Generate_otp.this,finalotp, Toast.LENGTH_SHORT).show();
                        Intent i1 = new Intent(Verify_otp.this,Verify_otp.class);
                        i1.putExtra("mem_contact",contact);
                        i1.putExtra("mem_otp",finalotp);
                        startActivity(i1);
                        finish();
                    } else if (result.getStatus().equals("already")){
                        Toast.makeText(Verify_otp.this, "Contact Number Already Register With us", Toast.LENGTH_SHORT).show();
                    }
                    else {

                    }
                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    // linear_server.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.d("fgh", String.valueOf(t));
                mDilatingDotsProgressBar.hideNow();
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verifyotp:
                String edotp = edt_verifyotp.getText().toString();
                if (edotp.equals(mem_otp))
                {
                    Intent i1 = new Intent(Verify_otp.this,Register.class);
                    i1.putExtra("mem_contact",mem_contact);
                    startActivity(i1);
                }
                else
                {
                    Toast.makeText(Verify_otp.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
