package com.tustar.pocket.volley;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpStatus;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.tustar.pocket.utils.DeviceUtils;
import com.tustar.pocket.utils.Logger;
import com.tustar.pocket.utils.NetworkUtils;
import com.tustar.pocket.volley.TuListener.ResultType;

/**
 * 
 * @author tustar
 * 
 */
public class TuNetCenter {

	private static final String TAG = TuNetCenter.class.getSimpleName();

	/** The default socket timeout in milliseconds */
	public static final int DEFAULT_TIMEOUT_MS = 30 * 1000;

	/** The default number of retries */
	public static final int DEFAULT_MAX_RETRIES = 1;

	/** The default backoff multiplier */
	public static final float DEFAULT_BACKOFF_MULT = 1f;

	/**
	 * 
	 * @param appContext
	 * @param request
	 * @param wListener
	 * @return
	 */
	public static void doRequest(Context context,
			final TuRequest<? extends TuResponse> request,
			TuListener listener) {

		final Context appContext = context.getApplicationContext();
		final WeakReference<TuListener> wListener = new WeakReference<TuListener>(
				listener);

		// All get request use cache.
		if (request.getMethod() == Method.GET) {
			request.setShouldCache(true);
		}

		// No network.
		if (!NetworkUtils.isNetworkAvailable(appContext)) {
			doResponse(appContext, request, null, wListener);
			return;
		}

		int method = request.getMethod();
		String methodStr = "GET";
		switch (method) {
		case Method.GET:
			methodStr = "GET";
			break;
		case Method.POST:
			methodStr = "POST";
			break;
		case Method.PUT:
			methodStr = "PUT";
			break;
		case Method.DELETE:
			methodStr = "DELETE";
			break;
		case Method.HEAD:
			methodStr = "HEAD";
			break;
		case Method.OPTIONS:
			methodStr = "OPTIONS";
			break;
		case Method.TRACE:
			methodStr = "TRACE";
			break;
		case Method.PATCH:
			methodStr = "PATCH";
			break;
		default:
			break;
		}

		// Headers
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("device_id", DeviceUtils.getIMEI(appContext));
		switch (method) {
		case Method.POST:
		case Method.PUT:
			headers.put("Accept", "application/json");
			break;
		default:
			break;
		}
		headers.putAll(request.getHeaders());
		request.setHeaders(headers);

		// Params
		Map<String, String> params = new HashMap<String, String>();
		Map<String, Object> requestParams = request.getParams();
		if (requestParams != null) {
			for (Entry<String, Object> param : requestParams.entrySet()) {
				params.put(param.getKey(), String.valueOf(param.getValue()));
			}
		}

		final String url = request.getUrl();
		try {
			BaseRequest voRequest = new BaseRequest(method, url,
					new Listener<NetworkResponse>() {

						@Override
						public void onResponse(NetworkResponse response) {

							Logger.i(TAG, "");
							Logger.i(TAG, "------------Response-------------");
							Logger.d(TAG, "onResponse :: response = " + url);

							doResponse(appContext, request, response, wListener);

						}

					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {

							Logger.i(TAG, "");
							Logger.i(TAG, "------------Response-------------");
							Logger.d(TAG, "onErrorResponse :: error = " + url);

							if (error != null) {
								doResponse(appContext, request,
										error.networkResponse, wListener);
							} else {
								doResponse(appContext, request, null, wListener);
							}
						}
					});
			Logger.i(TAG, "");
			Logger.i(TAG, "------------Request-------------");

			// Body Content-Type
			DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(
					DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES,
					DEFAULT_BACKOFF_MULT);
			switch (method) {
			case Method.POST:
			case Method.PUT:
				voRequest.setBodyContentType("application/json");
				voRequest.setBody(request.getRequestBody());
				retryPolicy = new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, 0,
						DEFAULT_BACKOFF_MULT);
				break;

			default:
				voRequest.setParams(params);
				break;
			}
			voRequest.setHeaders(headers);
			Logger.d(TAG,
					"doRequest :: " + methodStr + " => " + voRequest.getUrl());
			Logger.d(TAG, "doRequest :: Headers => " + request.getHeaders());
			Logger.d(TAG, "doRequest :: Params => " + params);
			Logger.d(
					TAG,
					"doRequest :: BodyContentType => "
							+ voRequest.getBodyContentType());
			Logger.d(TAG, "doRequest :: Body => " + request.getRequestBody());

			// Default retry policy
			voRequest.setRetryPolicy(retryPolicy);

			// Other
			voRequest.setTag(request.getTag());
			voRequest.setCacheKey(request.getCacheKey());
			voRequest.setShouldCache(request.isShouldCache());
			Logger.d(TAG, "doRequest :: CacheKey => " + request.getCacheKey());
			TuRequestManager.getRequestQueue().add(voRequest);
			Logger.i(TAG, "------------Request-------------");
			Logger.i(TAG, "");

		} catch (Exception e) {
			Logger.e(TAG, "doRequest :: Exception Error => " + e.getMessage());
		}
	}

	private static void doResponse(Context context,
			TuRequest<? extends TuResponse> request,
			NetworkResponse response, WeakReference<TuListener> wListener) {

		ResultType resultType = ResultType.UNKNOWN_ERROR;
		TuResponse freshResponse = null;
		try {
			freshResponse = request.getResponseClass().newInstance();
			boolean shouldCache = request.isShouldCache();

			// Return error.
			if (response == null) {
				// NO network.
				if (!NetworkUtils.isNetworkAvailable(context)) {
					resultType = ResultType.NO_NETWORK;
				}
				// Unknown error.
				else {
					resultType = ResultType.UNKNOWN_ERROR;
				}

				if (shouldCache) {
					freshResponse = getCacheResponse(context, request,
							freshResponse);
					if (!TextUtils.isEmpty(freshResponse.getBody())) {
						resultType = ResultType.SUCCESS;
					}
				}
			}
			// Return normal.
			else {

				freshResponse.parserNetworkResponse(response);
				byte[] data = response.data;
				if (data != null && data.length > 0) {
					Logger.d(TAG, "doResponse :: Use server return data");
					freshResponse.parserResponseData(data);
					freshResponse.parseResponseBody();
				} else {
					Logger.d(TAG, "doResponse :: Server return data is empty");
					if (shouldCache && request.getMethod() == Method.GET) {
						freshResponse = getCacheResponse(context, request,
								freshResponse);
					}
				}

				int sc = response.statusCode;
				// 200 <= sc < 400
				if (sc >= HttpStatus.SC_OK && sc < HttpStatus.SC_BAD_REQUEST) {
					resultType = ResultType.SUCCESS;
				}
				// sc = 408
				else if (sc == HttpStatus.SC_REQUEST_TIMEOUT) {
					resultType = ResultType.REQUEST_TIMEOUT;
				}
				// 400 =< sc < 500
				else if (sc >= HttpStatus.SC_BAD_REQUEST
						&& sc < HttpStatus.SC_INTERNAL_SERVER_ERROR) {
					resultType = ResultType.CLIENT_ERROR;
				}
				// sc = 504
				else if (sc == HttpStatus.SC_GATEWAY_TIMEOUT) {
					resultType = ResultType.GATEWAY_TIMEOUT;
				}
				// 500 <= sc <= 599
				else if (sc >= HttpStatus.SC_INTERNAL_SERVER_ERROR && sc <= 599) {
					resultType = ResultType.SERVER_ERROR;
				}
				// other
				else {
					resultType = ResultType.UNKNOWN_ERROR;
				}

				// Use cache data.
				if (TextUtils.isEmpty(freshResponse.getBody()) && shouldCache
						&& request.getMethod() == Method.GET) {
					freshResponse = getCacheResponse(context, request,
							freshResponse);
					if (!TextUtils.isEmpty(freshResponse.getBody())) {
						resultType = ResultType.SUCCESS;
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			Logger.e(
					TAG,
					"doResponse :: InstantiationException Error => "
							+ e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			Logger.e(
					TAG,
					"doResponse :: IllegalAccessException Error => "
							+ e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.e(TAG, "doResponse :: Exception Error => " + e.getMessage());
		}
		Logger.d(TAG, "doResponse :: resultType => " + resultType);
		if (freshResponse != null) {
			freshResponse.setTag(request.getTag());
			freshResponse.setResultType(resultType);
			Logger.d(
					TAG,
					"doResponse :: statusCode => "
							+ freshResponse.getStatusCode());
			Logger.d(TAG,
					"doResponse :: Headers => " + freshResponse.getHeaders());
			Logger.d(TAG, "doResponse :: Body => " + freshResponse.getBody());
		}

		// Callback
		try {

			TuListener listener = wListener.get();
			if (listener == null) {
				return;
			}

			if (listener instanceof android.support.v4.app.Fragment) {
				android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) listener;
				if (fragment.isAdded()) {
					listener.onRequestFinished(freshResponse);
				}
			} else {
				listener.onRequestFinished(freshResponse);
			}
		} catch (Exception e) {
			Logger.d(TAG, "doResponse :: Callback Error = " + e.getMessage());
			e.printStackTrace();
		}
		Logger.i(TAG, "------------Response-------------");
		Logger.i(TAG, "");
	}

	private static TuResponse getCacheResponse(Context context,
			TuRequest<? extends TuResponse> request,
			TuResponse freshResponse) {

		Cache.Entry entry = getCacheEntry(context, request);
		if (entry != null) {
			byte[] data = entry.data;
			if (data != null && data.length > 0) {
				Logger.d(TAG, "getCacheResponse :: Use ETAG cache data");
				freshResponse.parserResponseData(data);
				freshResponse.parseResponseBody();
			} else {
				Logger.d(TAG, "getCacheResponse :: ETAG cache data is empty");
			}
		} else {
			Logger.w(TAG, " getCacheResponse :: Cache entry is empty");
		}

		return freshResponse;
	}

	private static Cache.Entry getCacheEntry(Context context,
			TuRequest<? extends TuResponse> request) {

		Cache cache = TuRequestManager.getRequestQueue().getCache();
		Cache.Entry entry = cache.get(request.getCacheKey());
		return entry;
	}

	public static void clearCache() {

		Cache cache = TuRequestManager.getRequestQueue().getCache();
		cache.clear();
	}
	
	public static void clearCacheByKey(String cacheKey) {

		Cache cache = TuRequestManager.getRequestQueue().getCache();
		cache.remove(cacheKey);
	}
}
