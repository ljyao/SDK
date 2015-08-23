package org.weixvn.http;

public interface AsyncHttpRequestInterface {

	/**
	 * Fired when the request is started, you can change the request param.
	 * 
	 * @param request
	 *            request param
	 */
	void onRequest(final AsyncWaeHttpRequest request);

	/**
	 * Get http client when you want change some httpclient params.
	 * 
	 * @return AsyncWaeHttpClient
	 */
	public AsyncWaeHttpClient getHttpClient();

	/**
	 * set http client
	 * 
	 */
	public void setHttpClient(AsyncWaeHttpClient httpClinet);
}
