package com.tesuta.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tesuta.R;
import com.tesuta.delivary.Deliveryorderdetails;
import com.tesuta.models.AllCancel;
import com.tesuta.rest.Config;
import com.tesuta.rest.RestClient;
import com.tesuta.shopping.Master_Home;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class OSViewHolderDelivery1 extends GroupViewHolder {

    private TextView or_date;
    private TextView or_status;
    private TextView or_total;
    private TextView or_quntity;
    private TextView or_cancel;
    private ImageView h_e_arrow;
    private CardView h_e_card;
    private TextView txt_disc;
    private TextView or_disc;
    private TextView or_ship;
    private TextView or_address;
    private TextView or_pincode;
    private Button btndone;
    Context context;
    public OSViewHolderDelivery1(View itemView) {
        super(itemView);

        btndone=(Button)itemView.findViewById(R.id.btndone);
        or_address=(TextView)itemView.findViewById(R.id.or_address);
        or_pincode=(TextView)itemView.findViewById(R.id.or_pincode);
        or_date = (TextView) itemView.findViewById(R.id.or_date);
        or_status = (TextView) itemView.findViewById(R.id.or_status);
        or_total = (TextView) itemView.findViewById(R.id.or_total);
        or_quntity = (TextView) itemView.findViewById(R.id.or_quntity);
        //or_cancel = (TextView) itemView.findViewById(R.id.or_cancel);
        or_disc = (TextView) itemView.findViewById(R.id.or_disc);
        txt_disc = (TextView) itemView.findViewById(R.id.txt_disc);
        or_ship = (TextView) itemView.findViewById(R.id.or_ship);
        h_e_arrow = (ImageView) itemView.findViewById(R.id.h_e_arrow);
        h_e_card = (CardView) itemView.findViewById(R.id.card_view);
    }
    @Override
    public void expand() {
        h_e_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
        h_e_card.setBackgroundResource(R.color.bg4);
        // h_e_arrow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down_arrow, 0);
        Log.i("Adapter", "expand");
    }
    @Override
    public void collapse() {
        Log.i("Adapter", "collapse");
        h_e_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
        h_e_card.setBackgroundResource(R.color.bg9);
        // h_e_cname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_up_arrow, 0);
    }
    public void setGroupName(ExpandableGroup group) {
        String full_data = group.getTitle();
        Log.d("fgh",full_data);
        final String[] seperatr_data = full_data.split("!-");
        or_date.setText(seperatr_data[0]);
        or_status.setText(seperatr_data[1]);
        or_total.setText(seperatr_data[2]);
        if(Integer.parseInt(seperatr_data[6])<1)
        {
            or_disc.setVisibility(View.INVISIBLE);
            txt_disc.setVisibility(View.INVISIBLE);
        }
        else
        {
            or_disc.setText(seperatr_data[6]);
        }
        or_ship.setText(seperatr_data[5]);
        or_quntity.setText(seperatr_data[3]);
        or_address.setText(seperatr_data[7]);
        or_pincode.setText(seperatr_data[8]);
        if (seperatr_data[1].equals("Cancel") )
        {
            or_status.setTextColor(Color.RED);
        }

        if (seperatr_data[1].equals("Confirm")|| seperatr_data[1].equals("Out for Delivery"))
        {
            btndone.setVisibility(View.VISIBLE);
        }
        else{
            btndone.setVisibility(View.GONE);
        }
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true){
                    Log.d("fgh","Cancel");
                    // ((Cart)context).callCartData();
                    // itemView.getContext().startActivity(new Intent("com.hshop.shopping.Master_Home"));
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("Order Deliver Successfully ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    /* Toast.makeText(itemView.getContext(), seperatr_data[4], Toast.LENGTH_SHORT).show(); */
                                    orderdone(seperatr_data[4]);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Order Status");
                    alert.show();

                }
                else{
                    Toast.makeText(context, "Try After Some Time", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Contact Customer Service", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void orderdone(String order_id) {
        RestClient.GitApiInterface service = RestClient.getClient();
        Call<AllCancel> call = service.getCompeteOrder(Config.mem_string,order_id);
        call.enqueue(new Callback<AllCancel>() {
            @Override
            public void onResponse(Response<AllCancel> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    AllCancel result = response.body();
                    if (result.getStatus().equals("success")) {
                        Toast.makeText(itemView.getContext(), "Order Successfully Deliver", Toast.LENGTH_LONG).show();
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), Deliveryorderdetails.class));
                    }
                    else{
                        Toast.makeText(itemView.getContext(), "Please try again is something wrong", Toast.LENGTH_LONG).show();
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), Deliveryorderdetails.class));
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(itemView.getContext(), "Please try again is something wrong", Toast.LENGTH_LONG).show();
                itemView.getContext().startActivity(new Intent(itemView.getContext(), Deliveryorderdetails.class));
            }
        });
    }
}

