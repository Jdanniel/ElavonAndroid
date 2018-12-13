package com.artefacto.microformas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.artefacto.microformas.InventorySuppliesFragment;
import com.artefacto.microformas.InventoryUnitsFragment;

import java.util.HashMap;
import java.util.Map;

public class StatePagerAdapter extends FragmentStatePagerAdapter
{
    public StatePagerAdapter(FragmentManager fm)
    {
        super(fm);

        mFragmentMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int i)
    {
        Fragment myFragment = null;

        switch (i)
        {
            case 0:
                myFragment = new InventoryUnitsFragment();
                break;

            case 1:
                myFragment = new InventorySuppliesFragment();
                break;
        }

        if(myFragment != null)
        {
            mFragmentMap.put(i, myFragment);
        }

        return myFragment;
    }
    //Para agregar un elemento mas al array aumentar el numero de elemento a contar
    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        super.destroyItem(container, position, object);
        mFragmentMap.remove(position);
    }

    public Fragment getFragment(int key)
    {
        if(mFragmentMap.containsKey(key))
        {
            return mFragmentMap.get(key);
        }

        return null;
    }
    //Array para la construccion del tabulador de inventario
    private static String[] tabTitles = new String[]
    {
        "Unidades",
        "Insumos"
    };

    private Map<Integer, Fragment> mFragmentMap;
}
