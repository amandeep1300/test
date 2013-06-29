package com.example.DiaSlate;

import android.widget.ImageView;
import android.widget.TextView;

public class postext  {

	public Point p1,p2;
	public Boolean done;
	float top,left,bottom,right;
	public TextView thistext;
	int textno;
	int obj;//0-line,1-circle,2-oval,3-rectangle
	public Shape graphicInThis;
	public ImageView layoutinthis;

	
	/*protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Point p2=new Point(0,0);
	
		done=false;
		obj=-1;
		thistext=new TextView(this);
		
		
		
	}*/
	public postext()
	{
		Point p2=new Point(0,0);
		
		done=false;
		obj=-1;
		//layoutinthis=new RelativeLayout(this);
		
	}
	public void editmytext(String str)
	{
		
		thistext.setText(str);
	}
	public Boolean checkarea(Point in)
	{
		if(p1.getX()<p2.getX() && p1.getY()>p2.getY())
		{
			
			if(in.getX()>p1.getX()&&in.getY()>p2.getY()&&in.getX()<p2.getX()&&in.getY()<p1.getY())
			{
				left=p1.getX();
				right=p2.getX();
				top=p2.getY();
				bottom=p1.getY();
				return false;
			}
		}
		if(p1.getX()<p2.getX() && p1.getY()<p2.getY())
		{
			
			if(in.getX()>p1.getX()&&in.getY()>p1.getY()&&in.getX()<p2.getX()&&in.getY()<p2.getY())
			{
				left=p1.getX();
				right=p2.getX();
				top=p1.getY();
				bottom=p2.getY();
				return false;
			}
		}
		if(p1.getX()>p2.getX()  && p1.getY()>p2.getY())
		{
			
			if(in.getX()>p2.getX()&&in.getY()<p2.getY()&&in.getX()<p1.getX()&&in.getY()>p1.getY())
			{
				left=p2.getX();
				right=p1.getX();
				top=p1.getY();
				bottom=p2.getY();
				return false;
			}
		}
		if(p1.getX()>p2.getX() && p1.getY()<p2.getY())
		{
			
			if(in.getX()>p2.getX()&&in.getY()>p1.getY()&&in.getX()<p1.getX()&&in.getY()<p2.getY())
			{
				left=p2.getX();
				right=p1.getX();
				top=p1.getY();
				bottom=p2.getY();
				return false;
			}
		}
		return true;
	}
	
	
	
}
