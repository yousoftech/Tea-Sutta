package com.tesuta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.tesuta.R;
import com.tesuta.models.AllProductImage;

import java.util.List;

public class imgadapter extends RecyclerView.Adapter<imgadapter.RecyclerViewHolder> {

    LayoutInflater inflater;
    Context context;
    List<AllProductImage> event;
    ImageView p_img;

    public imgadapter(Context context, List<AllProductImage> event, ImageView p_img)
    {
        this.context=context;
        this.event=event;
        this.p_img=p_img;
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public imgadapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.imgallrow,parent,false);
        RecyclerViewHolder holder=new RecyclerViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final imgadapter.RecyclerViewHolder holder, int position) {

        final String img=event.get(position).getProimg_img();
        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Picasso.with(context).load(img).into(p_img);
                    Log.d("fgh",img);
                }
                catch (Exception e)
                {
                    Log.d("fgh","img"+img);
                    Log.d("fgh",e.toString());
                }
            }
        });

        try
        {
            Picasso.with(context).load(img).into(holder.img);
            Log.d("fgh",img);
        }
        catch (Exception e)
        {
            Log.d("fgh","img"+img);
            Log.d("fgh",e.toString());
        }


    }

    @Override
    public int getItemCount() {
        return event.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        RelativeLayout rel;
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            img=(ImageView)itemView.findViewById(R.id.img);
            rel=(RelativeLayout) itemView.findViewById(R.id.relimg);

        }
    }
}
