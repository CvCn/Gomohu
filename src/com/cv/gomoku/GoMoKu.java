package com.cv.gomoku;

import java.awt.Point;

public class GoMoKu implements GoMoKuInter
{
	Point p;
	public GoMoKu(Point p){
		this.p = p;
	}
	public Point getP()
	{
		return p;
	}
	public void setP(Point p)
	{
		this.p = p;
	}
	@Override
	public boolean equals(Object obj)
	{
		GoMoKu gomoku = (GoMoKu)obj;
		return this.p.equals(gomoku.getP());
	}
	@Override
	public int hashCode()
	{
		return this.p.hashCode();
	}
	

	
}
