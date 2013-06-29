package com.example.DiaSlate;


import com.example.DiaSlate.Point;
import com.example.DiaSlate.Shape;

public class Circle extends Shape {
	
	private float centerX;
	private float centerY;
	private float radius;
	private Point oneEndofTheCircle;
	public postext area;
	//private Point anotherEndOfTheCircle;
	
	public Circle(){
		super();
		centerX = 0;
		centerY = 0;
		radius = 0;
		oneEndofTheCircle = new Point();
	}
	
	public float getCenterX()
	{
		return centerX;
	}
	
	public float getCenterY()
	{
		return centerY;
	}
	
	public float getRadius()
	{
		return radius;
	}
	public void setCenterX(float x)
	{
		centerX = x;
	}
	public void setCenterY(float y)
	{
		centerY = y;
	}
	public void setReadius(float r)
	{
		radius = r;
	}
	public void setOneEndOfTheCircle(Point p)
	{
		oneEndofTheCircle = p;
	}
	public Point getOneEndOfTheCircle()
	{
		return oneEndofTheCircle;
	}
	

}
