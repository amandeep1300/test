package com.example.DiaSlate;
	 
	import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
	 
	public class ExpandAnimation extends Animation implements Animation.AnimationListener {
	    private View view ,v2;
	    private static int ANIMATION_DURATION;
	    private int LastWidth;
	    private int FromWidth;
	    private int ToWidth;
	    private static int STEP_SIZE=30;
	    public ExpandAnimation(View v, int FromWidth, int ToWidth, double d) {
	         
	        this.view = v;
	       
	        ANIMATION_DURATION = 1;
	        this.FromWidth = FromWidth;
	        this.ToWidth = ToWidth;
	        setDuration(ANIMATION_DURATION);
	        setRepeatCount(10);
	        setFillAfter(false);
	        setInterpolator(new AccelerateInterpolator());
	        setAnimationListener(this);
	    }
		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			LayoutParams lyp =  view.getLayoutParams();
	        lyp.height = LastWidth +=ToWidth/20;
	        view.setLayoutParams(lyp);
	       
			
		}
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			LayoutParams lyp =  view.getLayoutParams();
	       // lyp.width = 0;
	        view.setLayoutParams(lyp);
	        LastWidth = 0;
			
		}
	 
	    
		
	}