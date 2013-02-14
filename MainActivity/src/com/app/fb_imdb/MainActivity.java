package com.app.fb_imdb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.PublicKey;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String array_spinner[];
	String full_data[][];
	Spinner s;
	String url_final;
	TextView t1, mEdit1,t;
	String returned;
	  String query,query1;
	public int publiclen;
	public String text11;
	
	URI website;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mEdit1 =(EditText) findViewById(R.id.editText1);
       t1=(TextView) findViewById(R.id.textView1);
       
        array_spinner=new String[4];
        array_spinner[0]="All Types";
        array_spinner[1]="Feature Films";
        array_spinner[2]="TV Series";
        array_spinner[3]="Video Games";
        s = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
        Button button = (Button) findViewById(R.id.button1);      
         button.setOnClickListener(new View.OnClickListener() {
           
             @Override
             public void onClick(View v) {
                 
                 text11 =  mEdit1.getText().toString();
                  String text22 = s.getSelectedItem().toString();
                  if(text11.equals(""))
                  Toast.makeText(MainActivity.this, "Please enter data!" , Toast.LENGTH_LONG).show();
                  //String temp_url=text1+"&title_type="+text2;
                 
						try {
							query = URLEncoder.encode(text11, "utf-8");
							query1 = URLEncoder.encode(text22, "utf-8");
							url_final = "THE URL IS TO BE PUT LATER..."
							
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					//	Toast.makeText(MainActivity.this, "Link:"+url_final , Toast.LENGTH_SHORT).show();
						 grabURL(url_final);
					
		    } //onclickk method ends here
             
             
         });
         
    }
	
	
	public void grabURL(String url) {
        new GrabURL().execute(url);
    }
    
    private class GrabURL extends AsyncTask<String, Void, Void> {
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        
        protected void onPreExecute() {
            Dialog.setMessage("Downloading source..");
            Dialog.show();
        }

        protected Void doInBackground(String... urls) {
            try {
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);
            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }
            
            return null;
        }
        
        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
         //   TextView text1=(TextView) findViewById(R.id.textView2);
            if (Error != null) {
            	  Toast.makeText(MainActivity.this,"Data Does not exists!", Toast.LENGTH_LONG).show();
           } else {
            	
            	try {
            	
            		JSONObject fulljson = new JSONObject(Content);
                	JSONObject results = fulljson.getJSONObject("results");
                	JSONArray result=results.getJSONArray("result");
	                //StringBuilder contents=new StringBuilder();
	                full_data=new String[result.length()][6];

	                                   
               	for(int i=0;i<result.length();i++)
            	{
            			JSONObject temp_content = result.getJSONObject(i);
            			String temp_content1 = temp_content.getString("cover");
            			String temp_content2 = temp_content.getString("title");
    					String temp_content3 = temp_content.getString("year");
    					String temp_content4 = temp_content.getString("rating");
    					String temp_content5 = temp_content.getString("director");
    					String temp_content6 = temp_content.getString("details");
    					
    	    			full_data[i][0]=temp_content1;
            			full_data[i][1]=temp_content2;
            			full_data[i][2]=temp_content3;
            			full_data[i][3]=temp_content4;
            			full_data[i][4]=temp_content5;
            			full_data[i][5]=temp_content6;
            			
            	}
               	publiclen=result.length();
             
            	
               	//Intent intent = new Intent(MainActivity.this, Activity2.class);
               	Intent intent = new Intent(MainActivity.this, Activity2.class);              	
               	ABC s_full = ABC.getSingletonObject();
               	s_full.setString(full_data);
               	
               	
               	intent.putExtra("extra",publiclen);
               	startActivity(intent);
             
               	
               
               	
               	
			} catch (JSONException e) {
					// TODO Auto-generated catch bloc
				if(text11.equals(""))
	                  Toast.makeText(MainActivity.this, "Please enter data!" , Toast.LENGTH_LONG).show();
				else
				Toast.makeText(MainActivity.this,"No Results found!", Toast.LENGTH_LONG).show();
				}
            	 	
            		
            }
        }
        
    }
	
    
 
    
}
