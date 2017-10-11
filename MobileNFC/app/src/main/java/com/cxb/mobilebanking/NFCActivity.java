package com.cxb.mobilebanking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.Result;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@SuppressLint("DefaultLocale")
public class NFCActivity extends Activity implements Callback {
	final String TAG = "CXB Mobile Banking";
	private ImageButton imageButton1;
	private TextView textVShowText;
	private TextView textViewShowTitle;
	// private CaptureActivityHandler handler;
	// private ViewfinderView viewfinderView;
	// private SurfaceHolder surfaceHolder;
	// private boolean hasSurface;
	// private Vector<BarcodeFormat> decodeFormats;
	// private String characterSet;
	// private InactivityTimer inactivityTimer;
	// private MediaPlayer mediaPlayer;
	// private boolean playBeep;
	// private static final float BEEP_VOLUME = 0.10f;
	// private boolean vibrate;
	// private EditText ipEditText;
	// private TextView valueViewTip;
	// private ListView valueView;
	// private Button reservation;
	// private Button shake;
	// private Button enter;
	// private Button replaysound;
	// private Button phonenfc;
	// private ImageView qrImgImageView;
	// private ImageView qr_image_2;
	// private SurfaceView surfaceView;
	// private Button sweep;
	// private int operationType[] = new int[4]; // 当前操作类型和步骤号
	// private String message = ""; // 保存发送的数据
	// private String ip = ""; // 连接P端IP
	// private int port = 5002;
	//
	// private String dataToken;
	private int operationType[] = new int[4]; // 当前操作类型和步骤号
	private String strMsgToShow;
	private String strKeySend, strKeyRec ,strKey;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.phonenfc);

		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
		textVShowText = (TextView) findViewById(R.id.textVShowText);
		textViewShowTitle = (TextView) findViewById(R.id.textViewShowTitle);

		strKeySend="";
		strKeyRec = "";
		strKey="";
		
		operationType[0] = 0;
		operationType[1] = 0;
		operationType[2] = 0;
		operationType[3] = 0;// 手机NFC标示
		strMsgToShow = "";
		// 处理手机NFC
		operationType[3] = 1;// 手机NFC标示
		imageButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// mod by yywang for new activity
				// //单击手机NFC功能
				operationType[3] = 1;// 手机NFC标示
				showViewHome();
				strMsgToShow = "请将手机放于设备放于声音读卡去附近";
				textVShowText.setText(strMsgToShow);
				textViewShowTitle.setText("提示信息：");
			}
		});
	}

	private void showViewHome() {
		// 显示主页面试图
		// chirpServiceStop();
		// chirpServiceStart();

	}

	// private void showViewReservation1() {
	// // // 显示预约取款试图1
	// // showViewHome();
	// // valueView.setVisibility(View.VISIBLE);
	//
	// }

	// private void hideViewCamera() {
	// // 隐藏调用Camera试图所启用的元素
	// // viewfinderView.setVisibility(View.GONE);
	// // surfaceView.setVisibility(View.GONE);
	// }

	// private void showViewCamera() {
	// // // 显示调用Camera试图所启用的元素
	// // viewfinderView.setVisibility(View.VISIBLE);
	// // surfaceView.setVisibility(View.VISIBLE);
	// }
	//
	// private void stopStartCamera() {
	// // 停止调用二维码扫描
	// // hideViewCamera();
	// // onPause();
	// }
	//
	// private void reStartCamera() {
	// // // 调用二维码扫描
	// // stopStartCamera();
	// // showViewCamera();
	// // initCamera(surfaceHolder);
	// }

	// @Override
	// protected void onResume() {
	// // super.onResume();
	// showViewHome();
	// if (hasSurface) {
	// initCamera(surfaceHolder);
	// } else {
	// surfaceHolder.addCallback(this);
	// surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	// }
	// decodeFormats = null;
	// characterSet = null;
	//
	// playBeep = true;
	// AudioManager audioService = (AudioManager)
	// getSystemService(AUDIO_SERVICE);
	// if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
	// playBeep = false;
	// }
	// initBeepSound();
	// vibrate = true;
	// }

	// @Override
	// protected void onPause() {
	// // super.onPause();
	// // if (handler != null) {
	// // handler.quitSynchronously();
	// // handler = null;
	// // }
	// // CameraManager.get().closeDriver();
	// }
	//
	// @Override
	// protected void onDestroy() {
	// // inactivityTimer.shutdown();
	// // super.onDestroy();
	// }

	private void initCamera(SurfaceHolder surfaceHolder) {
		// try {
		// CameraManager.get().openDriver(surfaceHolder);
		// } catch (IOException ioe) {
		// return;
		// } catch (RuntimeException e) {
		// return;
		// }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// if (!hasSurface) {
		// hasSurface = true;
		// // initCamera(holder);
		// }

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// hasSurface = false;

	}

	// public ViewfinderView getViewfinderView() {
	// return viewfinderView;
	// }
	//
	// public Handler getHandler() {
	// return handler;
	// }

	public void drawViewfinder() {
		// viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		// inactivityTimer.onActivity();
		// hideViewCamera();
		// viewfinderView.drawResultBitmap(barcode);
		// playBeepSoundAndVibrate();
		// valueViewTip.setText("已采集到设备信息，系统正在为您处理");
		// message = "tmsg2" + obj.getText();
		// Socket socket = null;
		// try {
		// // 创建Socket
		// socket = new Socket(ip, port); // 查看本机IP,每次开机都不同
		// // socket=new Socket("192.168.1.110",50000);
		// // 向服务器发送消息
		// PrintWriter out = new PrintWriter(new BufferedWriter(
		// new OutputStreamWriter(socket.getOutputStream())), true);
		// out.println(message);
		//
		// // 接收来自服务器的消息
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// socket.getInputStream()));
		// String msg = br.readLine();
		// if (msg != null) {
		// if (msg.equals("20")) {
		// valueViewTip.setText("处理成功，请到ATM继续操作");
		// } else if (msg.equals("21")) {
		// valueViewTip.setText("预约失败，请先在ATM上进行预约操作");
		// } else {
		// valueViewTip.setText("处理异常，请联系发卡行");
		// }
		// } else {
		// valueViewTip.setText("处理失败，请联系发卡行");
		// }
		// // 关闭流
		// out.close();
		// br.close();
		// // 关闭Socket
		// socket.close();
		// } catch (Exception e) {
		// // TODO: handle exception
		// valueViewTip.setText(e.toString());
		// e.printStackTrace();
		// }
	}

	private void initBeepSound() {
		// if (playBeep && mediaPlayer == null) {
		// // The volume on STREAM_SYSTEM is not adjustable, and users found it
		// // too loud,
		// // so we now play on the music stream.
		// setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// mediaPlayer = new MediaPlayer();
		// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		// mediaPlayer.setOnCompletionListener(beepListener);
		//
		// AssetFileDescriptor file = getResources().openRawResourceFd(
		// R.raw.beep);
		// try {
		// mediaPlayer.setDataSource(file.getFileDescriptor(),
		// file.getStartOffset(), file.getLength());
		// file.close();
		// mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
		// mediaPlayer.prepare();
		// } catch (IOException e) {
		// mediaPlayer = null;
		// }
		// }
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		// if (playBeep && mediaPlayer != null) {
		// mediaPlayer.start();
		// }
		// if (vibrate) {
		// Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		// vibrator.vibrate(VIBRATE_DURATION);
		// }
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

	// @Override
	// public void onRecv(byte[] data)
	// {
	// String message = new String(data);
	// Log.i(TAG, "onRecv:"+message);
	// Toast.makeText(this, "���ճɹ�", Toast.LENGTH_LONG).show();
	// txtMessage.setText(message);
	// }


	public void onRecv(byte[] byteReciveData) {
		// 转换获取到的数据byte[] to string
		String dataToken = null;
		try {
			dataToken = new String(byteReciveData, "GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Log.i(TAG,"Get Shengbo Value"+dataToken);
		if (operationType[3] == 1) {
			String strGetValue;
			strGetValue = dataToken;
			if (strGetValue.substring(0,1).equals("1")&&strGetValue.length()>4 ) {
				strMsgToShow = strMsgToShow + "\n匹配成功。";
				textVShowText.setText(strMsgToShow);
				//strKeySend="";	strKeyRec = "";	strKey="";
				
				strKeyRec=dataToken.substring(1,5);
				Log.i(TAG,"get key "+strKeyRec);
				Random ranKey=new Random();
				int intKeySend=ranKey.nextInt();
				strKeySend=intKeySend+"0000";
				strKeySend=strKeySend.substring(0, 4);
				Log.i(TAG,"send key "+strKeySend);
				//key use to do something
				String strSend;
				strSend = "2"+strKeyRec+strKeySend;	
				strKey=strKeyRec+strKeySend;
				Log.i(TAG, "send to wsap:" + "data:" + strSend);
				byte[] data = strSend.getBytes();
				MediaPlayer mymediaPlayer;
				mymediaPlayer = MediaPlayer.create(NFCActivity.this, R.raw.shengbo);
				mymediaPlayer.start();
				operationType[3] = 2;
				strMsgToShow = "数据交换中...";
				textVShowText.setText(strMsgToShow);
			} else {
				textVShowText.setText("匹配错误，请重试！");
			}

		} else if (operationType[3] == 2) {
			String strGetValue;
			strGetValue = dataToken.substring(0, 1);
			if (strGetValue.equals("3")) {
				// 获取到设备的读卡命令，返回设备20000000
				
				String strSend;
				//strSend = "4"+strKey+"655362772=0123456789";	
				strSend = "4"+"62269655362772=0123456789";	
				Log.i(TAG, "send to wsap:" + "data:" + strSend);
				byte[] data = strSend.getBytes();
				MediaPlayer mymediaPlayer;
				mymediaPlayer = MediaPlayer.create(NFCActivity.this, R.raw.shengbo);
				mymediaPlayer.start();
				operationType[3] = 2;
				return ;
			}
			else if (strGetValue.equals("5")) {			
				strMsgToShow ="匹配成功，请在ATM上操作";
				textVShowText.setText(strMsgToShow);
			}
			else if (strGetValue.equals("1")) {
				strKeyRec=dataToken.substring(1,5);
				Log.i(TAG,"get key "+strKeyRec);
				//Random ranKey=new Random();
				//int intKeySend=ranKey.nextInt();
				int intKeySend=1234; 
				strKeySend=intKeySend+"0000";
				strKeySend=strKeySend.substring(0, 4);
				Log.i(TAG,"send key "+strKeySend);
				//key use to do something
				String strSend;
				strSend = "2"+strKeyRec+strKeySend;	
				strKey=strKeyRec+strKeySend;
				Log.i(TAG, "send to wsap:" + "data:" + strSend);
				byte[] data = strSend.getBytes();
				MediaPlayer mymediaPlayer;
				mymediaPlayer = MediaPlayer.create(NFCActivity.this, R.raw.shengbo);
				mymediaPlayer.start();
				operationType[3] = 2;
			}
		}
//			
//			strMsgToShow = strMsgToShow + "\n数据交换中。。。";
//			textVShowText.setText(strMsgToShow);
//
//			strGetValue = dataToken.substring(0, 1);
//			if (Integer.parseInt(strGetValue) == 3) {
//				// 获取到设备的读卡命令，返回设备20000000
//				Toast.makeText(this, "开始发送", Toast.LENGTH_LONG).show();
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
//				String strSend;
//				strSend = "200000000";
//				strSend = strSend + chirpService.get2121(strSend);
//				Log.i(TAG, "send to wsap:" + "data:" + strSend);
//				byte[] data = strSend.getBytes();
//				xququerService.sendData(data, 0.5f);
//			} else if (Integer.parseInt(strGetValue) == 5) {
//				// 获取到设备的读卡命令，返回设备20000000
//				String strSend;
//				strSend = "6" + "53637772";
//				strSend = strSend + chirpService.get2121(strSend);
//				Log.i(TAG, "send to wsap:" + "data:" + strSend);
//				byte[] data = strSend.getBytes();
//				xququerService.sendData(data, 0.5f);
//				strMsgToShow = strMsgToShow + "\n操作成功";
//				textVShowText.setText(strMsgToShow);
//			} else {
//				textVShowText.setText(dataToken + "交换数据错误");
//				operationType[3] = 1;
//			}
//		} else {
//			String strGetValue;
//			strGetValue = dataToken.substring(0, 9);
//			if (strGetValue.equals("100000000")) {
//				// 获取到设备的读卡命令，返回设备20000000
//				strMsgToShow = strMsgToShow + "\n匹配成功。";
//				textVShowText.setText(strMsgToShow);
//				String strSend;
//				strSend = "200000000";
//				strSend = strSend + chirpService.get2121(strSend);
//				Log.i(TAG, "send to wsap:" + "data:" + strSend);
//				byte[] data = strSend.getBytes();
//				xququerService.sendData(data, 0.5f);
//				operationType[3] = 2;
//			} 
//			strGetValue = dataToken.substring(0, 1);
//			if (Integer.parseInt(strGetValue) == 3) {
//				// 获取到设备的读卡命令，返回设备20000000
//				Toast.makeText(this, "开始发送", Toast.LENGTH_LONG).show();
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String strSend;
//				strSend = "4" + "62260965";
//				strSend = strSend + chirpService.get2121(strSend);
//				Log.i(TAG, "send to wsap:" + "data:" + strSend);
//				byte[] data = strSend.getBytes();
//				xququerService.sendData(data, 0.5f);
//			} else if (Integer.parseInt(strGetValue) == 5) {
//				// 获取到设备的读卡命令，返回设备20000000
//				Toast.makeText(this, "开始发送", Toast.LENGTH_LONG).show();
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				String strSend;
//				strSend = "6" + "53637772";
//				strSend = strSend + chirpService.get2121(strSend);
//				Log.i(TAG, "send to wsap:" + "data:" + strSend);
//				byte[] data = strSend.getBytes();
//				xququerService.sendData(data, 0.5f);
//				strMsgToShow = strMsgToShow + "\n操作成功";
//				textVShowText.setText(strMsgToShow);
//			} else {
//				textVShowText.setText(dataToken + "交换数据错误");
//				operationType[3] = 1;
//			}
//			Log.i(TAG, "onRecv:" + "无效数据:" + dataToken);
//			Toast.makeText(this, "无效数据:" + dataToken, Toast.LENGTH_LONG).show();
			return;
		}


	// private void chirpServiceStart() {
	// //chirpService.start(this);
	// Log.i(TAG, "chirpServiceStart");
	// }
	//
	// private void chirpServiceStop() {
	// //chirpService.stop();
	// Log.i(TAG, "chirpServiceStop");
	// }
	//
	// @Override
	// protected void onStop() {
	// super.onStop();
	// chirpServiceStop();
	// }

	public static String bytesToHexString(byte[] src) {
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