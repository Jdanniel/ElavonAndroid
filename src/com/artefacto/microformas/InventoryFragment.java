package com.artefacto.microformas;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artefacto.microformas.adapters.StatePagerAdapter;
import com.artefacto.microformas.uicomponents.SlidingTabLayout;

public class InventoryFragment extends Fragment
{
    public InventoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //int tabSection = getArguments().getInt(KEY_TAB_SECTION);
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        mPagerAdapter = new StatePagerAdapter(this.getActivity().getSupportFragmentManager());

        mInventoryViewPager = (ViewPager) rootView.findViewById(R.id.inventory_pager);
        mInventoryViewPager.setAdapter(mPagerAdapter);
        mInventoryViewPager.setCurrentItem(0);

        mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.inventory_tab);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mInventoryViewPager);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer()
        {
            @Override
            public int getIndicatorColor(int position)
            {
                return InventoryFragment.this.getResources().getColor(R.color.orange_micro);
            }

            @Override
            public int getDividerColor(int position) {
                // TODO Auto-generated method stub
                return InventoryFragment.this.getResources().getColor(R.color.orange_micro);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            mListener = (InventoryUnitsFragment.OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public ViewPager getViewPager()
    {
        return mInventoryViewPager;
    }

    private InventoryUnitsFragment.OnFragmentInteractionListener mListener;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mInventoryViewPager;
    private StatePagerAdapter mPagerAdapter;
}
