package com.accedo.colourmemory;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by o.lopez.cienfuegos on 18/03/2016.
 */
public class CardAdapter extends ArrayAdapter<Integer> {

    private final Activity context;
    Integer[] cards;

    public CardAdapter(Activity context, Integer[] cards) {
        super(context, R.layout.card_adapter, cards);
        this.context = context;
        this.cards = cards;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.card_adapter, null, true);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.card);
        imageView.setImageResource(cards[position]);

        return rootView;
    }
}
