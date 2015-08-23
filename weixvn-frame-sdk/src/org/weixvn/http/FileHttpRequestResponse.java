package org.weixvn.http;

import java.io.File;

import org.apache.http.Header;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

public abstract class FileHttpRequestResponse extends
		FileAsyncHttpResponseHandler implements
		AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "FileHttpRequestResponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public FileHttpRequestResponse(File file) {
		super(file);
	}

	public FileHttpRequestResponse(Context context) {
		super(context);
	}

	public FileHttpRequestResponse(File file, boolean append) {
		super(file, append);
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
	final public void doResponse(int statusCode, Header[] headers,
			byte[] responseBody) {
		doResponse(statusCode, headers, getTargetFile());
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
