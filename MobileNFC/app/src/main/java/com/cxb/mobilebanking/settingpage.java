package com.cxb.mobilebanking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cxb.mobilebanking.decoding.InactivityTimer;

import java.net.InetSocketAddress;
import java.net.Socket;


@SuppressLint("DefaultLocale")
public class settingpage extends Activity implements Callback {
	final String TAG = "CXB Mobile Banking";
	private EditText ipEditText;
	private TextView valueViewTip;
	private Button enter;
	public  static String ip = "10.32.6.24"; // 连接P端IP
	public static int port = 5002;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        //add by yywang end 解决Android 4.0以上主界面不可以建立网络连接问题。
		// 详见StrictMode文档
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
     //end add by yywang    
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.settingpage);

		
		valueViewTip = (TextView) findViewById(R.id.valueViewTip);
		new InactivityTimer(this);
		ipEditText = (EditText) findViewById(R.id.ipEditText);


		enter = (Button) findViewById(R.id.enter);



		// 处理IP连接;
		ipEditText.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
							|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
						ip = ipEditText.getText().toString();
						if (ip.indexOf(" ") > 0) {
							ip = ip.substring(0, ip.indexOf(" "));
						}
						if (ip.equals("")) {
							valueViewTip.setText("请输入合法IP地址");
							return true;
						}
						ConnectionDetector cd = new ConnectionDetector(
								getApplicationContext());
						Boolean isInternetPresent = cd.isConnectingToInternet(); // true
																					// or
																					// false
						if (isInternetPresent.booleanValue()) {
							Socket socket = null;
							try {
								// 创建Socket
								socket = new Socket(ip, port);
								ipEditText.setText(ip + " 成功");
								valueViewTip.setText("网络设置成功.");
								socket.close();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								ipEditText.setText("失败");
								valueViewTip.setText("请检查IP地址输入是否正确");
								ip = "";
							}
						} else {
							valueViewTip.setText("请打开手机网络.");
							ip = "";
						}
						return true;
					}
				return false;
			}
		});
		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ip = ipEditText.getText().toString();
				if (ip.indexOf(" ") > 0) {
					ip = ip.substring(0, ip.indexOf(" "));
				}
				if (ip.equals("")) {
					valueViewTip.setText("请输入合法IP地址");
					return;
				}
				ConnectionDetector cd = new ConnectionDetector(
						getApplicationContext());
				Boolean isInternetPresent = cd.isConnectingToInternet(); // true
																			// or
																		// false
				if (isInternetPresent.booleanValue()) {
					Socket socket = null;
					try {
						// 创建Socket	
						
						System.out.println("Begin Socket!");
						//socket = new Socket(ip, port);
						socket = new Socket();
						//socket.setSoTimeout(5);
						socket.connect(new InetSocketAddress(ip,port),3000);
						System.out.println("End Socket!");
						ipEditText.setText(ip + " 成功");
						valueViewTip.setText("网络链接成功，您可以继续其他交易。");
						Log.i(TAG,"after setting ip is "+ ip);
						socket.close();
						//enter.setVisibility(View.INVISIBLE);
						//ipEditText.setVisibility(View.INVISIBLE);
						
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("socket error "+e);
						e.printStackTrace();
						ipEditText.setText(ip+"失败");
						valueViewTip.setText("请检查IP地址输入是否正确.");
						ip = "";
					}
				} else {
					valueViewTip.setText("检测不到网络");
					ip = "";
				}

			}
		});

	}
	public String getIP() {
		// TODO Auto-generated method stub
		Log.i(TAG,"get setting ip is "+ ip);
		return ip;
	}
	public int getPort() {
		// TODO Auto-generated method stub
		return port;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
}