package com.example.DiaSlate;

import java.util.ArrayList;

import android.graphics.Path;

import com.example.DiaSlate.Shape;

public class FreeHand extends Shape{
	private ArrayList<Path>_graphics;
	
	public FreeHand(){
		super();
		 _graphics = new ArrayList<Path>();
	}
	
	public ArrayList<Path> getGraphicsPath(){
		return _graphics;
	}

}
