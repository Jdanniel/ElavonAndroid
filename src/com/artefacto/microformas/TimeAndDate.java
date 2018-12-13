package com.artefacto.microformas;

import java.util.Calendar;
import java.util.StringTokenizer;

import com.artefacto.microformas.uicomponents.DateButton;

import android.os.Bundle;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TimeAndDate extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_and_date);
		
		dateButton = (DateButton)findViewById(R.id.dateButtonNew);
		acceptButton = (Button) findViewById(R.id.timeDateAcceptButton);
		acceptButton.setOnClickListener(acceptButtonOnClickListener);
		
		initializeDate();
		initializeTime();
		
		//String day 	 = dateButton.getText().toString().substring(0,2);
		//String month = setNumericMonth(dateButton.getText().toString().substring(3, 7));
		//String year	 = dateButton.getText().toString().substring(8, 12);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.time_and_date, menu);
		return true;
	}
	
	protected void initializeDate() {
		Calendar calendar = Calendar.getInstance();
		
		dateButton.onDateSet(datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		dateButton.setOnClickListener(dateButtonOnClickListener);
		
		viewDate = (View) findViewById(android.R.id.content).getRootView();
	}
	
	protected void initializeTime() {
		timeButton = (Button) findViewById(R.id.timeButtonNew);
		timeButton.setText(this.currentTime());
		timeButton.setOnClickListener(timeButtonOnClickListener);
	}
	
	public String currentTime() {
		String hour;
		String minutes;
		
		Calendar calendar = Calendar.getInstance();
		
		hour = (calendar.get(Calendar.HOUR_OF_DAY) > 9) ? String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) : "0" + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		minutes = (calendar.get(Calendar.MINUTE) > 9) ? String.valueOf(calendar.get(Calendar.MINUTE)) : "0" + String.valueOf(calendar.get(Calendar.MINUTE));
		
		return hour + ":" + minutes;
	}
	
	public String setNumericMonth(String month){
		if(month.equals("ene."))
			month = "01";
		else if(month.equals("feb."))
			month = "02";
		else if(month.equals("mar."))
			month = "03";
		else if(month.equals("abr."))
			month = "04";
		else if(month.equals("may."))
			month = "05";
		else if(month.equals("jun."))
			month = "06";
		else if(month.equals("jul."))
			month = "07";
		else if(month.equals("ago."))
			month = "08";
		else if(month.equals("sep."))
			month = "09";
		else if(month.equals("oct."))
			month = "10";
		else if(month.equals("nov."))
			month = "11";
		else
			month = "12";	
		
		return month;
	}
	
	OnClickListener dateButtonOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			dateButton.onClick(viewDate);
		}
	};
	
	OnClickListener timeButtonOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			String time = timeButton.getText().toString();

            if (time != null && !time.equals("")) {
                StringTokenizer tokenizer = new StringTokenizer(time, ":");
                String hour = tokenizer.nextToken();
                String minutes = tokenizer.nextToken();
                
                timePickDialog = new TimePickerDialog(v.getContext(),
                        new TimePickHandler(), Integer.parseInt(hour),
                        Integer.parseInt(minutes), true);
            } else {
                timePickDialog = new TimePickerDialog(v.getContext(),
                        new TimePickHandler(), 10, 45, true);
            }
            
            timePickDialog.show();
		}
	};
	
	OnClickListener acceptButtonOnClickListener = new OnClickListener() {
		public void onClick(View v) {
			sendData();
		}
	};
	
	protected void sendData() {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("status", true);
		this.setResult(Activity.RESULT_OK, resultIntent);
		this.finish();
	}
	
	private class TimePickHandler implements OnTimeSetListener {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String hours ="00";
			String minutes = "00";
			
			if(hourOfDay < 10)
				hours = "0" + hourOfDay;
			else
				hours = String.valueOf(hourOfDay);
			
			if(minute < 10)
				minutes =  "0" + minute;
			else
				minutes = String.valueOf(minute);
			
			timeButton.setText(hours + ":" + minutes);
	        timePickDialog.hide();
	    }
	}
	
	TimePickerDialog timePickDialog;
	
	Button timeButton;
	Button acceptButton;
	
	DateButton dateButton;
	
	DatePicker datePicker;
	
	View viewDate;
}
