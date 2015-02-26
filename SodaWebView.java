/*
 * Copyright (C) 2015 Luiz Gustavo Gesswein Cruz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
	

package br.com.support;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.GeolocationPermissions.Callback;

public class SodaWebView extends WebView
{
	private SodaWebViewClient mSodaWebViewClient;
	private SodaWebViewBridge mSodaWebViewBridge;
	private SodaWebChromeClient mSodaWebChromeClient;
	
	private boolean debug = false;
	
	public SodaWebView(Context context)
	{
		super(context);
		init();
	}
	
	public SodaWebView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	
	public SodaWebView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		
		this.mSodaWebViewClient = new SodaWebViewClient(null);
		this.mSodaWebViewBridge = new SodaWebViewBridge();
		this.mSodaWebChromeClient = new SodaWebChromeClient();
		
		super.setWebViewClient(mSodaWebViewClient);
		super.setWebChromeClient(mSodaWebChromeClient);
	}

	@Override
	public void addJavascriptInterface(Object object, String name)
	{
		
		mSodaWebViewBridge.addInterface(object, name);
		
	}
	
	
	@Override
	public void setWebViewClient(WebViewClient client)
	{
		mSodaWebViewClient.setWebViewClient(client);
	}
	
	@Override
	public void setWebChromeClient(WebChromeClient client)
	{
		mSodaWebChromeClient.setWebChromeClient(client);
	}
	
	class SodaWebViewClient extends WebViewClient
	{
		
		private WebViewClient mWebViewClient;
		
		public SodaWebViewClient(WebViewClient webViewClient)
		{
			this.mWebViewClient = webViewClient;
		}
		
		@Override
		public void doUpdateVisitedHistory(WebView view, String url,
				boolean isReload) {
			if(this.mWebViewClient == null)
			{
				super.doUpdateVisitedHistory(view, url, isReload);
			}
			else
			{
				this.mWebViewClient.doUpdateVisitedHistory(view, url, isReload);
			}
		}
		
		@Override
		public void onFormResubmission(WebView view, Message dontResend,
				Message resend) {

			if(this.mWebViewClient == null)
			{
				super.onFormResubmission(view, dontResend, resend);
			}
			else
			{
				this.mWebViewClient.onFormResubmission(view, dontResend, resend);
			}
		}
		
		@Override
		public void onLoadResource(WebView view, String url) {

			if(this.mWebViewClient == null)
			{
				super.onLoadResource(view, url);
			}
			else
			{
				this.mWebViewClient.onLoadResource(view, url);
			}
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			
			
			if(this.mWebViewClient == null)
			{
				super.onPageFinished(view, url);
			}
			else
			{
				this.mWebViewClient.onPageFinished(view, url);
			}
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			/*
			 * Load JS bridge.
			 */
			view.loadUrl("javascript:"+mSodaWebViewBridge.getBridge());
			
			/*
			 * Load JS interfaces.
			 */
			for (String interfaceName : mSodaWebViewBridge.getInterfaces())
			{
				view.loadUrl("javascript:" + mSodaWebViewBridge.getInterfaceJS(interfaceName));
			}
			
			if(this.mWebViewClient == null)
			{
				super.onPageStarted(view, url, favicon);
			}
			else
			{
				this.mWebViewClient.onPageStarted(view, url, favicon);
			}
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {

			if(this.mWebViewClient == null)
			{
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
			else
			{
				this.mWebViewClient.onReceivedError(view, errorCode, description, failingUrl);
			}
		}
		
		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {

			if(this.mWebViewClient == null)
			{
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}
			else
			{
				this.mWebViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
			}
		}
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
		@Override
		public void onReceivedLoginRequest(WebView view, String realm,
				String account, String args) {

			if(this.mWebViewClient == null)
			{
				super.onReceivedLoginRequest(view, realm, account, args);
			}
			else
			{
				this.mWebViewClient.onReceivedLoginRequest(view, realm, account, args);
			}
		}
		
		@TargetApi(Build.VERSION_CODES.FROYO)
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {

			if(this.mWebViewClient == null)
			{
				super.onReceivedSslError(view, handler, error);
			}
			else
			{
				this.mWebViewClient.onReceivedSslError(view, handler, error);
			}
		}
		
		@Override
		public void onScaleChanged(WebView view, float oldScale, float newScale) {

			if(this.mWebViewClient == null)
			{
				super.onScaleChanged(view, oldScale, newScale);
			}
			else
			{
				this.mWebViewClient.onScaleChanged(view, oldScale, newScale);
			}
		}
		
		@Override
		@Deprecated
		public void onTooManyRedirects(WebView view, Message cancelMsg,
				Message continueMsg) {

			if(this.mWebViewClient == null)
			{
				super.onTooManyRedirects(view, cancelMsg, continueMsg);
			}
			else
			{
				this.mWebViewClient.onTooManyRedirects(view, cancelMsg, continueMsg);
			}
		}
		
		@Override
		public void onUnhandledKeyEvent(WebView view, KeyEvent event) {

			if(this.mWebViewClient == null)
			{
				super.onUnhandledKeyEvent(view, event);
			}
			else
			{
				this.mWebViewClient.onUnhandledKeyEvent(view, event);
			}
		}
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public WebResourceResponse shouldInterceptRequest(WebView view,
				String url) {

			WebResourceResponse result = null;
			
			if(this.mWebViewClient == null)
			{
				result = super.shouldInterceptRequest(view, url);
			}
			else
			{
				result = this.mWebViewClient.shouldInterceptRequest(view, url);
			}
			return result;
		}
		
		@Override
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				
			boolean result = false;
			
			if(this.mWebViewClient == null)
			{
				result = super.shouldOverrideKeyEvent(view, event);
			}
			else
			{
				result = this.mWebViewClient.shouldOverrideKeyEvent(view, event);
			}
			
			return result;
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			boolean result = false;

			if(this.mWebViewClient == null)
			{
				result = super.shouldOverrideUrlLoading(view, url);
			}
			else
			{
				result = this.mWebViewClient.shouldOverrideUrlLoading(view, url);
			}
			return result;
		}
		
		public void setWebViewClient(WebViewClient webViewClient)
		{
			this.mWebViewClient = webViewClient;
		}
		
	};
	
	class SodaWebViewBridge
	{
		private HashMap<String, Object[]> mInterfaces = new HashMap<String, Object[]>();
		private static final String TAG = "SodaWebViewBridge";

		
		public SodaWebViewBridge()
		{

		}
		
		/**
		 * Return a list name of mapped interfaces.
		 * @return list.
		 */
		public Set<String> getInterfaces()
		{
			return this.mInterfaces.keySet();
		}
		
		/**
		 * Return a JavaScript string with functions based on object methods.
		 * @param name interface name
		 * @return a JavaScript string.
		 */
		public String getInterfaceJS(String name)
		{
			return this.mInterfaces.get(name)[1].toString();
		}	
		
		/**
		 * Return a Java Object using the interface name.
		 * @param name interface name
		 * @return java Object.
		 */
		public Object getInterfaceObject(String name)
		{
			return this.mInterfaces.get(name)[0];
		}
		
		/**
		 * Add a interface to the SodaWebView and map an object to a JavaScript element.
		 * @param object java Object to be mapped.
		 * @param name interface name to be used with this object.
		 */
		public void addInterface(Object object, String name)
		{
			String javaScript = mapObjectToJavaScript(object, name);
			
			this.mInterfaces.put(name, new Object[] { object, javaScript });
		}

		/**
		 * Create the bridge that connects HTML to Soda Bridge.
		 * @return a JavaScript string.
		 */
		private String getBridge()
		{
			StringBuilder javaScript = new StringBuilder();

			javaScript.append("function soda_bridge(data)");
			javaScript.append("{");
			javaScript.append("return JSON.parse(prompt(data,'__soda__'));");
			javaScript.append("}");

			return javaScript.toString();
		}

		/**
		 * Create a JavaScript structure based on Java Object.
		 * @param object to be mapped.
		 * @param name interface name
		 * @return a JavaScript string.
		 */
		private String mapObjectToJavaScript(Object object, String name)
		{
			StringBuilder javaScript = new StringBuilder();
			javaScript.append("function ");
			javaScript.append(name);
			javaScript.append("()");
			javaScript.append("{");
			javaScript.append("};");
			
			for (Method method : object.getClass().getMethods())
			{
				String methodName = method.getName();
				
				if(Pattern.matches("equals|hashCode|notify|notifyAll|wait", methodName))
				{
					
					/* Don't be evil. */
					
					continue;
					
				}
				
				if(methodName.equals("getClass"))
				{
					javaScript.append(name);
					javaScript.append(".");
					javaScript.append(methodName);
					javaScript.append(" = function()");
					javaScript.append("{");
					javaScript.append("return 'D\\\'oh!';");
					javaScript.append("};");
				}
				else if(methodName.equals("toString"))
				{
					javaScript.append(name);
					javaScript.append(".");
					javaScript.append(methodName);
					javaScript.append(" = function()");
					javaScript.append("{");
					javaScript.append("return 'Soda Interface';");
					javaScript.append("};");
				}
				else
				{
					int paramCount = 0;
					javaScript.append(name);
					javaScript.append(".");
					javaScript.append(methodName);
					javaScript.append(" = function");
					javaScript.append("(");
					
					int params = method.getParameterTypes().length;
					
					for (int pos = 0; pos < params; pos ++)
					{
						
						javaScript.append("param");
						javaScript.append(paramCount);
						javaScript.append(",");
						paramCount++;
					}
					
					if(params>0) javaScript.deleteCharAt(javaScript.length()-1);
					javaScript.append(")");
					javaScript.append("{");

					javaScript.append("var data = {'interface':'");;
					javaScript.append(name);
					javaScript.append("','method':'");
					javaScript.append(methodName);
					javaScript.append("'");
					
					paramCount=0;
					javaScript.append(",args:");
					if(params == 0)
					{
						javaScript.append("[]");
					}
					else
					{
						javaScript.append("[");
						for (int pos = 0; pos < params; pos ++)
						{
							if(method.getParameterTypes()[pos] == String.class)
							{
								javaScript.append("escape(param");
								javaScript.append(paramCount);
								javaScript.append("),");
							}
							else
							{
								javaScript.append("param");
								javaScript.append(paramCount);
								javaScript.append(",");
							}
							paramCount++;
						}
						javaScript.deleteCharAt(javaScript.length()-1);
						javaScript.append("]");
					}
					
					javaScript.append("};");
					javaScript.append("return soda_bridge(JSON.stringify(data));");
					javaScript.append("};");
				}
			}

			if(debug)
				Log.i(TAG, javaScript.toString());

			return javaScript.toString();
		}
	}
	
	class SodaWebChromeClient extends WebChromeClient
	{
		private WebChromeClient mWebChromeClient;
		
		@Override
		public void onCloseWindow(WebView window)
		{
			if(this.mWebChromeClient == null)
			{
				super.onCloseWindow(window);
			}
			else
			{
				this.mWebChromeClient.onCloseWindow(window);
			}
		}
		
		@TargetApi(Build.VERSION_CODES.FROYO)
		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage)
		{
			boolean ret = false;
			
			if(this.mWebChromeClient == null)
			{
				ret = super.onConsoleMessage(consoleMessage);
			}
			else
			{
				ret = this.mWebChromeClient.onConsoleMessage(consoleMessage);
			}
			
			return ret;
		}
		@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
		@Override
		@Deprecated
		public void onConsoleMessage(String message, int lineNumber,
				String sourceID) {
			
			if(this.mWebChromeClient == null)
			{
				super.onConsoleMessage(message, lineNumber, sourceID);
			}
			else
			{
				this.mWebChromeClient.onConsoleMessage(message, lineNumber, sourceID);
			}

		}
		
		@Override
		public boolean onCreateWindow(WebView view, boolean isDialog,
				boolean isUserGesture, Message resultMsg) {

			boolean ret = false;
			
			if(this.mWebChromeClient == null)
			{
				ret = super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}
			else
			{
				ret = this.mWebChromeClient.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}
			
			return ret;

		}
		
		@Override
		@Deprecated
		public void onExceededDatabaseQuota(String url,
				String databaseIdentifier, long quota,
				long estimatedDatabaseSize, long totalQuota,
				QuotaUpdater quotaUpdater) {

			if(this.mWebChromeClient == null)
			{
				super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
			}
			else
			{
				this.mWebChromeClient.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
			}
		}
		
		@Override
		public void onGeolocationPermissionsHidePrompt() {
			// TODO Auto-generated method stub

			if(this.mWebChromeClient == null)
			{
				super.onGeolocationPermissionsHidePrompt();				
			}
			else
			{
				this.mWebChromeClient.onGeolocationPermissionsHidePrompt();
			}
			
			
		}
		
		@Override
		public void onGeolocationPermissionsShowPrompt(String origin,
				Callback callback) {

			if(this.mWebChromeClient == null)
			{
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}
			else
			{
				this.mWebChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
			}
		}
		
		@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
		@Override
		public void onHideCustomView() {

			if(this.mWebChromeClient == null)
			{
				super.onHideCustomView();
			}
			else
			{
				this.mWebChromeClient.onHideCustomView();
			}
			
		}
		
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {

			boolean ret = false;
			if(this.mWebChromeClient == null)
			{
				ret = super.onJsAlert(view, url, message, result);
			}
			else
			{
				ret = this.mWebChromeClient.onJsAlert(view, url, message, result);
			}
			
			return ret;
		}
		
		@Override
		public boolean onJsBeforeUnload(WebView view, String url,
				String message, JsResult result) {
			// TODO Auto-generated method stub

			boolean ret = false;
			if(this.mWebChromeClient == null)
			{
				ret = super.onJsBeforeUnload(view, url, message, result);
			}
			else
			{
				ret = mWebChromeClient.onJsBeforeUnload(view, url, message, result);
			}
			return ret;

		}
		
		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				JsResult result) {

			boolean ret = false;
			if(this.mWebChromeClient == null)
			{
				ret = super.onJsConfirm(view, url, message, result);
			}
			else
			{
				ret = this.mWebChromeClient.onJsConfirm(view, url, message, result);
			}
			return ret;
		}
		
		@Override
		public boolean onJsPrompt(WebView view, String url, String message,
				String defaultValue, JsPromptResult result) {

			boolean ret = false;
			if(defaultValue!=null && defaultValue.equals("__soda__"))
			{
		        HashMap<String, Object> response = new HashMap<String, Object>();
		        
				ret = true;
				
				try
				{

			        JSONObject jsonObject = new JSONObject(message);
			        
			        Object object = mSodaWebViewBridge.getInterfaceObject(jsonObject.getString("interface"));
			        
			        JSONArray args = jsonObject.getJSONArray("args");
			        
			        Class[] parameterTypes = new Class[args.length()];
			        Object[] argsList = new Object[args.length()];
			        		
			        for(int i=0;i<args.length();i++)
			        {
			        	Object arg = args.get(i);
			        	parameterTypes[i] = arg.getClass();
			        	if(parameterTypes[i] == Integer.class) parameterTypes[i] = int.class;
			        	argsList[i] = parameterTypes[i]==String.class? URLDecoder.decode(arg.toString(), "UTF-8") : arg;
			        }
		
			        Method objectMethod = object.getClass().getMethod(jsonObject.getString("method"), parameterTypes);
			        Object objectResult = objectMethod.invoke(object, argsList);
			        
			        response.put("success", true);
			        response.put("result", objectResult);
		        
				}
				catch(Exception ex)
				{
			        response.put("success", false);
			        response.put("result", ex.toString());
				}
				finally
				{
					result.confirm(new JSONObject(response).toString());
				}
			
			}
			else if(this.mWebChromeClient == null)
			{
				ret = super.onJsPrompt(view, url, message, defaultValue, result);
			}
			else
			{
				ret = this.mWebChromeClient.onJsPrompt(view, url, message, defaultValue, result);
			}
			
			return ret;
			
		}
		
		@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
		@Override
		@Deprecated
		public boolean onJsTimeout() {

			boolean ret = false;
			
			if(this.mWebChromeClient == null)
			{
				ret = super.onJsTimeout();
			}
			else
			{
				ret = this.mWebChromeClient.onJsTimeout();
			}
			
			return ret;
		}
		
		@Override
		public void onProgressChanged(WebView view, int newProgress) {

			if(this.mWebChromeClient == null)
			{
				super.onProgressChanged(view, newProgress);
			}
			else
			{
				this.mWebChromeClient.onProgressChanged(view, newProgress);
			}
			
		}
		
		@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
		@Override
		@Deprecated
		public void onReachedMaxAppCacheSize(long requiredStorage, long quota,
				QuotaUpdater quotaUpdater) {
			
			if(this.mWebChromeClient == null)
			{
				super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
			}
			else
			{
				this.mWebChromeClient.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
			}
			
		}
		
		@Override
		public void onReceivedIcon(WebView view, Bitmap icon) {

			if(this.mWebChromeClient == null)
			{
				super.onReceivedIcon(view, icon);
			}
			else
			{
				this.mWebChromeClient.onReceivedIcon(view, icon);
			}
		}
		
		@Override
		public void onReceivedTitle(WebView view, String title) {

			if(this.mWebChromeClient == null)
			{
				super.onReceivedTitle(view, title);
			}
			else
			{
				this.mWebChromeClient.onReceivedTitle(view, title);
			}

		}
		
		@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
		@Override
		public void onReceivedTouchIconUrl(WebView view, String url,
				boolean precomposed) {
			
			if(this.mWebChromeClient == null)
			{
				super.onReceivedTouchIconUrl(view, url, precomposed);
			}
			else
			{
				this.mWebChromeClient.onReceivedTouchIconUrl(view, url, precomposed);
			}

		}
		
		@Override
		public void onRequestFocus(WebView view) {

			if(this.mWebChromeClient == null)
			{
				super.onRequestFocus(view);
			}
			else
			{
				this.mWebChromeClient.onRequestFocus(view);
			}

		}
		
		@TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
		@Override
		public void onShowCustomView(View view, CustomViewCallback callback) {
			
			if(this.mWebChromeClient == null)
			{
				super.onShowCustomView(view, callback);
			}
			else
			{
				this.mWebChromeClient.onShowCustomView(view, callback);
			}
			
		}
		
		@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		@Override
		@Deprecated
		public void onShowCustomView(View view, int requestedOrientation,
				CustomViewCallback callback) {

			if(this.mWebChromeClient == null)
			{
				super.onShowCustomView(view, requestedOrientation, callback);
			}
			else
			{
				this.mWebChromeClient.onShowCustomView(view, requestedOrientation, callback);
			}
		}
		
		public void setWebChromeClient(WebChromeClient client)
		{
			this.mWebChromeClient = client;
		}
	}

}
