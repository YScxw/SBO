package com.cxb.mobilebanking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxb.mobilebanking.camera.CameraManager;
import com.cxb.mobilebanking.decoding.CaptureActivityHandler;
import com.cxb.mobilebanking.decoding.InactivityTimer;
import com.cxb.mobilebanking.encoding.EncodingHandler;
import com.cxb.mobilebanking.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.WriterException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
@SuppressLint("DefaultLocale")
public class ListenQK extends Activity implements Callback{
	final String TAG = "CXB Mobile Banking";
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private SurfaceHolder surfaceHolder;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private EditText ipEditText;
	private TextView valueViewTip;
	private ListView valueView;
	private Button reservation;
	private Button shake;
	private Button enter;
	private Button replaysound;
	private Button phonenfc;
	private ImageView qrImgImageView;
	private ImageView qr_image_2;
	private SurfaceView surfaceView;
	private Button sweep;
	private int operationType[] = new int[4]; // 当前操作类型和步骤号
	private String message = ""; // 保存发送的数据
	private String ip = ""; // 连接P端IP
	private int port = 5002;

	private String dataToken;
	
	private String strMsgToShow;
	private settingpage clsSettingpage;
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
		setContentView(R.layout.activity_listen_qk);

		Log.i(TAG, "activity_listen_qk begin");
		
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		valueViewTip = (TextView) findViewById(R.id.valueViewTip);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		ipEditText = (EditText) findViewById(R.id.ipEditText);
		valueView = (ListView) findViewById(R.id.valueView);
		reservation = (Button) findViewById(R.id.reservation);
		shake = (Button) findViewById(R.id.shake);
		phonenfc =(Button)findViewById(R.id.phonenfc);
		enter = (Button) findViewById(R.id.enter);
		replaysound = (Button) findViewById(R.id.replaysound);
		qrImgImageView = (ImageView) findViewById(R.id.qr_image);
		qr_image_2 = (ImageView) findViewById(R.id.qr_image_2);
		sweep = (Button) findViewById(R.id.sweep);
		surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		surfaceHolder = surfaceView.getHolder();
		operationType[0] = 0;
		operationType[1] = 0;
		operationType[2] = 0;
		operationType[3] = 0;//手机NFC标示
		
		

//		chirpServiceStart();
		clsSettingpage = new settingpage();
		ip=clsSettingpage.getIP();
		port=clsSettingpage.getPort();
		
		Log.i(TAG,"get setting ip is "+ ip);
		Log.i(TAG,"get setting port is "+ port);
		

