package com.example.DiaSlate;

import com.example.DiaSlate.R;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class Main extends Activity implements OnTouchListener 
{
	Button buml, bslate,help,exit;
	FrameLayout main;
	int screenWidth,screenHeight;
    int menubuttonsize,dividinglayoutsize,leftlayoutsize,leftbuttonsize;
    RelativeLayout dividing,dividingleft,dividingright,left,lefttop,leftright,insidemain,bringItDown,backgroundSplash;
    int expl1,expl2;
    SoundPool sp1,sp2;
   
    Boolean change;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        screenWidth = metrics.widthPixels;
	        screenHeight=metrics.heightPixels;
	        menubuttonsize=219;
	        leftbuttonsize=55;
	        
	        change=false;
	        sp1=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
	        expl1=sp1.load(getBaseContext(), R.raw.move, 1);
	        RelativeLayout.LayoutParams p1 =new RelativeLayout.LayoutParams(leftbuttonsize,leftbuttonsize);
	        p1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        
	        RelativeLayout.LayoutParams p2 =new RelativeLayout.LayoutParams(leftbuttonsize,leftbuttonsize);
	        p2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	        
	        RelativeLayout.LayoutParams p3 =new RelativeLayout.LayoutParams(menubuttonsize,menubuttonsize);
	        p3.addRule(RelativeLayout.CENTER_VERTICAL);
	        
	        RelativeLayout.LayoutParams p4 =new RelativeLayout.LayoutParams(menubuttonsize,menubuttonsize);
	        p4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        
	        RelativeLayout.LayoutParams p5 =new RelativeLayout.LayoutParams(menubuttonsize,menubuttonsize);
	        p5.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	        
	        RelativeLayout.LayoutParams p6 =new RelativeLayout.LayoutParams(75,75);
	        p6.addRule(RelativeLayout.CENTER_HORIZONTAL);
	        
	        RelativeLayout.LayoutParams pt11 =new RelativeLayout.LayoutParams((int)(1.5*menubuttonsize),(int)(1.5*menubuttonsize));
	        pt11.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
	        
	        
	        RelativeLayout.LayoutParams pt33 =new RelativeLayout.LayoutParams((int)(1.5*menubuttonsize),(int)(1.5*menubuttonsize));
	        pt33.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		main=new FrameLayout(this);
		main.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		main.setBackgroundResource(R.drawable.menu_bg);
		insidemain=new RelativeLayout(this);
		insidemain.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		backgroundSplash=new RelativeLayout(this);
		backgroundSplash.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		backgroundSplash.setBackgroundResource(R.drawable.menu_bg);
		setContentView(backgroundSplash);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dividing=new RelativeLayout(this);
		RelativeLayout.LayoutParams pt22 =new RelativeLayout.LayoutParams((int)(2.5*menubuttonsize),LayoutParams.FILL_PARENT);
        pt22.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dividing.setLayoutParams(pt22);
        
        bringItDown=new RelativeLayout(this);
        RelativeLayout.LayoutParams pt222 =new RelativeLayout.LayoutParams((int)(2.5*menubuttonsize),1*menubuttonsize);
        pt222.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bringItDown.setLayoutParams(pt222);
        
        dividingleft=new RelativeLayout(this);
        dividingleft.setLayoutParams(pt11);
        
        dividingright=new RelativeLayout(this);
        dividingright.setLayoutParams(pt33);
        
        left=new RelativeLayout(this);
        RelativeLayout.LayoutParams left1 =new RelativeLayout.LayoutParams(leftbuttonsize,(int)(2.5*leftbuttonsize));
        left1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        left.setLayoutParams(left1);
        
		buml=new Button(this);
		buml.setBackgroundResource(R.drawable.uml);
		buml.setId(2);
		buml.setLayoutParams(p5);
		buml.setOnTouchListener(this);
		
		bslate=new Button(this);
		bslate.setBackgroundResource(R.drawable.slate);
		bslate.setId(1);
		bslate.setLayoutParams(p4);
		bslate.setOnTouchListener(this);
		
		exit=new Button(this);
		exit.setBackgroundResource(R.drawable.exitmain);
		exit.setId(3);
		exit.setLayoutParams(p1);
		exit.setOnTouchListener(this);
		
		help=new Button(this);
		help.setBackgroundResource(R.drawable.i);
		help.setId(4);
		help.setLayoutParams(p2);
		help.setOnTouchListener(this);
		
		dividingleft.addView(bslate);
		dividingright.addView(buml);
		left.addView(exit);
		left.addView(help);
		bringItDown.addView(dividingright);
		bringItDown.addView(dividingleft);
		dividing.addView(bringItDown);
		insidemain.addView(dividing);
		insidemain.addView(left);
		main.addView(insidemain);
		setContentView(main);
		
		
		
		
	}
 
 
 
	



	@Override
	public boolean onTouch(View v, MotionEvent me) {
		switch(v.getId())
		{
			case 1:
			{
				if(me.getAction() == MotionEvent.ACTION_DOWN)
    	        {
    	           bslate.setBackgroundResource(R.drawable.slate2);
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_MOVE)
    	        {
    	            
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_UP)
    	        {
    	        	sp1.play(expl1, 1, 1, 0, 0,1);
    	        	bslate.setBackgroundResource(R.drawable.slate);
    	        	Intent j = new Intent("com.example.DiaSlate.SLATE");
    				startActivity(j);
    	        }	
				
			}
			break;
			case 2:
			{
				if(me.getAction() == MotionEvent.ACTION_DOWN)
    	        {
    	           buml.setBackgroundResource(R.drawable.uml2);
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_MOVE)
    	        {
    	            
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_UP)
    	        {
    	        	sp1.play(expl1, 1, 1, 0, 0,1);
    	        	buml.setBackgroundResource(R.drawable.uml);
    	        	Intent i = new Intent("com.example.DiaSlate.StartingActivity");
    				startActivity(i);
    	        }	
				
			}
				break;
			case 3:
			{
				if(me.getAction() == MotionEvent.ACTION_DOWN)
    	        {
    	           exit.setBackgroundResource(R.drawable.exitmain2);
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_MOVE)
    	        {
    	            
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_UP)
    	        {
    	        	sp1.play(expl1, 1, 1, 0, 0,1);
    	        	exit.setBackgroundResource(R.drawable.exitmain);
    	        	finish();
    	        	
    	        }	
				
			}
				break;
			case 4:
			{
				if(me.getAction() == MotionEvent.ACTION_DOWN)
    	        {
    	           help.setBackgroundResource(R.drawable.i);
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_MOVE)
    	        {
    	            
    	        }
    	        else if(me.getAction() == MotionEvent.ACTION_UP)
    	        {
    	        	sp1.play(expl1, 1, 1, 0, 0,1);
    	        	help.setBackgroundResource(R.drawable.i2);
    	        	Intent k = new Intent("com.example.DiaSlate.AboutUs");
    				startActivity(k);
    	        	
    	        }	
				
			}
				break;
			
		}
		// TODO Auto-generated method stub
		return false;
	}
	
}
