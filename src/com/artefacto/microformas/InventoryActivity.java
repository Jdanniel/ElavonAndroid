package com.artefacto.microformas;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.artefacto.microformas.services.GetUpdatesService;

public class InventoryActivity extends AppCompatActivity implements InventoryUnitsFragment.OnFragmentInteractionListener,
    InventorySuppliesFragment.OnFragmentInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        if (GetUpdatesService.isUpdating)
        {
            Toast.makeText(InventoryActivity.this, "Actualización en progreso, intente más tarde.", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

        Toolbar toolbarInventory = (Toolbar) this.findViewById(R.id.inventory_toolbar);
        TextView textTitle = (TextView) this.findViewById(R.id.inventory_toolbar_title);
        setTitle("");
        textTitle.setText(getString(R.string.title_activity_inventory));
        setSupportActionBar(toolbarInventory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mInventoryFragment = new InventoryFragment();
        transaction.replace(R.id.inventory_content, mInventoryFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    private InventoryFragment mInventoryFragment;
}