		// 处理IP连接;
		ipEditText.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
							|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
						//ip = ipEditText.getText().toString();
						if (ip.indexOf(" ") > 0) {
							ip = ip.substring(0, ip.indexOf(" "));
						}
						if (ip.equals("")) {
							valueViewTip.setText("请首先在'系统设置'中进行设置");
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
								valueViewTip.setText("网络链接成功.");
								socket.close();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								ipEditText.setText("失败"+e.getMessage());
								valueViewTip.setText("请首先在'系统设置'中进行设置");
								ip = "";
							}
						} else {
							valueViewTip.setText("请首先在'系统设置'中进行设置");
							ip = "";
						}
						showViewHome();
						reStartCamera();
						stopStartCamera();
						return true;
					}
				return false;
			}
		});
		enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//ip = ipEditText.getText().toString();
				if (ip.indexOf(" ") > 0) {
					ip = ip.substring(0, ip.indexOf(" "));
				}
				if (ip.equals("")) {
					valueViewTip.setText("请首先在'系统设置'中进行设置");
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
						socket = new Socket(ip, port);
						System.out.println("End Socket!");
						ipEditText.setText(ip + " 成功");
						valueViewTip.setText("网络链接成功.");
						socket.close();
						enter.setVisibility(View.INVISIBLE);
						ipEditText.setVisibility(View.INVISIBLE);
						
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("socket error "+e);
						e.printStackTrace();
						ipEditText.setText("失败"+e.getMessage());
						valueViewTip.setText("请首先在'系统设置'中进行设置");
						ip = "";
					}
				} else {
					valueViewTip.setText("请打开网络链接.");
					ip = "";
				}
				showViewHome();
				reStartCamera();
				stopStartCamera();
			}
		});

		// 处理预约取款
		final ArrayList<String> valueItems = new ArrayList<String>();
		final ArrayAdapter<String> aa;
		aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, valueItems);
		valueView.setAdapter(aa);

		reservation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				operationType[0] = 1;
				operationType[1] = 0;
				operationType[2] = 0;
				operationType[3] = 0;
				message = "";
				showViewHome();
				if (ip.equals("")) {
					valueViewTip.setText("请首先在'系统设置'中进行设置");
					return;
				}
				stopStartCamera();
				showViewReservation1();
				valueViewTip.setText("请选择预约取款账号！");
				valueItems.clear();
				valueItems.add(0, "62212300000000");
				valueItems.add(1, "62245600000000");
				valueItems.add(2, "62278900000000");
				aa.notifyDataSetChanged();
			}
		});
		valueView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (operationType[0] == 1) {
					operationType[0] = 2;
					message = "tmsg1" + valueItems.get(arg2);
					valueViewTip.setText("账号" + valueItems.get(arg2) + "\n"
							+ "请选择或输入预约金额");
					valueItems.clear();
					valueItems.add(0, "100");
					valueItems.add(1, "200");
					valueItems.add(2, "300");
					valueItems.add(3, "500");
					valueItems.add(4, "800");
					valueItems.add(5, "1000");
					aa.notifyDataSetChanged();
				} else if (operationType[0] == 2) {
					valueViewTip.setText("交易正在处理");
					message += valueItems.get(arg2);
					valueItems.clear();
					aa.notifyDataSetChanged();
					Socket socket = null;
					try {
						// 创建Socket
						socket = new Socket(ip, port); // 查看本机IP,每次开机都不同
						// socket=new Socket("192.168.1.110",50000);
						// 向服务器发送消息
						PrintWriter out = new PrintWriter(
								new BufferedWriter(new OutputStreamWriter(
										socket.getOutputStream())), true);
						out.println(message);

						// 接收来自服务器的消息
						BufferedReader br = new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
						String msg = br.readLine();
						if (msg != null) {
							valueViewTip.setText("处理成功，预约码为" + msg
									+ "\n点击二维码图片可放大");
							Bitmap qrCodeBitmap = null;
							Bitmap qrCodeBitmap2 = null;
							try {
								qrCodeBitmap = EncodingHandler.createQRCode(
										msg, 150);
								qrCodeBitmap2 = EncodingHandler.createQRCode(
										msg, 480);
							} catch (WriterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							qrImgImageView.setVisibility(View.VISIBLE);
							replaysound.setVisibility(View.VISIBLE);
							qrImgImageView.setImageBitmap(qrCodeBitmap);
							qr_image_2.setImageBitmap(qrCodeBitmap2);
						} else {
							valueViewTip.setText("连接异常");
						}
						// 关闭流
						out.close();
						br.close();
						// 关闭Socket
						socket.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						valueViewTip.setText(e.toString());
					}
				}

			}
		});
		
		replaysound.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				valueViewTip.setText("正在收听...\n请在ATM上进行操作");
			}
		});
		
		qrImgImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				qr_image_2.setVisibility(View.VISIBLE);
			}
		});
		qr_image_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				qr_image_2.setVisibility(View.GONE);
			}
		});
		// 处理摇一摇取款

		shake.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				operationType[0] = 0;
				operationType[1] = 1;
				operationType[2] = 0;
				operationType[3] = 0;
				showViewHome();
				if (ip.equals("")) {
					valueViewTip.setText("请先设置IP地址！");
					return;
				}
				stopStartCamera();
				valueViewTip.setText("暂不支持");
			}
		});
		//处理手机NFC		
		
		phonenfc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//mod by yywang for new activity
