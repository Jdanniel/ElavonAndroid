package com.artefacto.microformas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.beans.ShipmentBean;
import com.artefacto.microformas.beans.SimBean;
import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.beans.UnitBean;
import com.artefacto.microformas.connection.HTTPConnections;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.ConfirmReceptionTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReceptionActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.reception_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.reception_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_shipment_detail));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        shipmentBean = (ShipmentBean) intent.getSerializableExtra("bean");

        TextView textId = (TextView)findViewById(R.id.reception_text_id);
        TextView textResponsible 	= (TextView)findViewById(R.id.reception_text_responsible);
        TextView textResponsibleType = (TextView)findViewById(R.id.reception_text_responsible_type);
        TextView textMessaging = (TextView)findViewById(R.id.reception_text_messaging);
        TextView textNoGuide = (TextView)findViewById(R.id.reception_text_no_guide);
        TextView textDate = (TextView)findViewById(R.id.reception_text_date);

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

        Button buttonAccept = (Button)findViewById(R.id.reception_button_accept);
        buttonAccept.setOnClickListener(onButtonAcceptClicked);

        isRequestActivity = false;
    }

    @Override
    public void onBackPressed()
    {
        setResult(isRequestActivity ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQUEST_CODE_TERMS)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Bundle extraBundle = intent.getExtras();
                Boolean accepted = extraBundle.getBoolean("USER_ACCEPTED");
                if(accepted)
                {
                    SendReception();
                }
                else
                {
                    Toast.makeText(this, "Tienes que aceptar los terminos y condiciones para poder enviar la recepción.", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
        LinearLayout rootList = (LinearLayout) this.findViewById(R.id.reception_layout);

        ArrayList<UnitBean> list = shipmentBean.getUnitBeanArray();
        Map<String, String> fieldMap = new HashMap<>();
        for(int i = 0; i < list.size(); i++)
        {
            fieldMap.put("No. Serie:", list.get(i).getNoSerie());
            fieldMap.put("Marca:", list.get(i).getDescBrand());
            fieldMap.put("Modelo:", list.get(i).getDescModel());

            LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.setMargins(0, 0, 0, 10);

            RelativeLayout rootItem = (RelativeLayout) this.getLayoutInflater()
                    .inflate(R.layout.layout_box_checkbox, null, false);
            rootItem.setLayoutParams(relativeParams);

            RelativeLayout itemLayout = (RelativeLayout) rootItem.findViewById(R.id.box_checkbox_item);

            CheckBox itemCheckBox = (CheckBox) rootItem.findViewById(R.id.box_checkbox_status);
            itemCheckBox.setTag(list.get(i));
            itemCheckBox.setOnClickListener(onCheckBoxClicked);
            list.get(i).setChecked(false);

            generateItem(Constants.AGREGAR_UNIDAD, itemLayout, list.get(i).getId(), mKeysUnits, fieldMap);
            rootList.addView(rootItem);
        }
    }

    private void initSupplyList()
    {
        LinearLayout rootList = (LinearLayout) this.findViewById(R.id.reception_layout);

        ArrayList<SupplyBean> list = shipmentBean.getSupplyBeanArray();
        Map<String, String> fieldMap = new HashMap<>();
        for(int i = 0; i < list.size(); i++)
        {
            fieldMap.put("Insumo:", list.get(i).getDescSupply());
            fieldMap.put("Cliente:", list.get(i).getDescClient());
            fieldMap.put("Cantidad:", list.get(i).getCount());

            LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.setMargins(0, 0, 0, 10);

            RelativeLayout rootItem = (RelativeLayout) this.getLayoutInflater()
                    .inflate(R.layout.layout_box_checkbox, null, false);
            rootItem.setLayoutParams(relativeParams);

            RelativeLayout itemLayout = (RelativeLayout) rootItem.findViewById(R.id.box_checkbox_item);

            CheckBox itemCheckBox = (CheckBox) rootItem.findViewById(R.id.box_checkbox_status);
            itemCheckBox.setTag(list.get(i));
            itemCheckBox.setOnClickListener(onCheckBoxClicked);
            list.get(i).setChecked(false);

            generateItem(Constants.AGREGAR_INSUMO, itemLayout, list.get(i).getIdSupply(), mKeySupplies, fieldMap);
            rootList.addView(rootItem);
        }
    }

    private void initSimList()
    {
        LinearLayout rootList = (LinearLayout) this.findViewById(R.id.reception_layout);

        ArrayList<SimBean> list = shipmentBean.getSimBeanArray();
        Map<String, String> fieldMap = new HashMap<>();
        for(int i = 0; i < list.size(); i++)
        {
            fieldMap.put("No. Sim:", list.get(i).getNoSim());
            fieldMap.put("Cliente:", list.get(i).getDescClient());
            fieldMap.put("Carrier:", list.get(i).getDescCarrier());

            LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            relativeParams.setMargins(0, 0, 0, 10);

            RelativeLayout rootItem = (RelativeLayout) this.getLayoutInflater()
                    .inflate(R.layout.layout_box_checkbox, null, false);
            rootItem.setLayoutParams(relativeParams);

            RelativeLayout itemLayout = (RelativeLayout) rootItem.findViewById(R.id.box_checkbox_item);

            CheckBox itemCheckBox = (CheckBox) rootItem.findViewById(R.id.box_checkbox_status);
            itemCheckBox.setTag(list.get(i));
            itemCheckBox.setOnClickListener(onCheckBoxClicked);
            list.get(i).setChecked(false);

            generateItem(Constants.AGREGAR_INSUMO, itemLayout, list.get(i).getIdSim(), mKeySupplies, fieldMap);
            rootList.addView(rootItem);
        }
    }

    private OnClickListener onCheckBoxClicked = new OnClickListener() {
        @Override
        public void onClick(View v)
        {
            CheckBox myCheckBox = (CheckBox) v;
            UnitBean myUnitBean = null;
            SupplyBean mySupplyBean = null;
            SimBean mySimBean = null;

            if(v.getTag() instanceof UnitBean)
            {
                myUnitBean = (UnitBean) v.getTag();
            }

            if(v.getTag() instanceof SupplyBean)
            {
                mySupplyBean = (SupplyBean) v.getTag();
            }

            if(v.getTag() instanceof SimBean)
            {
                mySimBean = (SimBean) v.getTag();
            }

            boolean status = myCheckBox.isChecked();
            if(myUnitBean != null)
            {
                myUnitBean.setChecked(status);
            }
            else if(mySupplyBean != null)
            {
                mySupplyBean.setChecked(status);
            }
            else if(mySimBean != null)
            {
                mySimBean.setChecked(status);
            }
        }
    };

    private void generateItem(String typeAdd, RelativeLayout layout, String id, String[] mapKeys,
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

    private OnClickListener onButtonAcceptClicked = new OnClickListener()
    {
        public void onClick(View arg)
        {
            Intent intent = new Intent(ReceptionActivity.this, TermsActivity.class);
            ReceptionActivity.this.startActivityForResult(intent, REQUEST_CODE_TERMS);
        }
    };

    public void updateUnidades(String message)
    {
        if(message.equals(HTTPConnections.REQUEST_OK))
        {
            Toast.makeText(getApplicationContext(), "¡Se han recibido las unidades exitosamente!", Toast.LENGTH_SHORT).show();
            isRequestActivity = true;
            onBackPressed();
        }
        else
        {
            isRequestActivity = false;
            Toast.makeText(getApplicationContext(), "No se pudo confirmar la recepción, intente más tarde.", Toast.LENGTH_SHORT).show();
        }
    }

    public void SendReception()
    {
        ArrayList<UnitBean> selectedUnits = new ArrayList<>();
        ArrayList<SupplyBean> selectedSupplies = new ArrayList<>();
        ArrayList<SimBean> selectedSims = new ArrayList<>();

        ArrayList<UnitBean> listUnits = shipmentBean.getUnitBeanArray();
        for(int i = 0; i < listUnits.size(); i++)
        {
            if(listUnits.get(i).isChecked())
            {
                selectedUnits.add(listUnits.get(i));
            }
        }

        ArrayList<SupplyBean> listSupplies = shipmentBean.getSupplyBeanArray();
        for(int i = 0; i < listSupplies.size(); i++)
        {
            if(listSupplies.get(i).isChecked())
            {
                selectedSupplies.add(listSupplies.get(i));
            }
        }

        ProgressDialog progressDialog = new ProgressDialog(ReceptionActivity.this);
        progressDialog.setMessage("Enviando solicitud");
        progressDialog.setCancelable(false);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONFIG_USER, MODE_PRIVATE);

        ConfirmReceptionTask receptionTask = new ConfirmReceptionTask(ReceptionActivity.this, progressDialog,
                shipmentBean.getIdShipment(), sharedPreferences.getString(Constants.PREF_USER_ID, ""),
                selectedUnits, selectedSupplies);
        receptionTask.execute();
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

    public static int REQUEST_CODE_TERMS = 100;

    private ArrayList<ShipmentItem> mShipmentUnits;
    private ArrayList<ShipmentItem> mShipmentSupplies;

    private ShipmentBean shipmentBean;

    private boolean isRequestActivity;

    private String[] mKeysUnits = new String[] { "No. Serie:", "Marca:", "Modelo:" };
    private String[] mKeySupplies = new String[] { "Insumo:", "Cliente:", "Cantidad:" };
}