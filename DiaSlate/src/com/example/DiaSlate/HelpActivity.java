package com.example.DiaSlate;

import com.example.DiaSlate.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class HelpActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		WebView webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/slate.html");
	}
}
