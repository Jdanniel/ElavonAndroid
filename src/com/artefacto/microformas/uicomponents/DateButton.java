package com.artefacto.microformas.uicomponents;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v7.widget.AppCompatButton;
import android.widget.DatePicker;


public class DateButton extends AppCompatButton implements OnClickListener, OnDateSetListener {
    private int mYear;
    private int mMonth;
    private int mDay;

    public DateButton(Context context) {
    	super(context);
    }

    public DateButton(Context arg0, AttributeSet arg1) {
    	super(arg0, arg1);
    }

    public DateButton(Context arg0, AttributeSet arg1, int arg2) {
    	super(arg0, arg1, arg2);
    }

    public void onClick(View v) {
    	showDialog();
    }
    
    private void showDialog() {
        DatePickerDialog dialog = new DatePickerDialog(	getContext(),
        												this, 
        												mYear, 
        												mMonth, 
        												mDay);
        dialog.show();
    }

    public void onDateSet(	DatePicker view, 
    						int year, 
    						int monthOfYear, 
    						int dayOfMonth) {
    	//Se invirtió mes y dia por un error en el flujo
    	mYear 	= year;
    	mMonth 	= monthOfYear;
    	mDay 	= dayOfMonth;
    	updateDisplay();
    }

    private void updateDisplay() {
    	String monthRedux = "";
    	switch(Integer.valueOf(pad(mMonth)) + 1){
    		case 1: monthRedux = "ene.";
    				break;
    		case 2: monthRedux = "feb.";
			 		break;
    		case 3: monthRedux = "mar.";
			 		break;
    		case 4: monthRedux = "abr.";
			 		break;
    		case 5: monthRedux = "may.";
			 		break;
    		case 6: monthRedux = "jun.";
			 		break;
    		case 7: monthRedux = "jul.";
			 		break;
    		case 8: monthRedux = "ago.";
			 		break;
    		case 9: monthRedux = "sep.";
			 		break;
    		case 10: monthRedux = "oct.";
			 		break;
    		case 11: monthRedux = "nov.";
			 		break;
    		case 12: monthRedux = "dic.";
			 		break;
    	}
    	
    	this.setText(	new StringBuilder() // Month is 0 based so add 1
            			.append(pad(mDay)).append("/")
            			.append(monthRedux).append("/")
            			.append(mYear).append(" "));
    }

    //if single digit append "0" to the number
    private static String pad(int c) {
    	if (c >= 10)
    		return String.valueOf(c);
    	else
    		return "0" + String.valueOf(c);
    }
}