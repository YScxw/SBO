package com.cxb.mobilebanking;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Welcome extends Activity {

	public ProgressBar pBMyPBar;
	public TextView TxtShowPBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);

		pBMyPBar = (ProgressBar) findViewById(R.id.progress_horizontal);
		TxtShowPBar = (TextView) findViewById(R.id.TxtShowPBar);
		// 环境界面主要完成服务器的连接
		// 【具体步骤省略，利用超时函数实现，进度条效果】
		pBMyPBar.setVisibility(View.VISIBLE);
		TxtShowPBar.setText("连接服务器...");

		mHander.post(mRunnable);

	}

	private Handler mHander = new Handler();
	public int sum = 0;
	private final Runnable mRunnable = new Runnable() {
		public void run() {
			mHander.postDelayed(mRunnable, 1000);
			
			pBMyPBar.setProgress(sum);
			pBMyPBar.setSecondaryProgress(sum);
			sum += 20;
			if (pBMyPBar.getProgress() == pBMyPBar.getMax()) {
				pBMyPBar.setVisibility(View.VISIBLE);
				TxtShowPBar.setText("连接成功");
				//成功连接符服务后，跳转到登录主机面
				 Intent intent = new Intent();
				 intent.setClass(Welcome.this, LoginActivity.class);
				 Welcome.this.startActivity(intent);
				 //销毁
				 mHander.removeCallbacks(mRunnable);
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

}
