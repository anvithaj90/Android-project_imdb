package com.app.fb_imdb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Activity2 extends Activity {

	ABC previous_data = ABC.getSingletonObject();
	String[][] full_data = previous_data.getString();
	
	public Facebook fb=new Facebook("430731513660173");
	private ListView listView1;
	public  Button postbutton;
	public Bundle params=new Bundle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		  Intent intent = getIntent();
		  int temp_len = intent.getIntExtra("extra", 0);
		
     	
		//second one is always the type of data...
		Weather weather_data[] = new Weather[temp_len];
		int i;
			for(i=0;i<temp_len;i++)
			{
			weather_data[i]= new Weather(full_data[i][0],full_data[i][1],full_data[i][3]);
			}
		

		
		     	WeatherAdapter adapter = new WeatherAdapter(this,R.layout.listview_header_row, weather_data);
				listView1 = (ListView)findViewById(R.id.listView1);
			//	View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
			//	listView1.addHeaderView(header);
				listView1.setAdapter(adapter);	
				listView1.setClickable(true);
				listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				  @Override
				  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					  /* DISPLAYNG IT INA DIALOG BOX */
					    Dialog  dialog = new Dialog (Activity2.this);
		                dialog.setContentView(R.layout.dialogxml);
		                dialog.setCancelable(true);
		                ImageView dcover=(ImageView) dialog.findViewById(R.id.dimageView1);
		                TextView dtitle = (TextView) dialog.findViewById(R.id.dtextView1);
		                TextView dyear = (TextView) dialog.findViewById(R.id.dtextView2);
		                TextView ddirector = (TextView) dialog.findViewById(R.id.dtextView3);
		                TextView drating = (TextView) dialog.findViewById(R.id.dtextView4);
		                TextView len1 = (TextView) dialog.findViewById(R.id.textView77);
		               postbutton=(Button) dialog.findViewById(R.id.buttonfb); //creating a button inside a dialog box
		                
		                
		                InputStream is;
						try {
							is = (InputStream) new URL(full_data[position][0]).getContent();
							Drawable dd = Drawable.createFromStream(is, "src name");
				            dcover.setImageDrawable(dd);
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    	       
		                
		                dtitle.setText("Name: "+full_data[position][1]);
		                dyear.setText("Year: "+full_data[position][2]);
		                ddirector.setText("Director: "+full_data[position][4]);
		                drating.setText("Rating: "+full_data[position][3]+"/10");
		                dialog.show();
		             // clickdialogbutt();
		                
		                
		                params.putString("picture",full_data[position][0]);
		                params.putString("name", full_data[position][1]);		                
		                params.putString("caption", "I am interested in this movie/series/game");
		                params.putString("link", full_data[position][5]);
		                params.putString("description", full_data[position][1]+"has a rating of "+full_data[position][3]);
		                
		                
		                
		                
		                
		                
		                postbutton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
						//		Toast.makeText(Activity2.this, "Link:", Toast.LENGTH_SHORT).show();
								
								if(fb.isSessionValid())
								{//button close our session....
									
									try {
										fb.logout(getApplicationContext());
										
										
										
									} catch (MalformedURLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
								else
								{
									fb.authorize(Activity2.this,new String[] {"email"}, new DialogListener() {
										
										@Override
										public void onFacebookError(FacebookError e) {
											// TODO Auto-generated method stub
											Toast.makeText(Activity2.this, "FB Error", Toast.LENGTH_SHORT).show();
										}
										
										@Override
										public void onError(DialogError e) {
											// TODO Auto-generated method stub
											Toast.makeText(Activity2.this, "On Error", Toast.LENGTH_SHORT).show();
										}
										
										@Override
										public void onComplete(Bundle values) {
											// TODO Auto-generated method stub
											
											fb.dialog(Activity2.this,"feed",params, new DialogListener() {
												
												@Override
												public void onFacebookError(FacebookError e) {
													// TODO Auto-generated method stub
													
												}
												
												@Override
												public void onError(DialogError e) {
													// TODO Auto-generated method stub
													
												}
												
												@Override
												public void onComplete(Bundle values) {
													// TODO Auto-generated method stub
													
												}
												
												@Override
												public void onCancel() {
													// TODO Auto-generated method stub
													
												}
											});
											
											
											
											
										}
										
										@Override
										public void onCancel() {
											// TODO Auto-generated method stub
											Toast.makeText(Activity2.this, "On Cancel", Toast.LENGTH_SHORT).show();
										}
									});
									
									
								}
								
							}
							
						});
		                
		                
				  }

				  
				   
				});
			
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		fb.authorizeCallback(requestCode, resultCode, data);
	
		
		
	}
	
	
}
