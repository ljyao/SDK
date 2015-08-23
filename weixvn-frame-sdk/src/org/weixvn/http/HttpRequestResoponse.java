package org.weixvn.http;

import org.apache.http.Header;

import android.os.Message;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

public abstract class HttpRequestResoponse extends AsyncHttpResponseHandler
		implements AsyncHttpRequestResponseInterface {
	private static final String LOG_TAG = "HttpRequestResoponse";
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

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
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			byte[] responseBody, Throwable error) {

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
