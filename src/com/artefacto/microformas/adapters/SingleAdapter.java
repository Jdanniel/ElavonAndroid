package com.artefacto.microformas.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artefacto.microformas.R;
import com.artefacto.microformas.application.MicroformasApp;

import java.util.ArrayList;

public class SingleAdapter extends BaseAdapter
{
    public SingleAdapter(Context context, ArrayList<String> items)
    {
        mContext = context;
        mItems = items;

        mStatus = new ArrayList<>();
        for(int i = 0; i < items.size(); i++)
        {
            mStatus.add(false);
        }

        mSelected = null;
    }

    @Override
    public int getCount()
    {
        return mItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Holder itemHolder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {   // if it's not recycled, initialize some attributes
            convertView = inflater.inflate(R.layout.layout_item_single, parent, false);

            itemHolder = new Holder();
            itemHolder.textSingle = (TextView) convertView.findViewById(R.id.single_item_text);

            convertView.setTag(itemHolder);
        } else {
            itemHolder = (Holder) convertView.getTag();
        }

        convertView.setBackgroundColor(MicroformasApp.getColor(mContext, R.color.white));
        if (mStatus.get(position))
        {
            convertView.setBackgroundColor(MicroformasApp.getColor(mContext, R.color.orange_micro));
        }

        itemHolder.textSingle.setText(mItems.get(position));
        return convertView;
    }

    public ArrayList<Boolean> getStatus()
    {
        return mStatus;
    }

    public View getSelected()
    {
        return mSelected;
    }

    public void setSelected(View value)
    {
        mSelected = value;
    }

    static class Holder
    {
        TextView textSingle;
    }

    private ArrayList<String> mItems;
    private ArrayList<Boolean> mStatus;

    private View mSelected;

    private Context mContext;
}
