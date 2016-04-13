package com.example.httppost;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.entity.StringEntity;

/**
 * Created by ipm on 14.02.16.
 */
public class JsonPost {
	String str;
	String url;
	Activity currentActivity;

	public JsonPost(String url, String str, Activity currentActivity) {
		this.str = str;
		this.url = url;
		this.currentActivity = currentActivity;
	}

	public void persist() {
		// if (!str.isEmpty()) {
		// Toast.makeText(currentActivity.getBaseContext(), "data can't empty!",
		// Toast.LENGTH_LONG).show();
		// return;
		// }

		try {
			HttpClient client = new HttpClient();
			StringEntity data = new StringEntity(str);
//			client.performAsyncPost(url, data, new MessageCallback());
	
			client.performAsyncPost(url, data,handler);
		} catch (Exception e) {
			Log.d("JsonParse", e.getLocalizedMessage());
		}
	}

	private class MessageCallback implements CallBack {
		@Override
		public void callback(String... messages) {
			for (String m : messages)
//				Toast.makeText(currentActivity.getBaseContext(), m, Toast.LENGTH_LONG).show();
				System.out.println(m);
//				String sss = m;
//			    Message message = Message.obtain();
//			    Bundle bundleData = new Bundle();  
//			    bundleData.putString("Name", m);  
//			    message.setData(bundleData); 
//			    handler.sendMessage(message);
				
		}
	}
	
	 Handler handler = new Handler(){   
	        public void handleMessage(Message msg) {       
	            super.handleMessage(msg);  
	            Bundle data = msg.getData();
//	            Toast.makeText(currentActivity.getBaseContext(),data.getString("result"), Toast.LENGTH_LONG).show();
				System.out.println("--------:"+data.getString("result"));
	        }  
	          
	    };  
	
}
