package com.artefacto.microformas.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.artefacto.microformas.uicomponents.DynamicButton;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter
{
    public MainAdapter(Context context, ArrayList<Dyanamic> dynamicButtons)
    {
        mContext = context;
        mDynamics = dynamicButtons;
    }

    @Override
    public int getCount() {
        return mDynamics.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        DynamicButton dynamicButton;
        if (view == null)
        {   // if it's not recycled, initialize some attributes
            dynamicButton = new DynamicButton(mContext);
            dynamicButton.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT));
            dynamicButton.setClickable(false);
            dynamicButton.setFocusable(false);
            dynamicButton.setNotificationsNumber(mDynamics.get(i).count);
            dynamicButton.setNotificationsString(mDynamics.get(i).notifications, mDynamics.get(i).services,
                    mDynamics.get(i).lights);
            dynamicButton.setTitle(mDynamics.get(i).title);
            dynamicButton.setLightOn(mDynamics.get(i).litghsOn);

            int screenHeight = ((Activity) mContext).getWindowManager()
                    .getDefaultDisplay().getHeight();
            dynamicButton.setMinHeight((int) (screenHeight / HEIGHT_PARTS));
        }
        else
        {
            dynamicButton = (DynamicButton) view;
        }

        return dynamicButton;
    }

    public static class Dyanamic
    {
        public String[] notifications;
        public String[] services;
        public String title;
        public int[] lights;
        public int count;
        public boolean litghsOn;
    }

    private static float HEIGHT_PARTS = 5.17f;

    private ArrayList<Dyanamic> mDynamics;

    private Context mContext;
}
