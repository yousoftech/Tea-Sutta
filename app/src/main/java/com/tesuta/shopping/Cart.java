package com.tesuta.shopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tesuta.R;
import com.tesuta.adapter.SelectCartDetailAdapter;
import com.tesuta.models.AllCart;
import com.tesuta.models.AllCartProduct;
import com.tesuta.rest.Config;
import com.tesuta.rest.RestClient;
import com.tesuta.utils.NetworkUtils;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    SelectCartDetailAdapter adapter;
    List<AllCartProduct> getallCartProductLists = new ArrayList<>();
    TextView empty_view,c_subtotal,coupentxt,c_subtotal1,c_offerper,c_checktotalcost,txt_delivery_cost,txt_discount_cost;
    String user_id,status;
    PopupWindow popupWindow1;
    String s1;
    Button cancel,btnconfirm;
    TextView description;
    String coupencode=null,coupen=null;
    EditText txtcode;
    View popupView1;
    LinearLayout btn_applyy_coupen,promocard;
    LinearLayout c_linear,final_order,linear_no_internet,emp_empty_bucket;
    String user_info,user_address;
    Button start_shopping;
    String coupenid=null;
    RelativeLayout fullrel,mainrel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        C0456b.f2467p1 = getSharedPreferences(C0456b.f2907a1,0);
        C0456b.f2467p2 = getSharedPreferences(C0456b.f2907a2,0);
        mainrel=(RelativeLayout)findViewById(R.id.mainrel);
        fullrel=(RelativeLayout)findViewById(R.id.fullrel);
        user_info = C0456b.f2467p1.getString("user_info",null);
        user_address = C0456b.f2467p2.getString("user_address",null);
        final String user_name[] = user_info.split(",");
        final String user_location[] = user_address.split(",");
        btn_applyy_coupen=(LinearLayout)findViewById(R.id.btn_applyy_coupen);
        promocard=(LinearLayout)findViewById(R.id.promocard);
        coupentxt=(TextView)findViewById(R.id.coupentxt);
        recyclerView = (RecyclerView) findViewById(R.id.gmail_list);
        empty_view = (TextView) findViewById(R.id.empty_view);
        txt_discount_cost=(TextView)findViewById(R.id.txt_discount_cost);
        c_subtotal = (TextView) findViewById(R.id.c_subtotal);
        c_subtotal1 = (TextView) findViewById(R.id.c_subtotal1);
        c_offerper = (TextView) findViewById(R.id.c_offerper);
        c_checktotalcost = (TextView) findViewById(R.id.c_checktotalcost);
        txt_delivery_cost = (TextView) findViewById(R.id.txt_delivery_cost);
        c_linear = (LinearLayout) findViewById(R.id.c_linear);
        final_order = (LinearLayout) findViewById(R.id.final_order);
        linear_no_internet = (LinearLayout) findViewById(R.id.linear_no_internet);
        emp_empty_bucket = (LinearLayout) findViewById(R.id.emp_empty_bucket);
        start_shopping = (Button) findViewById(R.id.start_shopping);
        start_shopping.setEnabled(false);
        fullrel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mainrel.getVisibility()==View.VISIBLE)
                {
                    mainrel.setVisibility(View.GONE);
                }
            }
        });
        coupentxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupcoupen();
            }
        });
        start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Cart.this,Master_Home.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i1);
                finish();
            }
        });
        C0456b.f2467p = getSharedPreferences(C0456b.f2907a,0);
        user_id = C0456b.f2467p.getString("user_id",null);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (adapter == null) {
            adapter = new SelectCartDetailAdapter(this,getallCartProductLists);
            recyclerView.setAdapter(adapter);
        }

        status = NetworkUtils.getConnectivityStatus(Cart.this);
        if (status.equals("404")) {
            linear_no_internet.setVisibility(View.VISIBLE);
        }
        else {
            callCartData();
        }
        final_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Cart.this,Checkout.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if(coupen==null) {
                    i1.putExtra("disc","0");
                    i1.putExtra("coupenid","0");
                }
                else
                {
                    i1.putExtra("disc",coupen);
                    i1.putExtra("coupenid",coupenid);
                }
                startActivity(i1);
             /*  LayoutInflater layoutInflater = (LayoutInflater)Cart.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popu, null);
                popupWindow = new PopupWindow(
                        popupView,
                        DrawerLayout.LayoutParams.MATCH_PARENT,
                        DrawerLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,Gravity.CENTER,250);
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.update();
                final EditText cf_house = (EditText) popupView.findViewById(R.id.cf_house);
                final EditText cf_society = (EditText) popupView.findViewById(R.id.cf_society);
                final EditText cf_locality = (EditText) popupView.findViewById(R.id.cf_locality);
                final EditText cf_pincode = (EditText) popupView.findViewById(R.id.cf_pincode);

                cf_house.setText(user_location[0]);
                cf_society.setText(user_location[1]);
                cf_locality.setText(user_location[2]);
                cf_pincode.setText(user_location[3]);
                Button cf_cancel = (Button) popupView.findViewById(R.id.cf_cancel);
                Button cf_confirm = (Button) popupView.findViewById(R.id.cf_confirm);

                cf_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                    }
                });
                cf_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final_order.setEnabled(false);
                        String o_number = cf_house.getText().toString();
                        String o_society = cf_society.getText().toString();
                        String o_location = cf_locality.getText().toString();
                        String o_pincode = cf_pincode.getText().toString();
                        checkout(Config.mem_string,user_id,o_number,o_society,o_location,o_pincode);
                    }
                });
                */
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent i1 = new Intent(Cart.this,Master_Home.class);
        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        finish();
    }


    void popupcoupen() {
        mainrel.setVisibility(View.VISIBLE);
        //LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView1 = layoutInflater.inflate(R.layout.discountpopup, null);
        popupWindow1=new PopupWindow(popupView1, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow1.setTouchable(true);
        popupWindow1.setFocusable(true);
        popupWindow1.setOutsideTouchable(false);
        popupWindow1.showAtLocation(popupView1, Gravity.CENTER, 0, 0);
        //mainrel.setVisibility(View.GONE);
        cancel=(Button)popupView1.findViewById(R.id.cancel);
        btnconfirm=(Button) popupView1.findViewById(R.id.btnconfirm);
        txtcode=(EditText) popupView1.findViewById(R.id.txtcode);
        description=(TextView) popupView1.findViewById(R.id.description);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainrel.setVisibility(View.GONE);
                popupWindow1.dismiss();
            }
        });

        txtcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                coupen=null;
                coupencode=null;
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtcode.getText().equals(""))
                {}else{
                    if(coupen!=null && coupencode !=null)
                    {
                        callCartData1(coupen,coupencode);
                       popupWindow1.dismiss();
                    }
                    else {
                        checkcode(user_id,txtcode.getText().toString(),c_checktotalcost.getText().toString(),description);
                    }
                }
            }
        });


    }



    public void callCartData() {
        c_linear.setVisibility(View.INVISIBLE);
        promocard.setVisibility(View.GONE);
        coupentxt.setText("Apply Coupen");
        final DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.showNow();
        RestClient.GitApiInterface service = RestClient.getClient();

        Call<AllCart> call = service.getCartDetails(Config.mem_string,user_id);
        call.enqueue(new Callback<AllCart>() {
            @Override
            public void onResponse(Response<AllCart> response) {
                mDilatingDotsProgressBar.hideNow();
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    AllCart result = response.body();
                    if (result.getStatus().equals("success")) {
                        if (result.getProduct() != null) {
                            if (result.getProduct().size() > 0) {
                                getallCartProductLists.clear();
                                getallCartProductLists.addAll(result.getProduct());
                                s1=result.getOrd_id();
                                adapter.notifyDataSetChanged();
                                if (getallCartProductLists.isEmpty()) {
                                    recyclerView.setVisibility(View.GONE);
                                    empty_view.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    empty_view.setVisibility(View.GONE);
                                }
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                empty_view.setVisibility(View.VISIBLE);

                            }
                        }
                        c_subtotal.setText(result.getTotal());
                        c_subtotal1.setText(result.getSaving());
                        c_offerper.setText(result.getSavingoffer());
                        c_checktotalcost.setText(result.getTotal_shipping_cost());
                        txt_delivery_cost.setText(result.getShipping());
                        c_linear.setVisibility(View.VISIBLE);
                    } else if (result.getStatus().equals("empty_cart")){
                        recyclerView.setVisibility(View.INVISIBLE);
                        empty_view.setVisibility(View.INVISIBLE);
                        emp_empty_bucket.setVisibility(View.VISIBLE);
                        start_shopping.setEnabled(true);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    recyclerView.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                mDilatingDotsProgressBar.hideNow();
            }
        });
    }

    public void callCartData1(String coupen,String coupencode) {
        c_linear.setVisibility(View.INVISIBLE);
        final DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        mDilatingDotsProgressBar.showNow();
        RestClient.GitApiInterface service = RestClient.getClient();

        Call<AllCart> call = service.getCartDetailscoupen(Config.mem_string,user_id,coupen,coupencode);
        call.enqueue(new Callback<AllCart>() {
            @Override
            public void onResponse(Response<AllCart> response) {
                mDilatingDotsProgressBar.hideNow();
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    AllCart result = response.body();
                    if (result.getStatus().equals("success")) {
                        if (result.getProduct() != null) {
                            if (result.getProduct().size() > 0) {
                                getallCartProductLists.clear();
                                getallCartProductLists.addAll(result.getProduct());
                                s1=result.getOrd_id();
                                adapter.notifyDataSetChanged();
                                if (getallCartProductLists.isEmpty()) {
                                    recyclerView.setVisibility(View.GONE);
                                    empty_view.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    empty_view.setVisibility(View.GONE);
                                }
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                empty_view.setVisibility(View.VISIBLE);

                            }
                        }
                        c_subtotal.setText(result.getTotal());
                        c_subtotal1.setText(result.getSaving());
                        c_offerper.setText(result.getSavingoffer());
                        c_checktotalcost.setText(result.getTotal_shipping_cost());
                        txt_delivery_cost.setText(result.getShipping());
                        promocard.setVisibility(View.VISIBLE);
                        txt_discount_cost.setText(result.getCoupen());
                        coupentxt.setText("Coupen Applied "+result.getCoupencode());
                        c_linear.setVisibility(View.VISIBLE);
                    } else if (result.getStatus().equals("empty_cart")){
                        recyclerView.setVisibility(View.INVISIBLE);
                        empty_view.setVisibility(View.INVISIBLE);
                        emp_empty_bucket.setVisibility(View.VISIBLE);
                        start_shopping.setEnabled(true);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.GONE);
                        empty_view.setVisibility(View.VISIBLE);
                    }
                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    recyclerView.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Throwable t) {
                mDilatingDotsProgressBar.hideNow();
            }
        });
    }

    private void checkcode(String user_id, String code, String totalamount, final TextView description) {
        RestClient.GitApiInterface service = RestClient.getClient();
        //final JSONObject call=service.getsendsms(mem_string,user_id,mem_sms);
//        Call<coupen> c=service.coupencheck(code,user_id,totalamount);

        final ProgressDialog progressDialog;
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://teasuttaapi.yousoftech.com/API/check_coupen_code.php?code=" + code + "&user_id=" + user_id + "&totalamount=" + totalamount + "", null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    String result=response.getString("status");
                    if(result.equals("success"))
                    {
                        description.setText(response.getJSONArray("coupon").getJSONObject(0).getString("msg"));
                        coupen=response.getJSONArray("coupon").getJSONObject(0).getString("rupee");
                        coupencode=response.getJSONArray("coupon").getJSONObject(0).getString("code");
                        coupenid=response.getJSONArray("coupon").getJSONObject(0).getString("coupon_id");
                    }
                    else
                    {
                        description.setText(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
        });
        jsonObjectRequest.setRetryPolicy( new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
        RequestQueue requestQueue1 = Volley.newRequestQueue( this );
        requestQueue1.add( jsonObjectRequest );


    }

}
