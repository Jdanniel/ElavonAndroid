package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artefacto.microformas.R;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ShipmentBean;

public class ShipmentAdapter extends BaseAdapter
{
	public ShipmentAdapter(Context context, ArrayList<ShipmentBean> items, int type)
    {
		mContext = context;
		mItems = items;

        mStatus = new ArrayList<>();
        for(int i = 0; i < items.size(); i++)
        {
            mStatus.add(false);
        }

        mSelected = null;
        mType = type;
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
        HolderView itemHolder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {   // if it's not recycled, initialize some attributes
            convertView = inflater.inflate(R.layout.layout_item_shipment, parent, false);

            itemHolder = new HolderView();
            itemHolder.textResponsible = (TextView) convertView.findViewById(R.id.reception_item_destination);
            itemHolder.textTitleResponsible = (TextView) convertView.findViewById(R.id.reception_item_destination_title);
            itemHolder.textGuide = (TextView) convertView.findViewById(R.id.reception_item_guide);
            itemHolder.textService = (TextView) convertView.findViewById(R.id.reception_item_messaging);
            itemHolder.textSentDate = (TextView) convertView.findViewById(R.id.reception_item_date);
            itemHolder.textClient = (TextView) convertView.findViewById(R.id.reception_item_client);
            convertView.setTag(itemHolder);
        }
        else
        {
            itemHolder = (HolderView) convertView.getTag();
        }

        convertView.setBackgroundColor(MicroformasApp.getColor(mContext, R.color.white));
        if (mStatus.get(position))
        {
            convertView.setBackgroundColor(MicroformasApp.getColor(mContext, R.color.orange_micro));
        }

        ShipmentBean shipmentBean = mItems.get(position);
        itemHolder.textResponsible.setText(shipmentBean.getResponsible());
        itemHolder.textGuide.setText(shipmentBean.getNoGuide());
        itemHolder.textService.setText(shipmentBean.getMessagingServiceDesc());
        itemHolder.textSentDate.setText(shipmentBean.getDate());
        itemHolder.textClient.setText(shipmentBean.getDescClient());

        switch (mType)
        {
            case 0:
                itemHolder.textTitleResponsible.setText("Origen:");
                break;

            case 1:
                itemHolder.textTitleResponsible.setText("Destino:");
                break;
        }

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

    static class HolderView
    {
        public TextView textService;
//        public TextView textResponsibleType;
        public TextView textResponsible;
        public TextView textGuide;
        public TextView textSentDate;
        public TextView textClient;

        public TextView textTitleResponsible;
    }

    private ArrayList<Boolean> mStatus;

    private Context mContext;

    private View mSelected;

    private int mType;

	private ArrayList<ShipmentBean> mItems;
}