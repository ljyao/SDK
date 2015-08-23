package org.weixvn.http;

import org.apache.http.Header;

import android.os.Message;
import android.util.Log;

import com.loopj.android.http.BaseJsonHttpResponseHandler;

public abstract class BaseJsonHttpRequestResponse<JSON_TYPE> extends
		BaseJsonHttpResponseHandler<JSON_TYPE> implements
		AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "BaseJsonHttpRequestResponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public BaseJsonHttpRequestResponse() {
		super();
	}

	public BaseJsonHttpRequestResponse(String encoding) {
		super(encoding);
	}

	@Override
	final protected void sendMessage(Message msg) {
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
	 * Base abstract method, handling defined generic type
	 * 
	 * @param statusCode
	 *            HTTP status line
	 * @param headers
	 *            response headers
	 * @param rawJsonResponse
	 *            string of response, can be null
	 * @param response
	 *            response returned by {@link #parseResponse(String, boolean)}
	 */
	public abstract void doResponse(int statusCode, Header[] headers,
			String rawJsonResponse, JSON_TYPE response);

	@Override
	final public void doResponse(int statusCode, Header[] headers,
			byte[] responseBody) {
		String responseString = getResponseString(responseBody, getCharset());
		JSON_TYPE jsonResponse;
		try {
			jsonResponse = parseResponse(responseString, false);
			doResponse(statusCode, headers, responseString, jsonResponse);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			String rawJsonResponse, JSON_TYPE response) {

	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, String rawJsonData, JSON_TYPE errorResponse) {

	}

	@Override
	final public AsyncWaeHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	final public void setHttpClient(AsyncWaeHttpClient httpClinet) {
		this.httpClient = httpClinet;
	}
}
