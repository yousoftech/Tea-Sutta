package com.tesuta.shopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesuta.R;

/**
 * Created by Nilay on 12-Apr-17.
 */

public class Offer extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.frag_offer, container, false);

        return view;
    }
    public static Fragment newInstance() {
        Offer fragment = new Offer();
        return fragment;
    }
}