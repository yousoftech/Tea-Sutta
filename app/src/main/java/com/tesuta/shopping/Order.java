package com.tesuta.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tesuta.R;
import com.tesuta.adapter.MobileOS1;
import com.tesuta.adapter.Phone1;
import com.tesuta.adapter.RecyclerAdapter1;
import com.tesuta.models.AllExpandOrderList;
import com.tesuta.models.AllOrder;
import com.tesuta.rest.Config;
import com.tesuta.rest.RestClient;
import com.tesuta.utils.NetworkUtils;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Nilay on 12-Apr-17.
 */

public class Order extends Fragment {
    View view;
    RecyclerView recyclerView2;
    TextView empty_view;
    String user_id,status;
    LinearLayout linear_no_internet,emp_empty_bucket;
    List<AllExpandOrderList> getallOrderExpandLists = new ArrayList<>();
    RecyclerAdapter1 adapter2;
    private ArrayList<MobileOS1> mobileOSes = new ArrayList<>();
    Button start_shopping;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_order, container, false);

        recyclerView2 = (RecyclerView) view.findViewById(R.id.gmail_list2);
        empty_view = (TextView) view.findViewById(R.id.empty_view);
        linear_no_internet = (LinearLayout) view.findViewById(R.id.linear_no_internet);
        emp_empty_bucket = (LinearLayout) view.findViewById(R.id.emp_empty_bucket);
        start_shopping = (Button) view.findViewById(R.id.start_shopping);
        start_shopping.setEnabled(false);
        start_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getActivity(),Master_Home.class);
                startActivity(i1);
                getActivity().finish();
            }
        });
        C0456b.f2467p = getActivity().getSharedPreferences(C0456b.f2907a,0);
        user_id = C0456b.f2467p.getString("user_id",null);

        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        status = NetworkUtils.getConnectivityStatus(getContext());
        if (status.equals("404")) {
            linear_no_internet.setVisibility(View.VISIBLE);
        }
        else {
            callorderdetails(Config.mem_string,user_id);
        }



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment selectedFragment = Home.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, selectedFragment);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        } );


        return view;
    }


    private void callorderdetails(String mem_string, String user_id) {
        final DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) view.findViewById(R.id.progress);
        mDilatingDotsProgressBar.showNow();
        RestClient.GitApiInterface service = RestClient.getClient();

        Call<AllOrder> call = service.getAllorder(mem_string,user_id);
        call.enqueue(new Callback<AllOrder>() {
            @Override
            public void onResponse(Response<AllOrder> response) {
                mDilatingDotsProgressBar.hideNow();
                Log.d("fgh", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    AllOrder result = response.body();
                    if (result.getStatus().equals("success")) {
                        if (result.getUserorder() != null) {
                            if (result.getUserorder().size() > 0) {
                                getallOrderExpandLists.clear();
                                getallOrderExpandLists.addAll(result.getUserorder());
                                //  adapter2.notifyDataSetChanged();
                                for (int i =0 ;i<getallOrderExpandLists.size();i++){
                                    ArrayList<Phone1> iphones = new ArrayList<>();
                                    String o_date = getallOrderExpandLists.get(i).getOrd_date();
                                    String o_status = getallOrderExpandLists.get(i).getOrd_status();
                                    String o_total = getallOrderExpandLists.get(i).getOrd_totalcost();
                                    String o_quntity = getallOrderExpandLists.get(i).getOrd_totalquntity();
                                    String o_book_id = getallOrderExpandLists.get(i).getOrd_id();

                                    for (int j=0;j<getallOrderExpandLists.get(i).getOrderdetails().size();j++)
                                    {
                                        String od_name = getallOrderExpandLists.get(i).getOrderdetails().get(j).getPro_name();
                                        String od_actual_cost = getallOrderExpandLists.get(i).getOrderdetails().get(j).getOde_actual_cost();
                                        String od_offer_cost = getallOrderExpandLists.get(i).getOrderdetails().get(j).getOde_offer_cost();
                                        String od_offer = getallOrderExpandLists.get(i).getOrderdetails().get(j).getOde_offer();
                                        String od_quantity = getallOrderExpandLists.get(i).getOrderdetails().get(j).getOde_quantity();
                                        String od_total = getallOrderExpandLists.get(i).getOrderdetails().get(j).getOde_total();
                                        String od_unit = getallOrderExpandLists.get(i).getOrderdetails().get(j).getPro_unit();
                                        String od_image = getallOrderExpandLists.get(i).getOrderdetails().get(j).getPro_image();
                                        String od_pro_id = getallOrderExpandLists.get(i).getOrderdetails().get(j).getOde_pro_id();

                                        iphones.add(new Phone1(od_name+"!-"+od_actual_cost+"!-"+od_offer_cost+"!-"+od_offer+"!-"+od_quantity+"!-"+od_total+"!-"+od_unit+"!-"+od_image+"!-"+od_pro_id));
                                    }
                                    mobileOSes.add(new MobileOS1(o_date+"!-"+o_status+"!-"+o_total+"!-"+o_quntity+"!-"+o_book_id, iphones));
                                }
                                adapter2 = new RecyclerAdapter1(getActivity(),mobileOSes);
                                recyclerView2.setAdapter(adapter2);
                                if (getallOrderExpandLists.isEmpty()) {
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
                    } else if(result.getStatus().equals("emptyorder")){
                        recyclerView2.setVisibility(View.INVISIBLE);
                        empty_view.setVisibility(View.INVISIBLE);
                        emp_empty_bucket.setVisibility(View.VISIBLE);
                        start_shopping.setEnabled(true);

                    }else
                    {
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

    public static Fragment newInstance() {
        Order fragment = new Order();
        return fragment;
    }
}