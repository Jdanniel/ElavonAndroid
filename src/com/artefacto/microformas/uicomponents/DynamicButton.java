package com.artefacto.microformas.uicomponents;

import com.artefacto.microformas.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;

public class DynamicButton extends AppCompatButton{
	Paint buttonPaint		= null;
	Paint backgroundPaint 	= null;
	Paint linearPaint 		= null;
	Paint titlePaint 		= null;
	Paint textPaint 		= null;
	Paint requestPaint 		= null;
	Paint newRequestPaint 	= null;
	Paint circlePaint		= null;
	Paint circle2Paint		= null;
	Paint numberPaint		= null;
	
	String[] 	notificationsString = null;
	String[] 	servicioString = null;
	int[] 		light = null;
	int 		notificationsNumber = 1;
	String 		title				= "NUEVAS";
	boolean 	lightOn = true;
	
	public DynamicButton(Context context){
		super(context);
	}
	
	
	public DynamicButton(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public DynamicButton(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	
	public void onDraw(Canvas canvas){
		int buttonWidth		= this.getWidth();
		//int buttonHeight 	= this.getHeight();
		
		//Valores para las líneas de las notificaciones, dependiendo la cantidad a desplegar
		int[] HORIZONTAL_LINE_UP 	= new int[notificationsNumber];
		int[] HORIZONTAL_LINE_DOWN 	= new int[notificationsNumber];
		int[] TEXT_POSITION_HEIGHT	= new int[notificationsNumber];
		
		//Valores para las líneas horizontales
		int[] INITIAL_HEIGHT 	= new int[notificationsNumber];	//Y Inicial
		int[] FIXED_HEIGHT 		= new int[notificationsNumber]; //Y Final
		
		int NODE_TOP_LEFT;
		int NODE_TOP_RIGHT;
		int NODE_BOTTOM_LEFT;
		int NODE_BOTTOM_RIGHT;
		
		int razonDeCambio;
		int textDifference;
		int textSize;
		int LIGHT_POSITION;
		int SECOND_LINE_FIX;
		int TITLE_FIX;
		
		int titleSize;
		int numberSize;
		
		int circleRadius;
		int circleX;
		int circleY;
		int numberX;
		int numberY;
		
		int INITIAL_WIDTH; 	//X Inicial
		int FIXED_WIDTH;

		//Para equipos en HD
		if(buttonWidth > 170){
			//Valores para balancear el marco y otras referencias
			NODE_TOP_LEFT 	= 5;
			NODE_TOP_RIGHT 	= 0;
			NODE_BOTTOM_LEFT 	= this.getWidth() - 5;
			NODE_BOTTOM_RIGHT = this.getHeight() - 8;
			
			razonDeCambio 	= 30;
			textDifference 	= 18;
			textSize		= 12;
			LIGHT_POSITION	= 187;
			SECOND_LINE_FIX = 14;
			TITLE_FIX		= 20;
			
			titleSize = 21;
			numberSize = 22;
			
			//Datos del círculo
			circleRadius 	= 20;
			circleX 		= this.getWidth() - 29;//old position this.getWidth()
			circleY 		= this.getHeight() - circleRadius - 13;//old position 22
			numberX 		= this.getWidth() - 36;
			numberY 		= this.getHeight() - circleRadius - 6;//old position 29
			
			
			switch(notificationsNumber){
			case 0: break;
			case 1:	HORIZONTAL_LINE_UP[0]   = razonDeCambio + 18;//old position + 45
					HORIZONTAL_LINE_DOWN[0] = razonDeCambio + 50;//old position + 75
					INITIAL_HEIGHT[0] 		= 0;
					FIXED_HEIGHT[0] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[0]	= HORIZONTAL_LINE_DOWN[0] - textDifference;
					break;
			case 2: HORIZONTAL_LINE_UP[0]   = razonDeCambio + 15;//old position + 30
					HORIZONTAL_LINE_DOWN[0] = razonDeCambio + 45;//old position + 60
					INITIAL_HEIGHT[0] 		= 0;
					FIXED_HEIGHT[0] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[0]	= HORIZONTAL_LINE_DOWN[0] - textDifference;
					
					HORIZONTAL_LINE_UP[1]   = razonDeCambio + 46;//old position + 61
					HORIZONTAL_LINE_DOWN[1] = razonDeCambio + 76;//old position + 91
					INITIAL_HEIGHT[1] 		= 0;
					FIXED_HEIGHT[1] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[1]	= HORIZONTAL_LINE_DOWN[1] - textDifference;
					break;
			default:HORIZONTAL_LINE_UP[0]   = razonDeCambio - 10; //+ 10;//old position + 20
					HORIZONTAL_LINE_DOWN[0] = razonDeCambio - 25;//old position + 50
					INITIAL_HEIGHT[0] 		= 0;
					FIXED_HEIGHT[0] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[0]	= razonDeCambio + 25/*HORIZONTAL_LINE_DOWN[0]*/ - textDifference;
					
					HORIZONTAL_LINE_UP[1]   = razonDeCambio + 30;//old position + 51
					HORIZONTAL_LINE_DOWN[1] = razonDeCambio + 65;//old position + 81
					INITIAL_HEIGHT[1] 		= 0;
					FIXED_HEIGHT[1] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[1]	= HORIZONTAL_LINE_DOWN[1] - textDifference;
					
					HORIZONTAL_LINE_UP[2]   = razonDeCambio + 82;//old position + 82
					HORIZONTAL_LINE_DOWN[2] = razonDeCambio + 112;//old position + 112
					INITIAL_HEIGHT[2] 		= 0;
					FIXED_HEIGHT[2] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[2]	= HORIZONTAL_LINE_DOWN[2] - textDifference;
					break;
			}
			INITIAL_WIDTH = 5; 	//X Inicial
			FIXED_WIDTH= this.getWidth()  - 6;
		}
		else{
			//Para equipos de pantalla promedio
			//Valores para balancear el marco y otras referencias
			NODE_TOP_LEFT 	= 5;
			NODE_TOP_RIGHT 	= 0;
			NODE_BOTTOM_LEFT 	= this.getWidth() - 5;
			NODE_BOTTOM_RIGHT = this.getHeight() - 8;
			
			razonDeCambio 	= 25;//old position 30
			textDifference 	= 12;
			textSize		= 8;
			LIGHT_POSITION	= 125;
			SECOND_LINE_FIX = 8;
			TITLE_FIX		= 11;
			
			titleSize = 12;
			numberSize = 15;
			
			//Datos del círculo
			circleRadius 	= 12;
			circleX 		= this.getWidth() - 24;
			circleY 		= 14;
			numberX 		= this.getWidth() - 28;
			numberY 		= 19;
			
			switch(notificationsNumber){
			case 0: break;
			case 1:	HORIZONTAL_LINE_UP[0]   = razonDeCambio + 20;
					HORIZONTAL_LINE_DOWN[0] = razonDeCambio + 42;
					INITIAL_HEIGHT[0] 		= 0;
					FIXED_HEIGHT[0] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[0]	= HORIZONTAL_LINE_DOWN[0] - textDifference;
					break;
			case 2: HORIZONTAL_LINE_UP[0]   = razonDeCambio + 10;
					HORIZONTAL_LINE_DOWN[0] = razonDeCambio + 32;
					INITIAL_HEIGHT[0] 		= 0;
					FIXED_HEIGHT[0] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[0]	= HORIZONTAL_LINE_DOWN[0] - textDifference;
					
					HORIZONTAL_LINE_UP[1]   = razonDeCambio + 32;
					HORIZONTAL_LINE_DOWN[1] = razonDeCambio + 54;
					INITIAL_HEIGHT[1] 		= 0;
					FIXED_HEIGHT[1] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[1]	= HORIZONTAL_LINE_DOWN[1] - textDifference;
					break;
			default:HORIZONTAL_LINE_UP[0]   = razonDeCambio - 2;
					HORIZONTAL_LINE_DOWN[0] = razonDeCambio + 20;
					INITIAL_HEIGHT[0] 		= 0;
					FIXED_HEIGHT[0] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[0]	= HORIZONTAL_LINE_DOWN[0] - textDifference;
					
					HORIZONTAL_LINE_UP[1]   = razonDeCambio + 20;
					HORIZONTAL_LINE_DOWN[1] = razonDeCambio + 42;
					INITIAL_HEIGHT[1] 		= 0;
					FIXED_HEIGHT[1] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[1]	= HORIZONTAL_LINE_DOWN[1] - textDifference;
					
					HORIZONTAL_LINE_UP[2]   = razonDeCambio + 42;
					HORIZONTAL_LINE_DOWN[2] = razonDeCambio + 64;
					INITIAL_HEIGHT[2] 		= 0;
					FIXED_HEIGHT[2] 		= this.getHeight() - 8;
					TEXT_POSITION_HEIGHT[2]	= HORIZONTAL_LINE_DOWN[2] - textDifference;
					break;
			}
			INITIAL_WIDTH = 5; 	//X Inicial
			FIXED_WIDTH= this.getWidth()  -5;
		}
		
		super.onDraw(canvas);
		
		//Color del botón
		buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		buttonPaint.setColor(Color.rgb(255, 197, 127));
				
		//Color de fondo
		backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		backgroundPaint.setColor(Color.WHITE);
		
		//Color del delineado del botón
		linearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linearPaint.setColor(Color.rgb(255, 197, 127));
		linearPaint.setAntiAlias(true);
		
		//Color para el título
		titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		titlePaint.setColor(Color.WHITE);
		titlePaint.setStyle(Style.FILL);
		titlePaint.setTextSize(titleSize);
		titlePaint.setFakeBoldText(true);
		titlePaint.setAntiAlias(true);
		
		//Color para el círculo de cantidad de solicitudes
		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(Color.RED);
		circle2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circle2Paint.setColor(Color.WHITE);
		
		//Color del número de solicitudes
		numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		numberPaint.setColor(Color.rgb(255, 140, 0));
		numberPaint.setTextSize(numberSize);
		
		//Color para los textos
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.BLACK);
		textPaint.setStyle(Style.FILL);
		textPaint.setTextSize(textSize);
		titlePaint.setFakeBoldText(true);
		textPaint.setAntiAlias(true);
		
		//Color de fondo para las solicitudes
		requestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		requestPaint.setColor(Color.GRAY);
		
		//Color de fondo para las nuevas solicitudes
		newRequestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		newRequestPaint.setColor(Color.YELLOW);
		
		//Dibuja el botón
		canvas.drawRect(NODE_TOP_LEFT, NODE_TOP_RIGHT, NODE_BOTTOM_LEFT, NODE_BOTTOM_RIGHT, buttonPaint);
		
		//Dibuja el fondo
		int notificationCycles = 0;
		if(notificationsNumber > 2)
			notificationCycles = 2;
		else
			notificationCycles = notificationsNumber;
		
		//Dibuja el círuclo de notificaciones
		if(notificationsNumber > 0){
			//canvas.drawCircle(circleX, circleY, circleRadius, numberPaint);
			//canvas.drawCircle(circleX, circleY, circleRadius - 1, circlePaint);
			//canvas.drawCircle(circleX, circleY, circleRadius - 2, numberPaint);
			canvas.drawCircle(circleX, circleY, circleRadius - 3, circle2Paint);
			
			if(notificationsNumber < 10)
				canvas.drawText(String.valueOf(notificationsNumber), numberX, numberY, numberPaint);
			else
				canvas.drawText(String.valueOf(notificationsNumber), numberX -3, numberY, numberPaint);
		}	
				
		for(int i = 0; i < notificationCycles; i++){
			//Dibuja el fondo
			canvas.drawRect(NODE_TOP_LEFT,
							HORIZONTAL_LINE_UP[0], 
							NODE_BOTTOM_LEFT, 
							HORIZONTAL_LINE_DOWN[i], 
							backgroundPaint);
		}
		
		for(int i = 0; i < notificationCycles; i++){
			//Dibuja el texto
			
			if(i == 0){
				if (notificationsString[i]!=null){
					canvas.drawText(notificationsString[i],
									8,
									TEXT_POSITION_HEIGHT[i],
									textPaint);
				}
				if (servicioString[i]!=null){
					canvas.drawText(servicioString[i],
									8,
									TEXT_POSITION_HEIGHT[i] + SECOND_LINE_FIX,
									textPaint);
				}
				Bitmap bitmap;
				
				if (lightOn){
					if(light[i] > 2)
						 bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_button);
					else if(light[i] > 0)
						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_button);
					else
						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_button);

                    LIGHT_POSITION = this.getWidth() - bitmap.getWidth() - 13;
					canvas.drawBitmap(bitmap, LIGHT_POSITION,
                            TEXT_POSITION_HEIGHT[i] - 10
                            //HORIZONTAL_LINE_UP[i]// bitmap.getHeight()
                            /*bitmap.getHeight()*//*5*/,
                            textPaint);
				}
			}
			
