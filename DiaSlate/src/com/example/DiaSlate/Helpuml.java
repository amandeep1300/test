package com.example.DiaSlate;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Helpuml extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		WebView webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/Uml_help.html");
	}

}
