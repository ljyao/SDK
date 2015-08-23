package org.weixvn.http;

import org.apache.http.Header;

import android.os.Message;
import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

public abstract class TextHttpRequestResponse extends TextHttpResponseHandler
		implements AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "TextHttpRequestResponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public TextHttpRequestResponse() {
		super();
	}

	public TextHttpRequestResponse(String encoding) {
		super(encoding);
	}

	/**
	 * Fired when response successfully, override in your own code when you need
	 * progress response data in background.
	 * 
	 * @param statusCode
	 *            the status code of the response
	 * @param headers
	 *            return headers, if any
	 * @param responseString
	 *            the string body of the HTTP response from the server
	 */
	public abstract void doResponse(int statusCode, Header[] headers,
			String responseString);

	@Override
	final public void doResponse(int statusCode, Header[] headers,
			byte[] responseBody) {
		doResponse(statusCode, headers,
				getResponseString(responseBody, getCharset()));
	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			String responseString, Throwable throwable) {

	}

	@Override
	public void onSuccess(int statusCode, Header[] headers,
			String responseString) {

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

	@Override
	final public AsyncWaeHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	final public void setHttpClient(AsyncWaeHttpClient httpClinet) {
		this.httpClient = httpClinet;
	}
}
