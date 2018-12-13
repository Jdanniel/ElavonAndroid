package com.artefacto.microformas;

import com.artefacto.microformas.beans.ShipmentBean;
import com.artefacto.microformas.beans.SimBean;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.constants.Constants;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProcessShipmentActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_shipment);

		Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.process_shipment_toolbar);
		TextView textTitle = (TextView) this.findViewById(R.id.process_shipment_toolbar_title);
		setTitle("");
		textTitle.setText(getString(R.string.title_activity_shipment_detail));
		setSupportActionBar(toolbarInventory);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		shipmentBean = (ShipmentBean) intent.getSerializableExtra("bean");

		TextView textId = (TextView)findViewById(R.id.process_shipment_text_id);
		TextView textResponsible    = (TextView)findViewById(R.id.process_shipment_text_responsible);
		TextView textResponsibleType = (TextView)findViewById(R.id.process_shipment_text_responsible_type);
		TextView textMessaging = (TextView)findViewById(R.id.process_shipment_text_messaging);
		TextView textNoGuide = (TextView)findViewById(R.id.process_shipment_text_no_guide);
		TextView textDate = (TextView)findViewById(R.id.process_shipment_text_date);

		if(!(shipmentBean.getIdShipment() == null || shipmentBean.getIdShipment().equals("")))
		{
			textId.setText(shipmentBean.getIdShipment());
		}

		if(!(shipmentBean.getResponsible() == null || shipmentBean.getResponsible().equals("")))
		{
			textResponsible.setText(shipmentBean.getResponsible());
		}

		if(!(shipmentBean.getResponsibleTypeDesc() == null || shipmentBean.getResponsibleTypeDesc().equals("")))
		{
			textResponsibleType.setText(shipmentBean.getResponsibleTypeDesc());
		}

		if(!(shipmentBean.getMessagingServiceDesc() == null || shipmentBean.getMessagingServiceDesc().equals("")))
		{
			textMessaging.setText(shipmentBean.getMessagingServiceDesc());
		}

		if(!(shipmentBean.getNoGuide() == null || shipmentBean.getNoGuide().equals("")))
		{
			textNoGuide.setText(shipmentBean.getNoGuide());
		}

		if(!(shipmentBean.getDate() == null || shipmentBean.getDate().equals("")))
		{
			textDate.setText(shipmentBean.getDate());
		}

        mShipmentUnits = new ArrayList<>();
        initUnitList();

        mShipmentSupplies = new ArrayList<>();
        initSupplyList();
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUnitList()
    {
        LinearLayout rootList = (LinearLayout) this.findViewById(R.id.process_shipment_layout);

        ArrayList<UnitBean> list = shipmentBean.getUnitBeanArray();
        Map<String, String> fieldMap = new HashMap<>();
        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                fieldMap.put("No. Serie:", list.get(i).getNoSerie());
                fieldMap.put("Marca:", list.get(i).getDescBrand());
                fieldMap.put("Modelo:", list.get(i).getDescModel());

                LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                relativeParams.setMargins(0, 0, 0, 10);

                generateItem(Constants.AGREGAR_UNIDAD, rootList, list.get(i).getId(), mKeysUnits, fieldMap);
            }
        }
    }

    private void initSupplyList()
    {
        LinearLayout rootList = (LinearLayout) this.findViewById(R.id.process_shipment_layout);

        ArrayList<SupplyBean> list = shipmentBean.getSupplyBeanArray();
        Map<String, String> fieldMap = new HashMap<>();
        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                fieldMap.put("Insumo:", list.get(i).getDescSupply());
                fieldMap.put("Cliente:", list.get(i).getDescClient());
                fieldMap.put("Cantidad:", list.get(i).getCount());

                LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                relativeParams.setMargins(0, 0, 0, 10);

                generateItem(Constants.AGREGAR_INSUMO, rootList, list.get(i).getIdSupply(), mKeySupplies, fieldMap);
            }
        }
    }

    private void generateItem(String typeAdd, LinearLayout layout, String id, String[] mapKeys,
                              Map<String, String> fieldMap)
    {
        LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(0, 0, 0, 10);

        LinearLayout rootItem = (LinearLayout) this.getLayoutInflater()
                .inflate(R.layout.layout_box_search, null, false);

        generateElements(typeAdd, rootItem, fieldMap);
        layout.addView(rootItem, relativeParams);

        ShipmentItem newItem = new ShipmentItem();
        newItem.itemView = layout;
        newItem.elements.add(id);
        for (String key : mapKeys)
        {
            newItem.elements.add(fieldMap.get(key));
        }

        switch (typeAdd)
        {
            case Constants.AGREGAR_UNIDAD:
                mShipmentUnits.add(newItem);
                break;

            case Constants.AGREGAR_INSUMO:
                mShipmentSupplies.add(newItem);
                break;
        }
    }

    private void generateElements(String typeAdd, LinearLayout rootItem, Map<String, String> fieldMap)
    {
        for (Map.Entry<String, String> entry : fieldMap.entrySet())
        {
            RelativeLayout item = (RelativeLayout) this.getLayoutInflater()
                    .inflate(R.layout.layout_box_search_item, null, false);

            TextView textTitle = (TextView) item.findViewById(R.id.search_item_title);
            textTitle.setText(entry.getKey());

            TextView textDesc = (TextView) item.findViewById(R.id.search_item_desc);
            textDesc.setText(entry.getValue());

            switch (typeAdd)
            {
                case Constants.AGREGAR_UNIDAD:
                    rootItem.setTag(mShipmentUnits.size());
                    break;

                case Constants.AGREGAR_INSUMO:
                    rootItem.setTag(mShipmentSupplies.size());
                    break;
            }

            rootItem.addView(item);
        }
    }

    private static class ShipmentItem
    {
        public ShipmentItem()
        {
            elements = new ArrayList<>();
        }

        public View itemView;

        public ArrayList<String> elements;
    }

    private ArrayList<ShipmentItem> mShipmentUnits;
    private ArrayList<ShipmentItem> mShipmentSupplies;

    private ShipmentBean shipmentBean;

    private String[] mKeysUnits = new String[] { "No. Serie:", "Marca:", "Modelo:" };
    private String[] mKeySupplies = new String[] { "Insumo:", "Cliente:", "Cantidad" };
}