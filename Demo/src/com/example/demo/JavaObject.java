package com.example.demo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class JavaObject {

	private ActivityInterface mActivityInterface;
	public JavaObject(ActivityInterface activityInterface)
	{
		this.mActivityInterface = activityInterface;
	}
	
	public void logMessageError()
	{
		Log.e("Message from JS", "Logged message!");
	}
	
	public void logCustomMessageError(String message)
	{
		if(message == null || message.equals("null")) message = "Message is empty! Using default: Hello World!";
		Log.e("Message from JS", message);
	}
	
	public int sum(int a, int b)
	{
		return a+b;
	}
	
	public int sumStr(String a, String b)
	{
		int iA = 0;
		int iB = 0;
		try
		{
			iA = Integer.parseInt(a);
			iB = Integer.parseInt(b);
		}
		catch(Exception ex)
		{
			Log.e("Sum from JS", "sumStr error.");
		}
		
		return iA+iB;
	}
	
	public String stringFormat(int a, String b)
	{
		return String.format("Copyright (C) %d %s", a, b);
	}
	
	public void showToast(String message)
	{
		mActivityInterface.showToast(message);
	}
}
