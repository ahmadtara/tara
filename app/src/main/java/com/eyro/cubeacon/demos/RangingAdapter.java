package com.eyro.cubeacon.demos;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RangingAdapter extends ArrayAdapter<RangingModel>{

    private Context context;
    private List<RangingModel> rangingItems;

    public RangingAdapter(Context context,  int textViewResourceId, List<RangingModel> RangingItems) {
        super(context, textViewResourceId, RangingItems);
        this.context = context;
        this.rangingItems = RangingItems;
    }


    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.gridview_items, parent, false);
        }

        RangingModel rangingModel = rangingItems.get(position);

        if (rangingModel != null) {
            TextView tvTitle = (TextView) view.findViewById(R.id.txTitle);
            tvTitle.setText(rangingModel.getMtitle());
            TextView tvSubtitle = (TextView) view.findViewById(R.id.txSubtitle);
            tvSubtitle.setText(rangingModel.getMsubtitle());
            TextView tvStatus = (TextView) view.findViewById(R.id.txStatus);
            tvStatus.setText(rangingModel.getMstatus());
            if (tvStatus.getText().equals("IMMEDIATE")) {
                view.findViewById(R.id.gridPanels).setBackgroundColor(Color.parseColor("#c3c3c3"));
            } else {
                view.findViewById(R.id.gridPanels).setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

        return view;
    }
}
