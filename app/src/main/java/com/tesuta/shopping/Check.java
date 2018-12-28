package com.tesuta.shopping;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tesuta.R;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        C0456b.f2467p = getSharedPreferences(C0456b.f2907a,0);
        String contact= C0456b.f2467p.getString("user_id", null);

        if (contact != null)
        {
            Intent i1 = new Intent(Check.this,Splash_activity.class);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i1);
            finish();
        }
        else {
            Intent i1 = new Intent(Check.this,Login.class);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i1);
            finish();
        }
    }
}
