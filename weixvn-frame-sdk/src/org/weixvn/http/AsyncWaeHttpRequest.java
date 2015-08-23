package org.weixvn.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.RequestParams;

/**
 * Http Request
 * 
 * @author lcl
 * 
 */
@SuppressWarnings("unused")
public class AsyncWaeHttpRequest {
	private static final String LOG_TAG = "AsyncWaeHttpRequest";
	protected RequestType requestType = RequestType.GET; // 请求类型
	protected RequestParams requestParams = new RequestParams(); // 请求参数
	private String requestURI = null;
	private String contentType = null;
	private Context context;
	private Header[] requestHeaders = null;
	private HttpEntity entity;
	private boolean isCancelled = false;
	private AsyncWaeHttpClient httpClient = null; // HTTP请求客户端

	public AsyncWaeHttpRequest(AsyncWaeHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * HTTP请求类型的枚举
	 * <p>
	 * 目前支持GET, POST, PUT, DELETE，HEAD 网络请求
	 * 
	 * @author weixvn
	 */
	public enum RequestType {
		GET, POST, PUT, DELETE, HEAD
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType type) {
		requestType = type;
	}

	public RequestParams getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(RequestParams params) {
		requestParams = params;
	}

	public String getRequestURI() {
		return this.requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	public Header[] getRequestHeaders() {
		return this.requestHeaders;
	}

	public void setRequestHeaders(Header[] requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	/**
	 * @return the entity
	 */
	public HttpEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(HttpEntity entity) {
		this.entity = entity;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}
