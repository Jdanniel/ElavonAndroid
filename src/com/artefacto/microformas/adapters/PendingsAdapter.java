package com.artefacto.microformas.adapters;

import java.util.ArrayList;

import com.artefacto.microformas.R;
import com.artefacto.microformas.application.MicroformasApp;
import com.artefacto.microformas.constants.Constants;
import com.artefacto.microformas.sqlite.SQLiteDatabase;
import com.artefacto.microformas.sqlite.SQLiteHelper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PendingsAdapter extends ArrayAdapter<String> { 
	ArrayList<String> items;
	SQLiteHelper 	sqliteHelper;
	SQLiteDatabase 	db;
	
	public PendingsAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		
		sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
		db 				= sqliteHelper.getWritableDB();
	}
	
	public void reload(ArrayList<String> list){
		items.clear();
		items.addAll(list);
		notifyDataSetChanged();
		sqliteHelper 	= new SQLiteHelper(MicroformasApp.getAppContext(), null);
		db 				= sqliteHelper.getWritableDB();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_pendings_list, null);
		}
		TextView 	tipo 		= (TextView)v.findViewById(R.id.PendingType);
		TextView 	parametros 	= (TextView)v.findViewById(R.id.Parameters);
		
		String pendingsStr = items.get(position);
		
		String[] fragments = pendingsStr.split(Constants.ASYNC_SEPARATOR_PARAMETER);
		
		StringBuilder builder = new StringBuilder();
		
		for (int i=1;i<fragments.length;i++){
			builder.append(fragments[i]);
			builder.append(" | ");
		}
			
		parametros.setText(builder.toString());
		
		if (fragments[0].equals(Constants.ASYNC_CHANGE_STATUS)){
			tipo.setText("Cambio de status");
			parametros.setText(getStatusString(fragments));
		}
		else if(fragments[0].equals(Constants.ASYNC_SEND_COMMENT)){
			tipo.setText("Envío de Comentario");
			parametros.setText(getCommentString(fragments));
		}
		else if(fragments[0].equals(Constants.ASYNC_SEND_SPARES)){
			tipo.setText("Solicitud de refacciones");
		}
		else if(fragments[0].equals(Constants.ASYNC_SEND_VIATICOS)){
			tipo.setText("Solicitud de viaticos");
			parametros.setText(getViaticosString(fragments));
		}
		else if(fragments[0].equals(Constants.ASYNC_UPLOAD_IMAGE)){
			tipo.setText("Envío de foto");
		}
		else if(fragments[0].equals(Constants.ASYNC_UPLOAD_PDF)){
			tipo.setText("Envío de pdf");
		}

		if (position == (items.size()-1)){
			db.close();
		}
		
		return v;
	}
	
	public String getStatusString(String[] fragments){
		Cursor c = db.rawQuery("select "+SQLiteHelper.STATUS_DESC_STATUS+" from "+SQLiteHelper.STATUS_DB_NAME+" where "+SQLiteHelper.STATUS_ID_STATUS+" = "+fragments[3],null);
		if (c.moveToFirst()){
			return ("Solicitud: "+fragments[1]+" IdUsuario: "+fragments[2]+" Status: "+c.getString(0));
		}
		return "Solicitud: "+fragments[1]+" IdUsuario: "+fragments[2]+" Status: ";
	}
	
	public String getCommentString(String[] fragments){
		return "Solicitud: "+fragments[1]+" IdUsuario: "+fragments[2]+" Comentario: \""+fragments[3]+"\"";
	}
	
	public String getViaticosString(String[] fragments){
		String[] viaticos = fragments[6].split(",");
		String[] costos = fragments[7].split(",");
		int numViaticos = viaticos.length;
		StringBuilder builder = new StringBuilder();
		builder.append("Solicitud: "+ fragments[1]+" IdUsuario: "+fragments[2]+" Lugar: "+fragments[3]+" Observaciones: "+fragments[4]+" Prioridad: ");
		
		if (fragments[5].equals("1")){
			builder.append("Baja ");
		}
		else if (fragments[5].equals("2")){
			builder.append("Media ");
		}
		else if (fragments[5].equals("3")){
			builder.append("Alta ");
		}
		
		for (int i=0;i<numViaticos;i++){
			Cursor c = db.rawQuery("select "+SQLiteHelper.VIATICOS_DESC_VIATICO+" from "+SQLiteHelper.VIATICOS_DB_NAME+" where "+SQLiteHelper.VIATICOS_ID_VIATICO+" = "+viaticos[i],null);
			if (c.moveToFirst()){
				builder.append(c.getString(0));
				builder.append('-');
				builder.append('$');
				builder.append(costos[i]);
				builder.append(' ');
			}
		}
		return builder.toString();
	}	
}