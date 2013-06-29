package com.example.DiaSlate;

import android.graphics.RectF;

public class Oval extends Shape {
	private RectF r;
	public postext area;
	private Point oneEndOfTheOval;
	public Oval(){
		super();
		r = new RectF();
		oneEndOfTheOval = new Point();
	}
	
	public RectF getRectangle(){
		return r;
	}
	
	public Point getoneEndOfTheOval(){
		return oneEndOfTheOval;
	}
	
	public void setoneEndOfTheOval(Point p){
		oneEndOfTheOval = p;
	}
}
