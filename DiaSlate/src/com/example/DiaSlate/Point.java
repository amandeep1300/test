package com.example.DiaSlate;

import com.example.DiaSlate.*;



@SuppressWarnings("unused")
public class Point {
	
	private float _x ;
    private float _y;

    public Point(){
    	
    }
    public Point(float x, float y){
 	   _x = x;
 	   _y = y;
    }
    public float getX() {
        return _x;
    }

    public void setX(float value) {
        _x = value;
    }

    public float getY() {
        return _y;
    }

    public void setY(float value) {
        _y = value;
    }

    @Override
	public String toString() {
        return "Coordinates: (" + _x + "/" + _y + ")";
    }

}
