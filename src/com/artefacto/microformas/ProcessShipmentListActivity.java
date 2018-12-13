package com.artefacto.microformas;

import java.io.Serializable;
import java.util.ArrayList;

import com.artefacto.microformas.adapters.ShipmentAdapter;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.beans.ShipmentBean;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;

public class ProcessShipmentListActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_shipment_list);
        Serializable mySerializable = getIntent().getSerializableExtra("bean");
        shipmentBeanArray = (ArrayList<ShipmentBean>) mySerializable;

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.process_shipment_list_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.process_shipment_list_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_process_shipment_list));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView mList = (ListView) this.findViewById(R.id.process_shipment_listview);
        mShipmentAdapter = new ShipmentAdapter(this, shipmentBeanArray, 1);
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

        ListView mList = (ListView) this.findViewById(R.id.process_shipment_listview);
        mList.setAdapter(mShipmentAdapter);
        mList.setOnItemClickListener(onItemClickListener);
    }

    private ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            View oldSelected = mShipmentAdapter.getSelected();
            if(oldSelected != null)
            {
                oldSelected.setBackgroundColor(MicroformasApp.getColor(ProcessShipmentListActivity.this,
                        R.color.white));
                oldSelected.invalidate();
            }

            for(int i = 0; i < mShipmentAdapter.getStatus().size(); i++)
            {
                mShipmentAdapter.getStatus().set(i, (i == position));
                if(i == position)
                {
                    mShipmentAdapter.setSelected(view);
                    view.setBackgroundColor(MicroformasApp.getColor(ProcessShipmentListActivity.this,
                            R.color.orange_micro));
                    view.invalidate();
                }
            }

            Intent intent = new Intent(ProcessShipmentListActivity.this, ProcessShipmentActivity.class);
            intent.putExtra("bean", shipmentBeanArray.get(position));
            startActivityForResult(intent, REQUEST_PROCESS_SHIPMENT_LIST);
        }
    };

    public static int REQUEST_PROCESS_SHIPMENT_LIST = 15022;

    private ArrayList <ShipmentBean> shipmentBeanArray;

    private ShipmentAdapter mShipmentAdapter;
}