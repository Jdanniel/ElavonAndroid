package com.artefacto.microformas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.beans.SupplyBean;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ShipmentStatusTask;

import java.util.ArrayList;

public class InventorySuppliesFragment extends Fragment
{
    public InventorySuppliesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {   // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_supplies, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initSupplies();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.inventory_supplies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        ShipmentStatusTask pendientesTask;
        ProgressDialog progressDialog;
        Intent intent;

        switch (item.getItemId())
        {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;

            case R.id.action_inventory_units_new_shipment:
                intent = new Intent(getActivity(), NewShipmentActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_inventory_units_shipments_in_process:
                if (GetUpdatesService.isUpdating)
                {
                    Toast.makeText(getActivity(), "Actualización en progreso, intente más tarde.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Adquiriendo detalles de envíos en proceso");
                progressDialog.setCancelable(false);

                pendientesTask = new ShipmentStatusTask(getActivity(), progressDialog, 0);
                pendientesTask.execute();
                return true;

            case R.id.action_inventory_units_receptions:
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Adquiriendo detalles de envíos recibidos");
                progressDialog.setCancelable(false);

                pendientesTask = new ShipmentStatusTask(getActivity(), progressDialog, 1);
                pendientesTask.execute();
                return true;
        }

        return true;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            mListener = (InventorySuppliesFragment.OnFragmentInteractionListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    private void initSupplies()
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(getActivity(), null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();

        String query = "select " + SQLiteHelper.INV_SUPPLIES_DESC_CLIENT + ", "
                + SQLiteHelper.INV_SUPPLIES_DESC + "," + SQLiteHelper.INV_SUPPLIES_TOTAL
                + " from " + SQLiteHelper.INV_SUPPLIES_DB_NAME
                + " WHERE " + SQLiteHelper.INV_SUPPLIES_TOTAL + " > 0"
                + " order by " + SQLiteHelper.INV_SUPPLIES_DESC_CLIENT;

        String prevClient = "";

        ArrayList<SupplyBean> list = null;
        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor != null)
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        if(!cursor.getString(0).equals(prevClient))
                        {
                            if(list != null)
                            {
                                if(list.size() > 0)
                                {
                                    initSuppliesView(list.get(0).getDescClient(), list);
                                }
                            }

                            list = new ArrayList<>();
                            prevClient = cursor.getString(0);
                        }

                        if(list != null)
                        {
                            SupplyBean mySupply = new SupplyBean();
                            mySupply.setDescClient(cursor.getString(0));
                            mySupply.setDescSupply(cursor.getString(1));
                            mySupply.setCount(cursor.getString(2));

                            list.add(mySupply);
                        }
                    } while (cursor.moveToNext());

                    if(list != null)
                    {
                        if(list.size() > 0)
                        {
                            initSuppliesView(list.get(0).getDescClient(), list);
                        }
                    }
                }
            }
        }
        catch (Exception ex)
        {
            Log.d("Microformas", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void initSuppliesView(String client, ArrayList<SupplyBean> supplies)
    {
        Activity activity = getActivity();

        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.inv_supplies_layout);
        float size = new TextView(activity).getTextSize() * 1.3f;

        LinearLayout.LayoutParams textClientParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textClientParams.setMargins(0, (int) getResources().getDimension(R.dimen.base_form_vertical), 0, 0);

        TextView textClient = new TextView(activity);
        textClient.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textClient.setText(client);
        textClient.setGravity(Gravity.CENTER);
        textClient.setTypeface(null, Typeface.BOLD);
        textClient.setLayoutParams(textClientParams);
        layout.addView(textClient);

        TableLayout table = new TableLayout(activity);
        table.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);
        table.setColumnShrinkable(0, true);

        TableRow rowTitles = new TableRow(activity);

        TableLayout.LayoutParams rowTitlesParams = new TableLayout.LayoutParams (TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        rowTitlesParams.setMargins((int) getResources().getDimension(R.dimen.base_form_vertical),
                (int) getResources().getDimension(R.dimen.base_form_vertical),
                (int) getResources().getDimension(R.dimen.base_form_vertical),
                (int) getResources().getDimension(R.dimen.base_form_vertical));
        rowTitles.setLayoutParams(rowTitlesParams);

        TextView textTitleSupply = new TextView(activity);
        textTitleSupply.setText("Insumo");
        textTitleSupply.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        textTitleSupply.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textTitleSupply.setTypeface(null, Typeface.ITALIC);
        textTitleSupply.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        TextView textTitleCount = new TextView(activity);
        textTitleCount.setText("Cantidad");
        textTitleCount.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        textTitleCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textTitleCount.setTypeface(null, Typeface.ITALIC);
        textTitleCount.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        rowTitles.addView(textTitleSupply);
        rowTitles.addView(textTitleCount);
        table.addView(rowTitles);

        for (int i = 0; i < supplies.size(); i++)
        {
            SupplyBean supply = supplies.get(i);

            TableRow row = new TableRow(activity);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams (TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins((int) getResources().getDimension(R.dimen.base_form_vertical),
                    (int) getResources().getDimension(R.dimen.base_form_vertical),
                    (int) getResources().getDimension(R.dimen.base_form_vertical),
                    (int) getResources().getDimension(R.dimen.base_form_vertical));
            row.setLayoutParams(rowParams);

            TextView textSupply = new TextView(activity);
            textSupply.setText(supply.getDescSupply());
            textSupply.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            textSupply.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            textSupply.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3.0f));

            TextView textCount = new TextView(activity);
            textCount.setText(String.valueOf(supply.getCount()));
            textCount.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            textCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            textCount.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1.0f));

            row.addView(textSupply);
            row.addView(textCount);
            table.addView(row);
        }

        layout.addView(table);

        View separator = new View(activity);
        separator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,1));
        separator.setBackgroundColor(ContextCompat.getColor(activity, R.color.black_gray));
        layout.addView(separator);
    }

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
    }

    private OnFragmentInteractionListener mListener;

    static class Supply
    {
        public Supply(String client, String supply, String count)
        {
            this.client = client;
            this.supply = supply;
            this.count = count;
        }

        public String client;
        public String supply;
        public String count;
    }
}
