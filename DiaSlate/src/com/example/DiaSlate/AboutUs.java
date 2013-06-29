package com.example.DiaSlate;

import com.example.DiaSlate.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutUs extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
setContentView(R.layout.activity_about_us);
		
		WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/tree/tree.html");
	
	}

}
