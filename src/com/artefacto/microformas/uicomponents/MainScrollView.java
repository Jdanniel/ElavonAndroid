package com.artefacto.microformas.uicomponents;

import com.artefacto.microformas.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MainScrollView extends ScrollView {
	
	protected int scrollY = 0;

    public MainScrollView(Context context) {
        super(context);
    }

    public MainScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        MainActivity.scrollOffset = y;
    }
	
	public int getScrollPosition(){

		return scrollY;
	}
	
	public void onLayout(boolean a , int b, int c,  int d, int e){
		super.onLayout(a, b,c,d,e);
		this.scrollTo(0, MainActivity.scrollOffset);
	}

}
