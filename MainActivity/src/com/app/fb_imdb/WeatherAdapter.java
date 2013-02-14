package com.app.fb_imdb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherAdapter extends ArrayAdapter<Weather>{
	 
Context context;
int layoutResourceId;
Weather data[] = null;
 

	public WeatherAdapter(Context context, int layoutResourceId, Weather[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
 
@Override
public View getView(int position, View convertView, ViewGroup parent) {
	View row = convertView;
	WeatherHolder holder = null;
	 
	if(row == null)
	{
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		 
		holder = new WeatherHolder();
		holder.imgIcon = (ImageView)row.findViewById(R.id.imageView1);
		holder.txtTitle = (TextView)row.findViewById(R.id.txt1);
		holder.txtRating=(TextView)row.findViewById(R.id.rtxt1);
		 
		row.setTag(holder);
	}
	else
	{
		holder = (WeatherHolder)row.getTag();
	}
	 
	Weather weather = data[position];
	holder.txtTitle.setText(weather.wtitle);
	//holder.imgIcon.setImageResource(weather.wicon);
	holder.imgIcon.setImageDrawable(weather.wicon);
	holder.txtRating.setText("Rating: "+weather.wrating); 
	return row;
} 

	static class WeatherHolder
	{
	//Bitmap imgIcon;
	ImageView imgIcon;
	TextView txtTitle;
	TextView txtRating;
	}
}