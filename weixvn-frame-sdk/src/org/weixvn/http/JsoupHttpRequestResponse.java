package org.weixvn.http;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class JsoupHttpRequestResponse extends TextHttpRequestResponse {

	public JsoupHttpRequestResponse() {
		super();
	}

	public JsoupHttpRequestResponse(String encoding) {
		super(encoding);
	}

	/**
	 * Called when request succeeds
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param http
	 *            document response of given charset
	 */
	public void onSuccess(int statusCode, Header[] headers, Document doc) {

	}

	/**
	 * Called when request fails
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param http
	 *            jsoup document response of given charset
	 * @param throwable
	 *            throwable returned when processing request
	 */
	public void onFailure(int statusCode, Header[] headers, Document doc,
			Throwable throwable) {

	}

	/**
	 * Fired when response successfully, override in your own code when you need
	 * progress response data in background.
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param http
	 *            jsoup document response of given charset
	 */
	abstract public void doResponse(int statusCode, Header[] headers,
			Document doc);

	@Override
	final public void doResponse(int statusCode, Header[] headers,
			String responseString) {
		if (responseString == null) {
			this.doResponse(statusCode, headers, (Document) null);
		} else {
			this.doResponse(statusCode, headers, Jsoup.parse(responseString));
		}
	}

	@Override
	final public void onFailure(int statusCode, Header[] headers,
			String responseString, Throwable throwable) {
		if (responseString == null) {
			this.onFailure(statusCode, headers, (Document) null, throwable);
		} else {
			this.onFailure(statusCode, headers, Jsoup.parse(responseString),
					throwable);
		}
	}

	@Override
	final public void onSuccess(int statusCode, Header[] headers,
			String responseString) {
		if (responseString == null) {
			this.onSuccess(statusCode, headers, (Document) null);
		} else {
			this.onSuccess(statusCode, headers, Jsoup.parse(responseString));
		}
	}

}
