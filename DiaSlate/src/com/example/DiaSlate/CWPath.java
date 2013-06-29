/*
 * This class is used to store a path, its color and width and wheter it is drawn in draw mode
 * or eraser mode and also eraser's width at that time.
 * 
 */


package com.example.DiaSlate;

import android.graphics.Path;

public class CWPath 
{
	//Fields
	private Path path; //path
	private int pathColor; //path color
	private int pathWidth; // path width
	private int eraserWidth; // eraser width
	//whether this path is drawn in drawing mode or eraser mode
	//if it is drawn in draw mode then drawMode = true, if not then drawMode = false
	//if path is drawn in draw mode its color is pathColor else background color
	private boolean drawMode; 
	
	//Constructors
	public CWPath(Path p, int c, int pw, int ew, boolean d)
	{
		path = p;
		pathColor = c;
		pathWidth = pw;
		eraserWidth = ew;
		drawMode = d;
	}
	
	//Setters and Getters
	public Path getPath()
	{
		return path;
	}
	public int getPathColor()
	{
		return pathColor;
	}
	public int getPathWidth()
	{
		return pathWidth;
	}
	public int getEraserWidth()
	{
		return eraserWidth;
	}
	public boolean getDrawMode()
	{
		return drawMode;
	}
	
	public void setPath(Path p)
	{
		path = p;
	}
	public void setPathColor(int c)
	{
		pathColor = c;
	}
	public void setPathWidth(int w)
	{
		pathWidth = w;
	}
	public void setEraserWidth(int w)
	{
		eraserWidth = w;
	}
	public void setdrawMode(boolean d)
	{
		drawMode = d;
	}
}

