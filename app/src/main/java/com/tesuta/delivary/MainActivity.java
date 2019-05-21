package com.tesuta.delivary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tesuta.R;
import com.tesuta.shopping.Check;
import com.tesuta.shopping.Login;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        final MenuItem menuItem = menu.findItem(R.id.logout);
        getMenuInflater().inflate(R.menu.delivery_top, menu);






        // textCartItemCount.setText( "400" );



        // setupBadge();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            sharedpreferences = getSharedPreferences("deliveryprf", MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedpreferences.edit();
            editor.putString("name",null);
            editor.putString("deliveryid", null);
            editor.putString("number", null);
            editor.commit();



            Intent i = new Intent(MainActivity.this, Check.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        }


        return super.onOptionsItemSelected(item);
    }
}
