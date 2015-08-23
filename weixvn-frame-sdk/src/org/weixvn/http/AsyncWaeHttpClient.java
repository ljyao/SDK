package org.weixvn.http;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * http client 派生于{@link AsyncHttpClient}，主要添加支持同步异步调用。
 * 
 * @author lcl
 */
public class AsyncWaeHttpClient extends AsyncHttpClient {
	public static final String LOG_TAG = "AsyncWaeHttpClient";
	public static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	public static final String LANGUAGE_CN = "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4";
	public static final String HEADER_REFERER = "Referer";
	/**
	 * 缓存数据池
	 */
	private Map<String, Object> cacheMap = new HashMap<String, Object>();

	public AsyncWaeHttpClient() {
		super();
	}

	public AsyncWaeHttpClient(boolean fixNoHttpResponseException, int httpPort,
			int httpsPort) {
		super(fixNoHttpResponseException, httpPort, httpsPort);
	}

	public AsyncWaeHttpClient(int httpPort, int httpsPort) {
		super(httpPort, httpsPort);
	}

	public AsyncWaeHttpClient(int httpPort) {
		super(httpPort);
	}

	public AsyncWaeHttpClient(SchemeRegistry schemeRegistry) {
		super(schemeRegistry);
	}

	/**
	 * 得到缓存的数据池
	 */
	public Map<String, Object> getCache() {
		return cacheMap;
	}

	/**
	 * 设置缓存数据
	 * <p>
	 * 缓存数据可用于请求时需要设置的额外数据（不会传输到web），<br>
	 * 或解析时需要额外存储的数据
	 * 
	 * @param key
	 *            关键字
	 * @param value
	 *            键值
	 */
	public void putCache(String key, Object value) {
		this.cacheMap.put(key, value);
	}

	/**
	 * 移除缓存数据
	 * 
	 * @param key
	 *            关键字
	 */
	public void removeCache(String key) {
		this.cacheMap.remove(key);
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param key
	 *            关键字
	 * @return 键值
	 */
	public Object getCache(String key) {
		return this.cacheMap.get(key);
	}

	/**
	 * 清理缓存数据池
	 */
	public void clearCache() {
		cacheMap.clear();
	}

	/**
	 * 获得CookieStore
	 * 
	 * @return CookieStore
	 */
	public CookieStore getCookieStore() {
		return ((DefaultHttpClient) this.getHttpClient()).getCookieStore();
	}

	public RequestHandle execute(
			AsyncHttpRequestResponseInterface httpRequestResponse) {
		if (httpRequestResponse == null) {
			Log.e(LOG_TAG, "httpRequest can't be null!");
			return null;
		}

		// 装载请求参数
		AsyncWaeHttpRequest request = new AsyncWaeHttpRequest(this);
		httpRequestResponse.setHttpClient(this);
		httpRequestResponse.onRequest(request);

		// 检查请求参数
		if (request.getRequestURI() == null) {
			Log.e(LOG_TAG, "request url error!");
			return null;
		}

		// 发送网页请求类型
		RequestHandle requestHandle = null; // HTTP请求Handle
		ResponseHandlerInterface responseHandler = null;
		if (ResponseHandlerInterface.class.isInstance(httpRequestResponse)) {
			responseHandler = (ResponseHandlerInterface) httpRequestResponse;
		} else {
			Log.e(LOG_TAG,
					"httpRequest can't convert to ResponseHandlerInterface!");
			return null;
		}

		if (request.isCancelled()) {
			responseHandler.sendCancelMessage();
			Log.d(LOG_TAG, "User cancel the request!");
			return null;
		}

		switch (request.getRequestType()) {
		case GET:
			requestHandle = get(request.getRequestURI(),
					request.getRequestParams(), responseHandler);
			break;
		case POST:
			requestHandle = post(request.getRequestURI(),
					request.getRequestParams(), responseHandler);
			break;
		case PUT:
			requestHandle = put(request.getRequestURI(),
					request.getRequestParams(), responseHandler);
			break;
		case DELETE:
			requestHandle = delete(request.getRequestURI(), responseHandler);
			break;
		case HEAD:
			requestHandle = head(request.getRequestURI(), responseHandler);
			break;
		default:
			Log.e(LOG_TAG, "unknown request type:"
					+ request.getRequestType().toString());
			break;
		}

		return requestHandle;
	}
}
