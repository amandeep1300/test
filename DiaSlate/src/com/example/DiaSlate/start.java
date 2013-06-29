package com.example.DiaSlate;

import com.example.DiaSlate.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class start extends Activity {
RelativeLayout main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		main=new RelativeLayout(this);
		main.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
		main.setBackgroundResource(R.drawable.menu_bg);
		setContentView(main);
		Intent i=new Intent("com.example.DiaSlate.Main");
		startActivity(i);
		
	}
	

}
