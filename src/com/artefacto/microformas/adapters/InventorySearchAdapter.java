package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.artefacto.microformas.InventorySearchActivity;
import com.artefacto.microformas.R;
import com.artefacto.microformas.DetailUnitActivity;

public class InventorySearchAdapter extends ArrayAdapter<InventorySearchActivity.SearchResult>
{
	private ArrayList<InventorySearchActivity.SearchResult> items;

    public InventorySearchAdapter(Context context, int textViewResourceId,
                                  ArrayList<InventorySearchActivity.SearchResult> items)
    {
		super(context, textViewResourceId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
    {
		ViewHolder holder;
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.layout_item_inventory_result, null);
			
			holder = new ViewHolder();
			holder.TextViewNoSerie = (TextView) convertView.findViewById(R.id.listrowinventarioNoSerieTextViewContent);
			holder.TextViewModelo  = (TextView) convertView.findViewById(R.id.listrowinventarioModeloTextView);
			holder.TextViewCliente = (TextView) convertView.findViewById(R.id.listrowinventarioClienteTextViewContent);
			holder.TextViewStatus  = (TextView) convertView.findViewById(R.id.listrowinventarioStatusTextView);
			holder.ButtonVer	   = (Button) convertView.findViewById(R.id.listrowinventarioVerButton);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		InventorySearchActivity.SearchResult invetarioItem = items.get(position);
		if(invetarioItem != null)
		{
			//btnVer.setTag(lblNoSerie.getText())
			holder.TextViewNoSerie.setText(invetarioItem.noSerie);
			holder.TextViewModelo.setText(invetarioItem.model);
			holder.TextViewCliente.setText(invetarioItem.client);
			holder.TextViewStatus.setText(invetarioItem.status);
			holder.ButtonVer.setOnClickListener(new OnClickListener()
			{	
				public void onClick(View v)
				{
					View childView = (View)v.getParent().getParent();
					
					TextView textViewNoSerie = (TextView) childView.findViewById(R.id.listrowinventarioNoSerieTextViewContent);
					String noSerie = textViewNoSerie.getText().toString();
					
					Intent intent = new Intent(childView.getContext(), DetailUnitActivity.class);
					intent.putExtra("Unidad_Detail_No_Serie", noSerie);
					
					childView.getContext().startActivity(intent);
				}
			});
		}
		
		return convertView;
	}
	
	static class ViewHolder
    {
		TextView TextViewNoSerie;
		TextView TextViewModelo;
		TextView TextViewCliente;
		TextView TextViewStatus;

		Button 	ButtonVer;
	}
}