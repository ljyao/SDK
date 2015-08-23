package org.weixvn.http;

import android.util.Log;

public abstract class SeriesHttpRequestResponse implements Runnable {
	protected static final String LOG_TAG = "SeriesHttpRequestResponse";
	protected AsyncWaeHttpClient httpClient;

	public SeriesHttpRequestResponse(AsyncWaeHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * Fired when a request returns successfully, override to handle in your own
	 * code
	 */
	public abstract void onSuccess();

	/**
	 * Fired when a request fails to complete, override to handle in your own
	 * code
	 * 
	 * @param error
	 *            the underlying cause of the failure
	 */
	public abstract void onFailure(String error);

	public void onCancel() {
		Log.d(LOG_TAG, "Request got cancelled");
	}

	/**
	 * Fired when the request progress, override to handle in your own code
	 * 
	 * @param curentRequestResponse
	 *            offset from start of http request and response
	 * @param totalRequestResponse
	 *            total size of http request and response
	 */
	public void onProgress(int curentRequestResponse, int totalRequestResponse) {

	}

	/**
	 * Fired when the request is started, override to handle in your own code
	 */
	public void onStart() {
	}

	/**
	 * Fired in all cases when the request is finished, after both success and
	 * failure, override to handle in your own code
	 */
	public void onFinish() {
	}
}
