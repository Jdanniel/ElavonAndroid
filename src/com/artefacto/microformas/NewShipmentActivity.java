package com.artefacto.microformas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.tasks.NewShipmentTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewShipmentActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shipment);

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.new_shipment_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.new_shipment_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_new_shipment));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences	= getSharedPreferences(Constants.PREF_CONFIG_USER, Context.MODE_PRIVATE);

        ArrayList<String> mListDestination = new ArrayList<>();
        mListDestination.add("Almacen");
        mListDestination.add("Ingenieros");

        /*
        *   --------------------------------
        *   |                              |
        *   |                              |
        *   |           BUSCAR             | ----------> mListDestination
        *   |                              |
        *   |                              |
        *   -------------------------------
        * */

        ArrayList<String> mListMessaging = new ArrayList<>();
        mListMessaging.add("Interno");
        mListMessaging.add("DHL");
        mListMessaging.add("Estafeta");
        mListMessaging.add("Multipack");
        mListMessaging.add("ETN");
        mListMessaging.add("Pullman");
        mListMessaging.add("Estrella Roja");
        mListMessaging.add("Aeromexpress");

        /*
        *   --------------------------------
        *   |                              |
        *   |                              |
        *   |           MENSAJERIA         | ----------> mListMessaging
        *   |                              |
        *   |                              |
        *   -------------------------------
        * */


        ArrayList<String> mListType = new ArrayList<>();
        mListType.add("Unidades");
        mListType.add("Insumos");

        /*
        *   --------------------------------
        *   |                              | -------------------> mListType
        *   |                              |  -----
        *   |           Items              |  | + |
        *   |                              |  -----
        *   |                              |
        *   -------------------------------
        * */

        ArrayAdapter<String> destinationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                mListDestination);

        ArrayAdapter<String> messagingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                mListMessaging);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                mListType);

        mSpinnerDestination = (Spinner) this.findViewById(R.id.new_shipment_spinner_destination);
        mSpinnerDestination.setAdapter(destinationAdapter);
        mSpinnerDestination.post(new Runnable() {
            public void run() {
                mSpinnerDestination.setOnItemSelectedListener(onDestinationItemSelected);
            }
        });

        mSpinnerMessaging = (Spinner) this.findViewById(R.id.new_shipment_spinner_messaging);
        mSpinnerMessaging.setAdapter(messagingAdapter);

        final Spinner spinnerType = (Spinner) this.findViewById(R.id.new_shipment_spinner_search);
        spinnerType.setAdapter(typeAdapter);
        spinnerType.post(new Runnable()
        {
            @Override
            public void run()
            {
                spinnerType.setOnItemSelectedListener(onTypeItemSelected);
            }
        });

        if(sharedPreferences.getString(Constants.AGREGAR_LAST_CATALOG, "").equals(Constants.AGREGAR_INGENIERO))
        {
            mSpinnerDestination.setSelection(1);
        }

        mTextDestination = (TextView) this.findViewById(R.id.new_shipment_text_destination);

        mEditGuide = (EditText) this.findViewById(R.id.new_shipment_edit_no_guide);

        mButtonSearch = (Button) this.findViewById(R.id.new_shipment_button_search);
        mButtonSearch.setOnClickListener(onButtonSearchClicked);
        mButtonSearch.setText(getString(R.string.base_search_warehouse));

        Button buttonAdd = (Button) this.findViewById(R.id.new_shipment_button_add);
        buttonAdd.setOnClickListener(onButtonAddClicked);

        /*
        * -----------
        * |         |
        * |    +    |   -------------------> buttonAdd
        * |         |
        * -----------
        * */

        Button buttonSend = (Button) this.findViewById(R.id.new_shipment_button_send);
        buttonSend.setOnClickListener(onButtonSendClicked);

        mRadioGroupPriority = (RadioGroup) this.findViewById(R.id.new_shipment_radio_priority);
        mRadioGroupPriority.setOnCheckedChangeListener(onGroupPriorityChanged);

        mLinearItems = (LinearLayout) this.findViewById(R.id.new_shipment_layout_items);

        mTypeDestination = Constants.AGREGAR_ALMACEN;
        mTypeAdd = Constants.AGREGAR_UNIDAD;

        if(mShipmentUnits == null)
        {
            mShipmentUnits = new ArrayList<>();
        }

        if(mShipmentSupplies == null)
        {
            mShipmentSupplies = new ArrayList<>();
        }

        mIdDestination = Integer.MIN_VALUE;
        mDescDestination = null;

        prioritySelected = 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SEARCH)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String type = data.getStringExtra(Constants.AGREGAR_TYPE);
                String typeId = data.getStringExtra(Constants.SEARCH_SHIPMENT_ID);
                String typeDesc = data.getStringExtra(Constants.SEARCH_SHIPMENT_DESC);

                if(typeId != null && typeDesc != null)
                {
                    switch(type)
                    {
                        case Constants.AGREGAR_ALMACEN:
                        case Constants.AGREGAR_INGENIERO:
                            mTypeDestination = type;
                            mIdDestination = Integer.parseInt(typeId);
                            mDescDestination = typeDesc;
                            mButtonSearch.setText(typeDesc);
                            break;

                        case Constants.AGREGAR_UNIDAD:
                            mTypeAdd = type;
                            Map<String, String> fieldMap = new HashMap<>();
                            fieldMap.put("Unidad:", typeDesc);
                            generateItem(typeId, "", mKeysUnits, fieldMap);
                            break;
                    }
                }
            }
        }

        if(requestCode == REQUEST_SEARCH_SUPPLY)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                mTypeAdd = Constants.AGREGAR_INSUMO;

                String typeId = data.getStringExtra(Constants.SEARCH_SHIPMENT_ID);
                String typeIdClient = data.getStringExtra(Constants.SEARCH_SHIPMENT_ID_CLIENT);
                String typeClient = data.getStringExtra(Constants.SEARCH_SHIPMENT_CLIENT);
                String typeDesc = data.getStringExtra(Constants.SEARCH_SHIPMENT_DESC);
                String typeCount = data.getStringExtra(Constants.SEARCH_SHIPMENT_COUNT);

                Map<String, String> fieldMap = new HashMap<>();
                fieldMap.put("Cliente:", typeClient);
                fieldMap.put("Insumo:", typeDesc);
                fieldMap.put("Cantidad:", typeCount);
                generateItem(typeId, typeIdClient, mKeysSupply, fieldMap);
            }
        }
    }

    private Button.OnClickListener onButtonSearchClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToNewShipmentSearch(mTypeDestination);
        }
    };

    private Button.OnClickListener onButtonAddClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goToNewShipmentSearch(mTypeAdd);
            /*
            *   mTypeAdd ---->  Constants.AGREGAR_UNIDAD
            *              |
            *              -------> Constants.AGREGAR_INSUMO
            * */
        }
    };

    private Button.OnClickListener onButtonSendClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            String guideNum = mEditGuide.getText().toString().trim().replace(" ", "");
            Integer Num = guideNum.length();
            String idMessaging = String.valueOf(mSpinnerMessaging.getSelectedItemPosition() + 1);
            Boolean validate =  false;
            if(mIdDestination != Integer.MIN_VALUE)
            {
                if(!guideNum.equals(""))
                {
                    if(Num > 0)
                    {
                        if(Num > 0)
                        {
                            if (idMessaging.equals("3")){
                                if (Num.equals(22)){
                                    validate = true;
                                }else{
                                    Toast.makeText(NewShipmentActivity.this, "La mensajeria seleccionada debe contar con 22 digitos.", Toast.LENGTH_SHORT).show();
                                }
                            }else if (idMessaging.equals("2")){
                                if (Num.equals(10)){
                                    validate = true;
                                }else{
                                    Toast.makeText(NewShipmentActivity.this, "La mensajeria seleccionada debe contar con 10 digitos.", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                validate = true;
                            }
                            if (validate){
                                if(mShipmentSupplies.size() > 0 || mShipmentUnits.size() > 0)
                                {
                                    String idUser = sharedPreferences.getString(Constants.PREF_USER_ID, "");
                                    String idDestinationType = String.valueOf(mSpinnerDestination.getSelectedItemPosition() + 1);

                                    ProgressDialog dialog = new ProgressDialog(NewShipmentActivity.this);
                                    dialog.setIndeterminate(true);

                                    String dataUnits = arrayToString(mShipmentUnits);
                                    String dataSupplies = arrayToString(mShipmentSupplies);

                                    NewShipmentTask shipmentTask = new NewShipmentTask(NewShipmentActivity.this, dialog);
                                    shipmentTask.execute(idUser, idDestinationType, String.valueOf(mIdDestination),
                                            idMessaging, String.valueOf(prioritySelected), guideNum, dataUnits,
                                            dataSupplies);
                                }
                                else
                                {
                                    Toast.makeText(NewShipmentActivity.this, "Debe ingresar al menos una unidad, insumo o sim", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(NewShipmentActivity.this, "El número de guía debe contener más de cuatro dígitos", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(NewShipmentActivity.this, "El número de guía no puede incluir sólo ceros", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(NewShipmentActivity.this, "No. Guia es incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(NewShipmentActivity.this, "Es necesario buscar el destino de envío", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private AdapterView.OnItemSelectedListener onTypeItemSelected = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 0:
                    mTypeAdd = Constants.AGREGAR_UNIDAD;
                    break;

                case 1:
                    mTypeAdd = Constants.AGREGAR_INSUMO;
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private AdapterView.OnItemSelectedListener onDestinationItemSelected = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (position)
            {
                case 0:
                    mTypeDestination = Constants.AGREGAR_ALMACEN;
                    mTextDestination.setText(getString(R.string.base_warehouse));
                    mButtonSearch.setText(getString(R.string.base_search_warehouse));
                    break;

                case 1:
                    mTypeDestination= Constants.AGREGAR_INGENIERO;
                    mTextDestination.setText(getString(R.string.base_engineers));
                    mButtonSearch.setText(getString(R.string.base_search_engineers));
                    break;
            }

            mIdDestination = Integer.MIN_VALUE;
            mDescDestination = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private View.OnLongClickListener onAddLongListener = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {
            mViewLongClicked = v;
            AlertDialog.Builder builder = new AlertDialog.Builder(NewShipmentActivity.this);
            builder.setMessage("Desea eliminar item?").setPositiveButton("OK", onDeleteDialogListener)
                    .setNegativeButton("Cancelar", onDeleteDialogListener).show();
            return false;
        }
    };

    private RadioGroup.OnCheckedChangeListener onGroupPriorityChanged = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch(checkedId)
            {
                case R.id.new_shipment_radio_low:
                    prioritySelected = 1;
                    break;

                case R.id.new_shipment_radio_medium:
                    prioritySelected = 2;
                    break;

                case R.id.new_shipment_radio_high:
                    prioritySelected = 3;
                    break;
            }
        }
    };

    public DialogInterface.OnClickListener onDeleteDialogListener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                    int position = (Integer) mViewLongClicked.getTag();
                    ((ViewManager) mViewLongClicked.getParent()).removeView(mViewLongClicked);

                    switch (mTypeAdd)
                    {
                        case Constants.AGREGAR_UNIDAD:
                            mShipmentUnits.remove(position);
                        break;

                        case Constants.AGREGAR_INSUMO:
                            mShipmentSupplies.remove(position);
                            break;
                    }

                    updateIdItems();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    private void goToNewShipmentSearch(String type)
    {
        if(!type.equals(Constants.AGREGAR_INSUMO))
        {
            Intent intent = new Intent(NewShipmentActivity.this, NewShipmentSearchActivity.class);
            intent.putExtra(Constants.AGREGAR_TYPE, type);
            startActivityForResult(intent, REQUEST_SEARCH);
        }
        else
        {
            ArrayList<String> idsAdded = GetIdFromShipment(mShipmentSupplies);

            Intent intent = new Intent(NewShipmentActivity.this, NewShipmentSearchSuppliesActivity.class);
            intent.putStringArrayListExtra("list", idsAdded);
            startActivityForResult(intent, REQUEST_SEARCH_SUPPLY);
        }
    }

    private void generateItem(String id, String idClient, String[] mapKeys, Map<String, String> fieldMap)
    {
        LinearLayout.LayoutParams relativeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(0, 0, 0, 10);

        LinearLayout rootItem = (LinearLayout) this.getLayoutInflater()
                .inflate(R.layout.layout_box_search, null, false);

        generateElements(rootItem, fieldMap);
        mLinearItems.addView(rootItem, relativeParams);

        ShipmentItem newItem = new ShipmentItem();
        newItem.itemView = rootItem;
        newItem.elements.add(id);
        if(!idClient.equals(""))
        {
            newItem.elements.add(idClient);
        }

        for (String key : mapKeys)
        {
            newItem.elements.add(fieldMap.get(key));
        }

        switch (mTypeAdd)
        {
            case Constants.AGREGAR_UNIDAD:
                mShipmentUnits.add(newItem);
                break;

            case Constants.AGREGAR_INSUMO:
                mShipmentSupplies.add(newItem);
                break;
        }
    }

    private void generateElements(LinearLayout rootItem, Map<String, String> fieldMap)
    {
        for (Map.Entry<String, String> entry : fieldMap.entrySet())
        {
            RelativeLayout item = (RelativeLayout) this.getLayoutInflater()
                    .inflate(R.layout.layout_box_search_item, null, false);

            TextView textTitle = (TextView) item.findViewById(R.id.search_item_title);
            textTitle.setText(entry.getKey());

            TextView textDesc = (TextView) item.findViewById(R.id.search_item_desc);
            textDesc.setText(entry.getValue());

            switch (mTypeAdd)
            {
                case Constants.AGREGAR_UNIDAD:
                    rootItem.setTag(mShipmentUnits.size());
                    break;

                case Constants.AGREGAR_INSUMO:
                    rootItem.setTag(mShipmentSupplies.size());
                    break;
            }

            rootItem.setOnLongClickListener(onAddLongListener);
            rootItem.addView(item);
        }
    }

    private void updateIdItems()
    {
        ArrayList<ShipmentItem> arrayItem = null;

        switch (mTypeAdd)
        {
            case Constants.AGREGAR_UNIDAD:
                arrayItem = mShipmentUnits;
                break;

            case Constants.AGREGAR_INSUMO:
                arrayItem = mShipmentSupplies;
                break;
        }

        if(arrayItem != null)
        {
            for(int i = 0; i < arrayItem.size(); i++)
            {
                arrayItem.get(i).itemView.setTag(i);
            }
        }
    }

    private String arrayToString(ArrayList<ShipmentItem> list)
    {
        String strList = "";
        for(int i = 0; i < list.size(); i++)
        {
            ArrayList<String> elements = list.get(i).elements;
            String strElements = "";
            for(int j = 0; j < elements.size(); j++)
            {
                strElements += elements.get(j) + ((j == elements.size() - 1) ? "" : ",");
            }

            strList += strElements + ((i == list.size() - 1) ? "" : "#");
        }

        return strList;
    }

    private ArrayList<String> GetIdFromShipment(ArrayList<ShipmentItem> list)
    {
        ArrayList<String> ids =new ArrayList<>();
        for(int i = 0; i < list.size(); i++)
        {
            ids.add(list.get(i).elements.get(0));
        }

        return ids;
    }

    static class ShipmentItem
    {
        public ShipmentItem()
        {
            elements = new ArrayList<>();
        }

        public View itemView;

        public ArrayList<String> elements;
    }

    public static int REQUEST_SEARCH        = 7100;
    public static int REQUEST_SEARCH_SUPPLY = 7200;

    private LinearLayout mLinearItems;

    private SharedPreferences sharedPreferences;

    private ArrayList<ShipmentItem> mShipmentUnits = null;
    private ArrayList<ShipmentItem> mShipmentSupplies = null;

    private View mViewLongClicked;

    private Spinner mSpinnerDestination;
    private Spinner mSpinnerMessaging;

    private RadioGroup mRadioGroupPriority;

    private EditText mEditGuide;

    private Button mButtonSearch;

    private int prioritySelected;
    private int mIdDestination;

    private String mTypeDestination;
    private String mTypeAdd;
    private String mDescDestination;

    private String[] mKeysUnits = new String[] {"Unidad:"};
    private String[] mKeysSupply = new String[] {"Cliente:", "Insumo:", "Cantidad:"};

    private TextView mTextDestination;
}
