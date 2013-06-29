package com.example.DiaSlate;


import com.example.DiaSlate.*;
import com.example.DiaSlate.R;

import java.io.File;



import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

@SuppressWarnings("unused")
public class StartingActivity extends Activity implements View.OnClickListener, OnTouchListener{
	Button newImage;
	Button editImage;
	Button viewImage,helpbutton;
	Intent i;
	RelativeLayout rl1,il1,helpbuttonlayout;
	static boolean isToBeEdited = false;
	static boolean isToBeViewd = false;
	int mainbuttonsize,layoutsize;
	int screenWidth,screenHeight;
	int expl1,expl2;
    SoundPool sp1,sp2;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight=metrics.heightPixels;
        
        if(screenWidth>screenHeight)
        {
        mainbuttonsize=screenHeight*1/3;
        }
        else
        {
        	mainbuttonsize=screenWidth*1/3;
        }
        layoutsize=mainbuttonsize+10;
        sp1=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        expl1=sp1.load(getBaseContext(), R.raw.move, 1);
        sp2=new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        expl2=sp2.load(getBaseContext(), R.raw.click, 1);
        
        rl1 =new RelativeLayout(this);
        
        RelativeLayout.LayoutParams p1 =new RelativeLayout.LayoutParams(mainbuttonsize,mainbuttonsize);
        p1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        
        RelativeLayout.LayoutParams p2 =new RelativeLayout.LayoutParams(mainbuttonsize,mainbuttonsize);
        p2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        RelativeLayout.LayoutParams p3 =new RelativeLayout.LayoutParams(mainbuttonsize,mainbuttonsize);
        p3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        
        il1=new RelativeLayout(this);
        RelativeLayout.LayoutParams pt1 =new RelativeLayout.LayoutParams(3*layoutsize,layoutsize);
        pt1.addRule(RelativeLayout.CENTER_IN_PARENT);
        il1.setLayoutParams(pt1);
        
        helpbuttonlayout=new RelativeLayout(this);
        RelativeLayout.LayoutParams help =new RelativeLayout.LayoutParams((int)(mainbuttonsize/3),(int)(mainbuttonsize/3));
        help.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        helpbuttonlayout.setLayoutParams(help);
        
        newImage=new Button(this);
        newImage.setLayoutParams(p1);
        newImage.setId(1);
        newImage.setBackgroundResource(R.drawable.newimage);
        newImage.setOnTouchListener(this);
        
        viewImage=new Button(this);
        viewImage.setLayoutParams(p2);
        viewImage.setId(2);
        viewImage.setBackgroundResource(R.drawable.viewimage);
        viewImage.setOnTouchListener(this);
        
        editImage=new Button(this);
        editImage.setLayoutParams(p3);
        editImage.setId(3);
        editImage.setBackgroundResource(R.drawable.editimage);
        editImage.setOnTouchListener(this);
        
        
        helpbutton=new Button(this);
        RelativeLayout.LayoutParams p1h =new RelativeLayout.LayoutParams((int)(mainbuttonsize/3),(int)(mainbuttonsize/3));
        p1h.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
       helpbutton.setLayoutParams(p1h);
        helpbutton.setId(4);
        helpbutton.setBackgroundResource(R.drawable.help1);
        helpbutton.setOnTouchListener(this);
        
        helpbuttonlayout.addView(helpbutton);
        rl1.addView(helpbuttonlayout);
        il1.addView(newImage);
        il1.addView(editImage);
        il1.addView(viewImage);
        rl1.setBackgroundResource(R.drawable.uml_bg);
        rl1.addView(il1);
        setContentView(rl1);
        
        
        i = new Intent();
        
        
        i.setClassName("com.example.DiaSlate", "com.example.DiaSlate.MainActivity");
        
	}
	
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		 Intent iStartingActivity = new Intent("com.example.DiaSlate.main");
			startActivity(iStartingActivity);
	}*/

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0 == newImage){
		startActivity(i);
		isToBeEdited  = false;
		isToBeViewd = false;
		}
		
		if(arg0 == editImage){
			isToBeEdited = true;
			isToBeViewd = false;
			Intent iFileList = new Intent();
			iFileList.setClassName("com.example.DiaSlate","com.example.DiaSlate.FileList");
			startActivity(iFileList);
			//isToBeEdited = false;
		}
		
		if(arg0 == viewImage){
			isToBeEdited  = false;
			isToBeViewd = true;
			Intent iFileList = new Intent();
			iFileList.setClassName("com.example.DiaSlate","com.example.DiaSlate.FileList");
			startActivity(iFileList);
			//isToBeEdited = false;
		}
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		// TODO Auto-generated method stub
		if(v.getId() == 1){
			if(me.getAction() == MotionEvent.ACTION_DOWN)
	        {
	           newImage.setBackgroundResource(R.drawable.newimage2);
	        }
	        else if(me.getAction() == MotionEvent.ACTION_MOVE)
	        {
	            
	        }
	        else if(me.getAction() == MotionEvent.ACTION_UP)
	        {
	        	sp1.play(expl1, 1, 1, 0, 0,1);
	        	newImage.setBackgroundResource(R.drawable.newimage);
	        	startActivity(i);
				isToBeEdited  = false;
				isToBeViewd = false;
	            
	        }return true;
			
			}
			
			if(v.getId() == 3){
				if(me.getAction() == MotionEvent.ACTION_DOWN)
		        {
		           editImage.setBackgroundResource(R.drawable.editimage2);
		        }
		        else if(me.getAction() == MotionEvent.ACTION_MOVE)
		        {
		            
		        }
		        else if(me.getAction() == MotionEvent.ACTION_UP)
		        {
		        	sp1.play(expl1, 1, 1, 0, 0,1);
		        	editImage.setBackgroundResource(R.drawable.editimage);
		        	isToBeEdited = true;
					isToBeViewd = false;
					Intent iFileList = new Intent();
					iFileList.setClassName("com.example.DiaSlate","com.example.DiaSlate.FileList");
					startActivity(iFileList);
		            
		        }
				return true;
			}
			
			if(v.getId() == 2){
				if(me.getAction() == MotionEvent.ACTION_DOWN)
		        {
		           viewImage.setBackgroundResource(R.drawable.viewimage2);
		        }
		        else if(me.getAction() == MotionEvent.ACTION_MOVE)
		        {
		            
		        }
		        else if(me.getAction() == MotionEvent.ACTION_UP)
		        {
		        	sp1.play(expl1, 1, 1, 0, 0,1);
		        	viewImage.setBackgroundResource(R.drawable.viewimage);
		        	isToBeEdited  = false;
					isToBeViewd = true;
					Intent iFileList = new Intent();
					iFileList.setClassName("com.example.DiaSlate","com.example.DiaSlate.FileList");
					startActivity(iFileList);
					
		            
		        }
				return true;
				
				
			}
			if(v.getId() == 4){
				
				if(me.getAction() == MotionEvent.ACTION_DOWN)
		        {
		           helpbutton.setBackgroundResource(R.drawable.help1);
		        }
		        else if(me.getAction() == MotionEvent.ACTION_MOVE)
		        {
		            
		        }
		        else if(me.getAction() == MotionEvent.ACTION_UP)
		        {
		        	sp1.play(expl1, 1, 1, 0, 0,1);
		        	helpbutton.setBackgroundResource(R.drawable.help2);
		        	Intent j = new Intent();
					j.setClassName("com.example.DiaSlate","com.example.DiaSlate.Helpuml");
					startActivity(j);
		        	
					
		            
		        }
			}
		return false;
	}
	
}
