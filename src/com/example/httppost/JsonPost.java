package com.example.httppost;

import android.app.Activity;
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
//		if (!str.isEmpty()) {
//			Toast.makeText(currentActivity.getBaseContext(), "data can't empty!", Toast.LENGTH_LONG).show();
//			return;
//		}

		try {
			HttpClient client = new HttpClient();
			StringEntity data = new StringEntity(str);
			client.performAsyncPost(url, data, new MessageCallback());
		} catch (Exception e) {
			Log.d("JsonParse", e.getLocalizedMessage());
		}
	}

	private class MessageCallback implements CallBack {
		@Override
		public void callback(String... messages) {
			for (String m : messages)
				Toast.makeText(currentActivity.getBaseContext(), m, Toast.LENGTH_LONG).show();
		}
	}
}
