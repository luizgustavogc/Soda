package com.example.demo;


import br.com.support.SodaWebView;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity implements ActivityInterface {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		//Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(), this.toString(), Toast.LENGTH_LONG).show();
		
		SodaWebView wv = (SodaWebView)findViewById(R.id.webview);
		
		
		wv.setWebViewClient(new WebViewClient() {
		    @Override
		    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		            Log.e("WEB_VIEW_TEST", "error code:" + errorCode);
		            super.onReceivedError(view, errorCode, description, failingUrl);
		    }
		 });
		
	      
	      wv.getSettings().setJavaScriptEnabled(true);
	      wv.getSettings().setAllowFileAccess(true);

	      
		wv.loadUrl("file:///android_asset/test.html");
		wv.addJavascriptInterface(new JavaObject(this), "droid");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void showToast(String message)
	{
		final String msg = message;

		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {

				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		});
	}

}
