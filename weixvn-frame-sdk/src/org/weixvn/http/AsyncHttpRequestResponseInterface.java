package org.weixvn.http;

import org.apache.http.Header;

public interface AsyncHttpRequestResponseInterface {

	/**
	 * Fired when the request is started, you can change the request param.
	 * 
	 * @param request
	 *            request param
	 */
	void onRequest(final AsyncWaeHttpRequest request);

	/**
	 * Fired when response successfully, override in your own code when you need
	 * progress response data in background.
	 * 
	 * @param statusCode
	 *            the status code of the response
	 * @param headers
	 *            return headers, if any
	 * @param responseBody
	 *            the body of the HTTP response from the server
	 */
	void doResponse(int statusCode, Header[] headers, byte[] responseBody);

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
