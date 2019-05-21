package com.tesuta.delivary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tesuta.R;
import com.tesuta.adapter.MobileOS1;
import com.tesuta.adapter.Phone1;

import com.tesuta.adapter.RecyclerViewHolderDeliver1;
import com.tesuta.models.AllExpandOrderDeliverList;
import com.tesuta.models.AllExpandOrderList;
import com.tesuta.models.AllOrder;
import com.tesuta.models.AllOrderDelivery;
import com.tesuta.rest.Config;
import com.tesuta.rest.RestClient;
import com.tesuta.shopping.C0456b;
import com.tesuta.shopping.Check;
import com.tesuta.shopping.Master_Home;
import com.tesuta.utils.NetworkUtils;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class Deliveryorderdetails extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    RecyclerView recyclerView2;
    TextView empty_view;
    String user_id, status;
    LinearLayout linear_no_internet, emp_empty_bucket;
    List<AllExpandOrderList> getallOrderExpandLists = new ArrayList<>();
    List<AllExpandOrderDeliverList> getallOrderExpandDeliveryList = new ArrayList<>();
    RecyclerViewHolderDeliver1 adapter2;
    private ArrayList<MobileOS1> mobilelOSes = new ArrayList<>();

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryorderdetails);

        recyclerView2 = (RecyclerView) findViewById(R.id.gmail_list2);
        empty_view = (TextView) findViewById(R.id.empty_view);
        linear_no_internet = (LinearLayout) findViewById(R.id.linear_no_internet);
        emp_empty_bucket = (LinearLayout) findViewById(R.id.emp_empty_bucket);
        sharedpreferences = getSharedPreferences("deliveryprf", MODE_PRIVATE);
        user_id = sharedpreferences.getString("deliveryid",null);

        recyclerView2.setLayoutManager(new LinearLayoutManager(Deliveryorderdetails.this));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        status = NetworkUtils.getConnectivityStatus(Deliveryorderdetails.this);
        if (status.equals("404")) {
            linear_no_internet.setVisibility(View.VISIBLE);
        } else {
            callorderdetails(Config.mem_string, user_id);
        }

    }

    private void callorderdetails(String mem_string, String user_id) {
        final DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.showNow();
        RestClient.GitApiInterface service = RestClient.getClient();

        Call<AllOrderDelivery> call = service.getAllorderDelivery(mem_string, user_id);
        call.enqueue(new Callback<AllOrderDelivery>() {
            @Override
            public void onResponse(Response<AllOrderDelivery> response) {
                mDilatingDotsProgressBar.hideNow();
                Log.d("fgh", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    AllOrderDelivery result = response.body();
                    if (result.getStatus().equals("success")) {
                        if (result.getUserorder() != null) {
                            if (result.getUserorder().size() > 0) {
                                getallOrderExpandDeliveryList.clear();
                                getallOrderExpandDeliveryList.addAll(result.getUserorder());
                                //  adapter2.notifyDataSetChanged();
                                for (int i = 0; i < getallOrderExpandDeliveryList.size(); i++) {
                                    ArrayList<Phone1> iphones = new ArrayList<>();
                                    String o_date = getallOrderExpandDeliveryList.get(i).getOrd_date();
                                    String o_status = getallOrderExpandDeliveryList.get(i).getOrd_status();
                                    String o_ship = getallOrderExpandDeliveryList.get(i).getOrd_ship();
                                    String o_disc = getallOrderExpandDeliveryList.get(i).getOrd_dics();
                                    String o_total = getallOrderExpandDeliveryList.get(i).getOrd_totalcost();
                                    String o_quntity = getallOrderExpandDeliveryList.get(i).getOrd_totalquntity();
                                    String o_book_id = getallOrderExpandDeliveryList.get(i).getOrd_id();
                                    String o_address = getallOrderExpandDeliveryList.get(i).getOrd_houseno() + " , " + getallOrderExpandDeliveryList.get(i).getOrd_society() + " " + getallOrderExpandDeliveryList.get(i).getOrd_locality();
                                    String o_pincode = getallOrderExpandDeliveryList.get(i).getOrd_pincode();
                                    for (int j = 0; j < getallOrderExpandDeliveryList.get(i).getOrderdetails().size(); j++) {
                                        String od_name = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getPro_name();
                                        String od_actual_cost = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getOde_actual_cost();
                                        String od_offer_cost = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getOde_offer_cost();
                                        String od_offer = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getOde_offer();
                                        String od_quantity = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getOde_quantity();
                                        String od_total = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getOde_total();
                                        String od_unit = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getPro_unit();
                                        String od_image = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getPro_image();
                                        String od_pro_id = getallOrderExpandDeliveryList.get(i).getOrderdetails().get(j).getOde_pro_id();

                                        iphones.add(new Phone1(od_name + "!-" + od_actual_cost + "!-" + od_offer_cost + "!-" + od_offer + "!-" + od_quantity + "!-" + od_total + "!-" + od_unit + "!-" + od_image + "!-" + od_pro_id));
                                    }
                                    mobilelOSes.add(new MobileOS1(o_date + "!-" + o_status + "!-" + o_total + "!-" + o_quntity + "!-" + o_book_id + "!-" + o_ship + "!-" + o_disc + "!-" + o_address + "!-" + o_pincode, iphones));
                                }
                                adapter2 = new RecyclerViewHolderDeliver1(Deliveryorderdetails.this, mobilelOSes);
                                recyclerView2.setAdapter(adapter2);
                                if (getallOrderExpandDeliveryList.isEmpty()) {
                                    recyclerView2.setVisibility(View.GONE);
                                    empty_view.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView2.setVisibility(View.VISIBLE);
                                    empty_view.setVisibility(View.GONE);
                                }
                            } else {
                                recyclerView2.setVisibility(View.GONE);
                                empty_view.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (result.getStatus().equals("emptyorder")) {
                        recyclerView2.setVisibility(View.INVISIBLE);
                        empty_view.setVisibility(View.INVISIBLE);
                        emp_empty_bucket.setVisibility(View.VISIBLE);


                    } else {
                        recyclerView2.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    recyclerView2.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mDilatingDotsProgressBar.hideNow();
            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
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
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("name", null);
            editor.putString("deliveryid", null);
            editor.putString("number", null);
            editor.commit();


            Intent i = new Intent(Deliveryorderdetails.this, Check.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        }


        return super.onOptionsItemSelected(item);
    }
}