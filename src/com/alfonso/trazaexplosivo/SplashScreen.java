package com.alfonso.trazaexplosivo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class SplashScreen extends Activity {

	// Set the duration of the splash screen
	private static final long SPLASH_SCREEN_DELAY = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set portrait orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		// Hide title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_splash_screen);

		ImageView im = (ImageView) findViewById(R.id.splash);

		im.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActividadPrincipal();
			}
		});

//		TimerTask task = new TimerTask() {
//			@Override
//			public void run() {
//				ActividadPrincipal();
//			}
//		};
//
//		// Simulate a long loading process on application startup.
//		Timer timer = new Timer();
//		timer.schedule(task, SPLASH_SCREEN_DELAY);
//
	}

	void ActividadPrincipal() {
		// Start the next activity
		Intent mainIntent = new Intent().setClass(SplashScreen.this, Logon.class);
//		Intent mainIntent = new Intent().setClass(SplashScreen.this, MainTrazaExplosivo.class);
		startActivity(mainIntent);

		// Close the activity so the user won't able to go back this
		// activity pressing Back button
		finish();

	}

}
