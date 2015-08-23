package org.weixvn.http;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class JsonHttpRequestResponse extends JsonHttpResponseHandler
		implements AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "JsonHttpRequestResponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public JsonHttpRequestResponse() {
		super();
	}

	public JsonHttpRequestResponse(String encoding) {
		super(encoding);
	}

	@Override
	final public AsyncWaeHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	final public void doResponse(int statusCode, Header[] headers,
			byte[] responseBody) {
		if (statusCode != HttpStatus.SC_NO_CONTENT) {

			try {
				final Object jsonResponse = parseResponse(responseBody);

				if (jsonResponse instanceof JSONObject) {
					onSuccess(statusCode, headers, (JSONObject) jsonResponse);
				} else if (jsonResponse instanceof JSONArray) {
					onSuccess(statusCode, headers, (JSONArray) jsonResponse);
				}
			} catch (final JSONException ex) {

			}
		}
	}

	@Override
	protected void sendMessage(Message msg) {
		// 发送OnSucce之前，线程池中调用doResponse
		if (msg.what == SUCCESS_MESSAGE) {
			Object[] response = (Object[]) msg.obj;
			if (response != null && response.length >= 3) {
				doResponse((Integer) response[0], (Header[]) response[1],
						(byte[]) response[2]);
			} else {
				Log.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
			}
		}

		super.sendMessage(msg);
	}

	/**
	 * Fired when response successfully, override in your own code when you need
	 * progress response JSONObject in background.
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param response
	 *            parsed response if any
	 */
	public void doResponse(int statusCode, Header[] headers, JSONObject response) {

	}

	/**
	 * Fired when response successfully, override in your own code when you need
	 * progress response JSONArray in background.
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param response
	 *            parsed response if any
	 */
	public void doResponse(int statusCode, Header[] headers, JSONArray response) {

	}

	@Override
	final public void setHttpClient(AsyncWaeHttpClient httpClinet) {
		this.httpClient = httpClinet;
	}
}
