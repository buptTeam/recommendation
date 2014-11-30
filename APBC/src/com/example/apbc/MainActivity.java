package com.example.apbc;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myhttp.MyhttpClient;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	 EditText useNameEditText;
	 EditText passwordEditText;
	 Button loginB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useNameEditText = (EditText)super.findViewById(R.id.username_edit);
        passwordEditText = (EditText)super.findViewById(R.id.password_edit);
        loginB=(Button)findViewById(R.id.login_button);
        loginB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("test", "click");
				String userName= useNameEditText.getText().toString();
				String password=passwordEditText.getText().toString();
				RequestParams params = new RequestParams();
				 params.put("username", userName);
				 params.put("password", password);
				 
				MyhttpClient.post("hello/check.php", params, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						// If the response is JSONObject instead of expected
						// JSONArray
						Log.d("test", "hello>>>>"+response);
						System.out.println("response:>>>>"+response);
						try {
							String msg= response.getString("msg");
							if(msg.equals("true"))
							{
								Intent intent = new Intent();  
				                intent.setClass(MainActivity.this, MainPage.class);  
				                startActivity(intent);  
				                finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity   
								
							}
							else {
								
								
							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray timeline) {
						// Pull out the first event on the public timeline
						JSONObject firstEvent = null;
						String tweetText = null;
						try {
							firstEvent = (JSONObject) timeline.get(0);
							 tweetText = firstEvent.getString("text");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

						
						// Do something with the response
						Log.d("test", "hello>>>>"+tweetText);
					}
				}); 
			}
		}); 
    }
    
    
}