			if(i == 1){
				if (notificationsString[1]!=null){
					canvas.drawText(notificationsString[1],
									8,
									TEXT_POSITION_HEIGHT[i],
									textPaint);
				}
				if (servicioString[1]!=null){
					canvas.drawText(servicioString[1],
									8,
									TEXT_POSITION_HEIGHT[i] + SECOND_LINE_FIX,
									textPaint);
				}
				
				Bitmap bitmap;

				if (lightOn){
					if(light[i] > 2)
						 bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_button);
					else if(light[i] > 0)
						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_button);
					else
						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_button);
					
					canvas.drawBitmap(bitmap, LIGHT_POSITION, TEXT_POSITION_HEIGHT[i] - 10, textPaint);
				}
			}
			
//			if(i == 2){
//				if (notificationsString[1]!=null)
//				{
//					canvas.drawText(notificationsString[1],
//									8,
//									TEXT_POSITION_HEIGHT[i],
//									textPaint);
//				}
//				if (servicioString[1]!=null){
//					canvas.drawText(servicioString[1],
//									8,
//									TEXT_POSITION_HEIGHT[i] + SECOND_LINE_FIX,
//									textPaint);
//				}
//
//				Bitmap bitmap;
//
//
//				if (lightOn){
//					if(light[i] > 2)
//						 bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green_button);
//					else if(light[i] > 0)
//						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_button);
//					else
//						bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red_button);
//
//					canvas.drawBitmap(bitmap, LIGHT_POSITION, TEXT_POSITION_HEIGHT[i] - 5, textPaint);
//				}
//			}
			
			//Delineado Horizontal
			canvas.drawLine(INITIAL_WIDTH, 
							INITIAL_HEIGHT[i], 
							FIXED_WIDTH, 
							INITIAL_HEIGHT[i], 
							linearPaint);
			canvas.drawLine(INITIAL_WIDTH, 
							FIXED_HEIGHT[i], 
							FIXED_WIDTH, 
							FIXED_HEIGHT[i], 
							linearPaint);
		
			canvas.drawLine(INITIAL_WIDTH, 
							HORIZONTAL_LINE_UP[i], 
							FIXED_WIDTH, 
							HORIZONTAL_LINE_UP[i], 
							linearPaint);
			canvas.drawLine(INITIAL_WIDTH, 
							HORIZONTAL_LINE_DOWN[i], 
							FIXED_WIDTH, 
							HORIZONTAL_LINE_DOWN[i], 
							linearPaint);
		
			//Delineado vertical
			/*canvas.drawLine(INITIAL_WIDTH, 
							INITIAL_HEIGHT[i], 
							INITIAL_WIDTH, 
							FIXED_HEIGHT[i], 
							linearPaint);
			canvas.drawLine(FIXED_WIDTH, 
							INITIAL_HEIGHT[i], 
							FIXED_WIDTH, 
							FIXED_HEIGHT[i], 
							linearPaint);*/
		}
		
		//Dibuja el título
		canvas.drawText(title,
						15,
						this.getHeight() - TITLE_FIX,
						titlePaint);
	}		
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setNotificationsString(String[] notificationsString, String[] servicioString, int[] light){
		for(int i = 0; i < 3; i++){
			try{
				if(notificationsString[i].length() > 32 )
					notificationsString[i] = notificationsString[i].substring(0, 30) + "...";
			}
			catch(Exception e){}
			try{
				if(servicioString[i].length() > 32 )
					servicioString[i] = servicioString[i].substring(0, 30) + "...";
			}
			catch(Exception e){}
		}
		
		
		this.notificationsString = notificationsString;
		this.servicioString		 = servicioString;
		this.light				 = light;
	}
	
	public String[] getNotificationsString(){
		return notificationsString;
	}
	
	public String[] getServicioString(){
		return servicioString;
	}
	
	public int[] getLight(){
		return light;
	}
	
	public void setNotificationsNumber(int notificationsNumber){
		this.notificationsNumber = notificationsNumber;
	}
	
	public int getNotificationsNumber(){
		return notificationsNumber;
	}

	public boolean isLightOn() {
		return lightOn;
	}

	public void setLightOn(boolean lightOn) {
		this.lightOn = lightOn;
	}
}