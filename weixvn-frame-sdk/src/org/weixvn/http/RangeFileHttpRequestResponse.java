package org.weixvn.http;

import java.io.File;

import org.apache.http.Header;

import android.os.Message;
import android.util.Log;

import com.loopj.android.http.RangeFileAsyncHttpResponseHandler;

public abstract class RangeFileHttpRequestResponse extends
		RangeFileAsyncHttpResponseHandler implements
		AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "RangeFileHttpRequestResponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public RangeFileHttpRequestResponse(File file) {
		super(file);
	}

	@Override
	final public AsyncWaeHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	final public void doResponse(int statusCode, Header[] headers,
			byte[] responseBody) {
		doResponse(statusCode, headers, getTargetFile());
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
	 * Fired when response successfully, override in your own code when you need
	 * progress response file in background.
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response http headers if any
	 * @param file
	 *            file in which the response is stored
	 */
	public void doResponse(int statusCode, Header[] headers, File file) {

	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, File file) {

	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, File file) {

	}

	@Override
	final public void setHttpClient(AsyncWaeHttpClient httpClinet) {
		this.httpClient = httpClinet;
	}
}
