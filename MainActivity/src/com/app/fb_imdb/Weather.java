package com.app.fb_imdb;

import java.io.InputStream;
import java.net.URL;


import android.graphics.drawable.Drawable;


public class Weather {
	
	public Drawable wicon;	
	public String wtitle;
	public String wrating;
	public Weather(){
	super();
	}

	public Weather(String icon, String title,String rating) {
		super();
		//this.wicon=LoadImageFromWeb(icon);
		
		this.wicon=LoadImageFromWebOperations(icon);
		this.wtitle = title;
		this.wrating=rating;
	}
	

	public static Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	        return null;
	    }
	}
	
}