package com.artefacto.microformas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.artefacto.microformas.adapters.ShipmentAdapter;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ShipmentBean;

import java.io.Serializable;
import java.util.ArrayList;

public class ReceptionsListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptions_list);
        Serializable mySerializable = getIntent().getSerializableExtra("bean");
        shipmentBeanArray = (ArrayList<ShipmentBean>) mySerializable;

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.receptions_list_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.receptions_list_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_receptions_list));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView mList = (ListView) this.findViewById(R.id.reception_listview);
        mShipmentAdapter = new ShipmentAdapter(this, shipmentBeanArray, 0);
        mList.setAdapter(mShipmentAdapter);
        mList.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        for(int i = 0; i < mShipmentAdapter.getStatus().size(); i++)
        {
            mShipmentAdapter.getStatus().set(i, false);
        }

        ListView mList = (ListView) this.findViewById(R.id.reception_listview);
        mList.setAdapter(mShipmentAdapter);
        mList.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_RECEPTION_LIST)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                finish();
            }
        }
    }

    private ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            View oldSelected = mShipmentAdapter.getSelected();
            if(oldSelected != null)
            {
                oldSelected.setBackgroundColor(MicroformasApp.getColor(ReceptionsListActivity.this,
                        R.color.white));
                oldSelected.invalidate();
            }

            for(int i = 0; i < mShipmentAdapter.getStatus().size(); i++)
            {
                mShipmentAdapter.getStatus().set(i, (i == position));
                if(i == position)
                {
                    mShipmentAdapter.setSelected(view);
                    view.setBackgroundColor(MicroformasApp.getColor(ReceptionsListActivity.this,
                            R.color.orange_micro));
                    view.invalidate();
                }
            }

            Intent intent = new Intent(ReceptionsListActivity.this, ReceptionActivity.class);
            intent.putExtra("bean", shipmentBeanArray.get(position));
            startActivityForResult(intent, REQUEST_RECEPTION_LIST);
        }
    };

    public static int REQUEST_RECEPTION_LIST = 15021;

    private ArrayList <ShipmentBean> shipmentBeanArray;

    private ShipmentAdapter mShipmentAdapter;
}
