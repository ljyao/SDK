package org.weixvn.http;

import org.apache.http.Header;

import android.os.Message;
import android.util.Log;

import com.loopj.android.http.BinaryHttpResponseHandler;

public abstract class BinaryHttpRequestResponse extends
		BinaryHttpResponseHandler implements AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "BinaryHttpRequestResponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public BinaryHttpRequestResponse() {
		super();
	}

	public BinaryHttpRequestResponse(String[] allowedContentTypes) {
		super(allowedContentTypes);
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
	public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {

	}

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] binaryData,
			Throwable error) {

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
