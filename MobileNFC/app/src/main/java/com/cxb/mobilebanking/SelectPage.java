package com.cxb.mobilebanking;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class SelectPage extends Activity {

	private Button BtnEWM;
	private Button BtnPhoneNFC;
	private Button BtnSBO;
	private Button BtnListen;
	private Button btnSetting;
	final String TAG = "CXB Mobile Banking";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_select_page);

		// 定义控件
		BtnEWM = (Button) findViewById(R.id.btnEWM);
		BtnPhoneNFC = (Button) findViewById(R.id.BtnMNFC);
		BtnSBO = (Button) findViewById(R.id.BtnSBo);
		BtnListen = (Button)findViewById(R.id.BtnListen);
		btnSetting = (Button)findViewById(R.id.BtnSetting); 

		//设置按钮
		btnSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.i(TAG, "BtnSetting on click");
				Intent intent = new Intent();
				intent.setClass(SelectPage.this, settingpage.class);
				SelectPage.this.startActivity(intent);
			}
		});
		
		// 二维码按钮
		BtnEWM.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SelectPage.this, CaptureActivity.class);
				SelectPage.this.startActivity(intent);
			}
		});

		// 手机NFC按钮
		BtnPhoneNFC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SelectPage.this, NFCActivity.class);
				SelectPage.this.startActivity(intent);

			}
		});

		// 声波无卡取款
		BtnSBO.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SelectPage.this, SBo.class);
				SelectPage.this.startActivity(intent);

			}
		});
		//BtnListen
		BtnListen.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SelectPage.this, ListenQK.class);
				SelectPage.this.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_page, menu);
		return true;
	}

}
