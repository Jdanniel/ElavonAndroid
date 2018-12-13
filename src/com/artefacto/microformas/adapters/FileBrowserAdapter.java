package com.artefacto.microformas.adapters;

import java.util.List;

import com.artefacto.microformas.R;
import com.artefacto.microformas.FileBrowserActivity.FileBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class FileBrowserAdapter extends ArrayAdapter<FileBean>{

	private List<FileBean> items;
	
	public FileBrowserAdapter(Context context, int textViewResourceId, List<FileBean> items) {
		super(context, textViewResourceId, items);
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null){
			LayoutInflater vi=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.activity_file_browser, null);
		}

		FileBean fileBean = items.get(position);

		if(fileBean != null){
			TextView 	fileNameTextView		 	= (TextView)v.findViewById(R.id.fileName);
			fileNameTextView.setText(fileBean.getFileName());
			if (fileBean.isDirectory())
				fileNameTextView.setCompoundDrawablesWithIntrinsicBounds(this.getContext().getResources().getDrawable(R.drawable.androidfolder),null,null,null);
			else
				fileNameTextView.setCompoundDrawablesWithIntrinsicBounds(this.getContext().getResources().getDrawable(R.drawable.pdf),null,null,null);
		}
		return v;
	}
	
}
