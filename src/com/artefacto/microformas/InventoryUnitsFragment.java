package com.artefacto.microformas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.services.GetUpdatesService;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;
import com.artefacto.microformas.tasks.ShipmentStatusTask;

import java.util.ArrayList;

public class InventoryUnitsFragment extends Fragment
{
    public InventoryUnitsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_units, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initUnits();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.inventory_units, menu);
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

            case R.id.action_inventory_units_no_serie:
                initSearchDialog(Constants.BUSQUEDA_INVENTARIO_TIPO_NO_SERIE);
                return true;

            case R.id.action_inventory_units_model:
                initSearchDialog(Constants.BUSQUEDA_INVENTARIO_TIPO_MODELO);
                return true;

            case R.id.action_inventory_units_client:
                initSearchDialog(Constants.BUSQUEDA_INVENTARIO_TIPO_CLIENTE);
                return true;

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
            mListener = (InventoryUnitsFragment.OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
    }

    private void initUnits()
    {
        SQLiteHelper sqliteHelper = new SQLiteHelper(getActivity(), null);
        SQLiteDatabase db = sqliteHelper.getWritableDB();

        String query = "select " + SQLiteHelper.UNIDAD_DESC_CLIENTE + ", "
                + SQLiteHelper.UNIDAD_DESC_MODELO + ","+SQLiteHelper.UNIDAD_IS_DANIADA
                + "," + SQLiteHelper.UNIDAD_IS_NUEVA + " from "+SQLiteHelper.UNIDAD_DB_NAME
                + " order by "+SQLiteHelper.UNIDAD_DESC_CLIENTE + "," + SQLiteHelper.UNIDAD_DESC_MODELO
                + ","+SQLiteHelper.UNIDAD_IS_DANIADA + "," + SQLiteHelper.UNIDAD_IS_NUEVA;

        Cursor cursor = db.rawQuery(query, null);

        String prevClient="";
        String prevModel = "";

        ArrayList<Unit> list = null;
        Unit unit = null;
        try
        {
            if (cursor != null )
            {
                if  (cursor.moveToFirst())
                {
                    do
                    {
                        if (!cursor.getString(0).equals(prevClient))
                        {
                            if (list != null)
                            {
                                list.add(unit);
                                initClientView(prevClient, list);
                            }

                            list = new ArrayList<>();
                            prevClient = cursor.getString(0);
                            prevModel="";
                        }

                        if (!cursor.getString(1).equals(prevModel))
                        {
                            if (!prevModel.equals("") && list != null)
                            {
                                list.add(unit);
                            }

                            unit = new Unit();
                            unit.model = cursor.getString(1);
                            prevModel = cursor.getString(1);
                        }

                        if(unit != null)
                        {
                            if (cursor.getString(2).equals("1"))
                            {
                                unit.broken++;
                            }
                            else if (cursor.getString(3).equals("1"))
                            {
                                unit.news++;
                            }
                            else {
                                unit.used++;
                            }
                        }
                    } while(cursor.moveToNext());
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(cursor != null)
            {
                cursor.close();
                db.close();
            }
        }

        if (list!=null)
        {
            list.add(unit);
            initClientView(prevClient, list);
        }
    }

    public void initClientView(String client, ArrayList<Unit> units)
    {
        Activity activity = getActivity();

        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.inv_units_layout);
        float size = new TextView(activity).getTextSize() * 1.3f;

        LinearLayout.LayoutParams textClientParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textClientParams.setMargins(0, (int) getResources().getDimension(R.dimen.base_form_vertical), 0, 0);

        TextView textClient = new TextView(activity);
        textClient.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textClient.setText(client);
        textClient.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
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
        rowTitlesParams.setMargins(0,(int) getResources().getDimension(R.dimen.base_form_vertical),
                0, (int) getResources().getDimension(R.dimen.base_form_vertical));
        rowTitles.setLayoutParams(rowTitlesParams);

        TextView textTitleModel = new TextView(activity);
        textTitleModel.setText("Modelo");
        textTitleModel.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textTitleModel.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textTitleModel.setTypeface(null, Typeface.ITALIC);
        textTitleModel.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        TextView textTitleNews = new TextView(activity);
        textTitleNews.setText("Nva");
        textTitleNews.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textTitleNews.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textTitleNews.setTypeface(null, Typeface.ITALIC);
        textTitleNews.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        TextView textTitleUsed = new TextView(activity);
        textTitleUsed.setText("Usd");
        textTitleUsed.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textTitleUsed.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textTitleUsed.setTypeface(null, Typeface.ITALIC);
        textTitleUsed.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        TextView textTitleBroken = new TextView(activity);
        textTitleBroken.setText("Dan");
        textTitleBroken.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textTitleBroken.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        textTitleBroken.setTypeface(null, Typeface.ITALIC);
        textTitleBroken.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        rowTitles.addView(textTitleModel);
        rowTitles.addView(textTitleNews);
        rowTitles.addView(textTitleUsed);
        rowTitles.addView(textTitleBroken);
        table.addView(rowTitles);

        for (int i = 0; i < units.size(); i++)
        {
            Unit unit = units.get(i);

            TableRow row = new TableRow(activity);
            TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams (TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0,(int) getResources().getDimension(R.dimen.base_form_vertical),
                    0, (int) getResources().getDimension(R.dimen.base_form_vertical));
            row.setLayoutParams(rowParams);
            row.setLayoutParams(rowParams);

            TextView textModel = new TextView(activity);
            textModel.setText(unit.model);
            textModel.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textModel.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            textModel.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            TextView textNews = new TextView(activity);
            textNews.setText(String.valueOf(unit.news));
            textNews.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textNews.setOnClickListener(new RowItemListener(Constants.BUSQUEDA_INVENTARIO_TIPO_USO_NUEVAS,
                    client, unit.model));
            textNews.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            textNews.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1.0f));

            TextView textUsed = new TextView(activity);
            textUsed.setText(String.valueOf(unit.used));
            textUsed.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textUsed.setOnClickListener(new RowItemListener(Constants.BUSQUEDA_INVENTARIO_TIPO_USO_USADAS,
                    client, unit.model));
            textUsed.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            textUsed.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1.0f));

            TextView textBroken = new TextView(activity);
            textBroken.setText(String.valueOf(unit.broken));
            textBroken.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textBroken.setOnClickListener(new RowItemListener(Constants.BUSQUEDA_INVENTARIO_TIPO_USO_DANIANDAS,
                    client, unit.model));
            textBroken.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            textBroken.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.FILL_PARENT, 1.0f));

            row.addView(textModel);
            row.addView(textNews);
            row.addView(textUsed);
            row.addView(textBroken);
            table.addView(row);
        }

        layout.addView(table);

        View separator = new View(activity);
        separator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,1));
        separator.setBackgroundColor(ContextCompat.getColor(activity, R.color.black_gray));
        layout.addView(separator);
    }

    private void initSearchDialog(final int type)
    {
        String title = "";
        String keyField = "";
        switch (type)
        {
            case Constants.BUSQUEDA_INVENTARIO_TIPO_NO_SERIE:
                title = "No. Serie";
                keyField = "Criterio_Busqueda_No_Serie";
                break;

            case Constants.BUSQUEDA_INVENTARIO_TIPO_MODELO:
                title = "Modelo";
                keyField = "Criterio_Busqueda_Modelo";
                break;

            case Constants.BUSQUEDA_INVENTARIO_TIPO_CLIENTE:
                title = "Cliente";
                keyField = "Criterio_Busqueda_Cliente";
                break;
        }

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(30, 0, 30, 0);
        layout.addView(input, params);

        final String finalKeyField = keyField;
        new AlertDialog.Builder(getActivity())
            .setTitle("")
            .setMessage(title)
            .setView(layout)
            .setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    String keySearch = input.getText().toString().trim();
                    if (keySearch.length() > 0)
                    {
                        Intent intent = new Intent(getActivity(), InventorySearchActivity.class);
                        intent.putExtra("type", type);
                        intent.putExtra(finalKeyField, keySearch);
                        intent.putExtra("Criterio_Busqueda_Uso", Constants.BUSQUEDA_INVENTARIO_TIPO_USO_TODAS);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Criterio de busqueda no puede ser vacio.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).show();
    }

    static class Unit
    {
        public String model;

        public int broken = 0;
        public int news = 0;
        public int used = 0;
    }

    public class RowItemListener implements View.OnClickListener
    {
        private int mType;

        private String mClient;
        private String mModel;

        public RowItemListener(int type, String client,String model)
        {
            mType = type;
            mClient = client;
            mModel = model;
        }

        public void onClick(View v)
        {
            Context context = v.getContext();

            Intent intent = new Intent(context, InventorySearchActivity.class);
            intent.putExtra("Criterio_Busqueda_Cliente", mClient);
            intent.putExtra("Criterio_Busqueda_Modelo", mModel);
            intent.putExtra("type", Constants.BUSQUEDA_INVENTARIO_TIPO_CLIENTE);
            intent.putExtra("Criterio_Busqueda_Uso", mType);
            context.startActivity(intent);
        }
    }

    private OnFragmentInteractionListener mListener;
}
