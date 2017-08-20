package com.cv.jishiqi;

import java.text.SimpleDateFormat;
import java.util.Date;


public class JiShiQi
{
	private long time;
	public JiShiQi(long time){
		this.time = time;
	}
	public void setTime(long time)
	{
		this.time = time;
	}
	public long getTime()
	{
		return time;
	}
	public String jishi(boolean b){
		if(b)
			time -= 1000;
		if(time > 0){
			SimpleDateFormat fmt = new SimpleDateFormat("mm:ss");
	        return fmt.format(new Date(time-8*60*60*1000));
		}
		return "00:00";
	}
}