//				operationType[0] = 0;
//				operationType[1] = 0;
//				operationType[2] = 0;
//				operationType[3] = 1;
//				//单击手机NFC功能
//				strMsgToShow="请将手机放于设备放于声音读卡去附近";
//				valueViewTip.setText(strMsgToShow);
				
				 Intent intent = new Intent();               
				 intent.setClass(ListenQK.this, SelectPage.class);               
				 ListenQK.this.startActivity(intent);
				//end add 
			}
		});
		// 处理扫一扫取款
		sweep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				operationType[0] = 0;
				operationType[1] = 0;
				operationType[2] = 1;
				operationType[3] = 0;
				if (ip.equals("")) {
					valueViewTip.setText("请先设置IP地址！");
					return;
				}
				try {
					// 创建Socket	
					Socket socket =null;
					socket = new Socket();
					//socket.setSoTimeout(5);
					socket.connect(new InetSocketAddress(ip,port),3000);
					socket.close();
					//enter.setVisibility(View.INVISIBLE);
					//ipEditText.setVisibility(View.INVISIBLE);
					
				} catch (Exception e) {
					valueViewTip.setText("网络异常，请首先在'系统设置'中进行设置");
					return;
				}
				valueViewTip.setText("正在收听...");
				valueItems.clear();
				aa.notifyDataSetChanged();
				replaysound.setVisibility(View.VISIBLE);
				//showViewHome();
				//reStartCamera();
			}
		});
	}

	private void showViewHome() {
		// 显示主页面试图
//		chirpServiceStop();
//		chirpServiceStart();
		valueView.setVisibility(View.GONE);
		qrImgImageView.setVisibility(View.GONE);
		qr_image_2.setVisibility(View.GONE);
		viewfinderView.setVisibility(View.GONE);
		replaysound.setVisibility(View.GONE);
		// surfaceView.setVisibility(View.GONE);
	}

	private void showViewReservation1() {
		// 显示预约取款试图1
		showViewHome();
		valueView.setVisibility(View.VISIBLE);

	}

	private void hideViewCamera() {
		// 隐藏调用Camera试图所启用的元素
		viewfinderView.setVisibility(View.GONE);
		surfaceView.setVisibility(View.GONE);
	}

	private void showViewCamera() {
		// 显示调用Camera试图所启用的元素
		viewfinderView.setVisibility(View.VISIBLE);
		surfaceView.setVisibility(View.VISIBLE);
	}

	private void stopStartCamera() {
		// 停止调用二维码扫描
		hideViewCamera();
		onPause();
	}

	private void reStartCamera() {
		// 调用二维码扫描
		stopStartCamera();
		showViewCamera();
		initCamera(surfaceHolder);
	}

	@Override
	protected void onResume() {
		super.onResume();
		showViewHome();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			// initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		hideViewCamera();
		viewfinderView.drawResultBitmap(barcode);
		playBeepSoundAndVibrate();
		valueViewTip.setText("已采集到设备信息，系统正在为您处理");
		message = "tmsg2" + obj.getText();
		Socket socket = null;
		try {
			// 创建Socket
			socket = new Socket(ip, port); // 查看本机IP,每次开机都不同
			// socket=new Socket("192.168.1.110",50000);
			// 向服务器发送消息
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(message);

			// 接收来自服务器的消息
			BufferedReader br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String msg = br.readLine();
			if (msg != null) {
				if (msg.equals("20")) {
					valueViewTip.setText("处理成功，请到ATM继续操作");
				} else if (msg.equals("21")) {
					valueViewTip.setText("预约失败，请先在ATM上进行预约操作");
				} else {
					valueViewTip.setText("处理异常，请联系发卡行");
				}
			} else {
				valueViewTip.setText("处理失败，请联系发卡行");
			}
			// 关闭流
			out.close();
			br.close();
			// 关闭Socket
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			valueViewTip.setText(e.toString());
			e.printStackTrace();
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override
	protected void onStart() {
		super.onStart();

		Log.i(TAG, "onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
	}

	private void send() {
		String strToSend;
		strToSend = "1234567890";
		byte[] data = strToSend.toString().getBytes();
	}


	public void onRecv(byte[] byteReciveData) {
		// 转换获取到的数据byte[] to string
		String dataToken = null;
		try {
			dataToken = new String(byteReciveData, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i(TAG, "onRecv:" + dataToken);
		if (operationType[2] == 1 && dataToken.length()>7 ) {
			Log.i(TAG, "operationType[2] == 1");
			Log.i(TAG, "onRecv:" + dataToken);

			//Toast.makeText(this, "Received successfully", Toast.LENGTH_LONG).show();
			if (true) {
				
				valueViewTip.setText("已采集到设备信息，系统正在为您处理");
				stopStartCamera();
				message = "tmsg2" + dataToken.substring(0, 8);
				Socket socket = null;
				try {
					// 创建Socket
					socket = new Socket(ip, port); // 查看本机IP,每次开机都不同
					// socket=new Socket("192.168.1.110",50000);
					// 向服务器发送消息
					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream())), true);
					out.println(message);

					// 接收来自服务器的消息
					BufferedReader br = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					String msg = br.readLine();
					if (msg != null) {
						if (msg.equals("20")) {
							valueViewTip.setText("处理成功，请到ATM继续操作");
						} else if (msg.equals("21")) {
							valueViewTip.setText("预约失败，请先在ATM上进行预约操作");
						} else {
							valueViewTip.setText("处理异常，请联系发卡行");
						}
					} else {
						valueViewTip.setText("处理失败，请联系发卡行");
					}
					// 关闭流
					out.close();
					br.close();
					// 关闭Socket
					socket.close();
				} catch (Exception e) {
					// TODO: handle exception
					valueViewTip.setText(e.toString());
					e.printStackTrace();
				}
			}
		}
		else {
			Log.i(TAG, "onRecv:" + "接收到不需要处理的数据，不处理");
			Toast.makeText(this, "接收到不需要处理的数据，不处理", Toast.LENGTH_LONG).show();
			return;
		}
	}

//	private void chirpServiceStart() {
//		chirpService.start(this);
//		Log.i(TAG, "chirpServiceStart");
//	}

//	private void chirpServiceStop() {
//		chirpService.stop();
//		Log.i(TAG, "chirpServiceStop");
//	}
	

	
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
}