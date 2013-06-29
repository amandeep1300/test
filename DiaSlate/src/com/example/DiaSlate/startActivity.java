package com.example.DiaSlate;

import com.example.DiaSlate.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class startActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			@Override
			public void run(){
				try{
					sleep(3000); /* screen is displayed for 3 secs */
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					startActivity(new Intent(startActivity.this,Main.class));
 /* Replace Menu.class with our Activity containing both slate and uml as options */
				}
			}
		};
		timer.start();
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	}


