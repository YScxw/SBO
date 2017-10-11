/************************************************************************ 
android/iphone/windows/linux声波通讯库
声波通讯库特征： 
准确性95%以上，其实一般是不会出错的。 
接口非常简单，有完整的示例，3分钟就可以让你的应用增加声波通讯功能 
抗干扰性强，基本上无论外界怎么干扰，信号都是准确的 
基本的编码为16进制，而通过编码可传输任何字符 
性能非常强，没有运行不了的平台，而且通过内存池优化，长时间解码不再分配新内存，可7*24小时运行 
可支持任何平台，常见的平台android, iphone, windows, linux, arm, mipsel都有示例 
详情可查看：http://blog.csdn.net/softlgh 
作者: 夜行侠 QQ:3116009971 邮件：3116009971@qq.com 
************************************************************************/  

package com.example.voicetest;

import voice.SSIDWiFiInfo;
import voice.decoder.DataDecoder;
import voice.decoder.VoiceRecognizer;
import voice.decoder.VoiceRecognizerListener;
import voice.encoder.DataEncoder;
import voice.encoder.VoicePlayer;
import voice.encoder.VoicePlayerListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    static {
        System.loadLibrary("voiceRecog");
    }

    private final static int MSG_RECG_TEXT = 1;
    private final static int MSG_RECG_START = 2;
    private final static int MSG_PLAY_START = 3;
    private final static int MSG_PLAY_END = 4;
    private final static int MSG_PLAY_STEP = 5;
    class MyHandler extends Handler
    {
        private TextView mRecognisedTextView;
        private TextView playButtonTips;
        public MyHandler(TextView recogTextView, TextView _playTips) {
            mRecognisedTextView = recogTextView;
            playButtonTips = _playTips;
        }
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == MSG_RECG_TEXT)
            {
                String s = (String)msg.obj;
                mRecognisedTextView.setText(s);
                if(s != null && s.length() > 0)
                {
	                toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
	                Toast toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
	                toast.show();
                }
            }
            else if(msg.what == MSG_RECG_START)
            {
            	mRecognisedTextView.setText("声波识别中......");
            }
            else if(msg.what == MSG_PLAY_START)
            {
            	playButtonTips.setText("播放中......");
            }
            else if(msg.what == MSG_PLAY_STEP)
            {
            	String s = (String)msg.obj;
            	playButtonTips.setText("播放中" + s + "......");
            }
            else if(msg.what == MSG_PLAY_END)
            {
            	playButtonTips.setText("");
            }
            super.handleMessage(msg);
        }
    }

    private static String TAG = "mainActivity";
    Handler handler;
    VoiceRecognizer recognizer;//声波识别器
    VoicePlayer player;//声波播放器
    ToneGenerator toneGenerator =
            new ToneGenerator(
                    AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        int[] freqs = new int[19];
        int baseFreq = 4000;
        for(int i = 0; i < freqs.length; i ++)
        {
        	freqs[i] = baseFreq + i * 150;
        }
		
        player = new VoicePlayer();
        player.setFreqs(freqs);
        player.setListener(new VoicePlayerListener(){
			@Override
			public void onPlayEnd(VoicePlayer arg0) {
				handler.sendMessage(handler.obtainMessage(MSG_PLAY_END));
				//recognizer.pause(0);//如果是双向通讯，则恢复识别
			}

			@Override
			public void onPlayStart(VoicePlayer arg0) {
				handler.sendMessage(handler.obtainMessage(MSG_PLAY_START));
				//recognizer.pause(10000);//如果是双向通讯，为了避免自己识别到自己播放的信号，在这里暂停识别器
			}
        });
        
        //创建声波识别器
        recognizer = new VoiceRecognizer(16000);
        recognizer.setFreqs(freqs);
        recognizer.setListener(new VoiceRecognizerListener() {
            @Override
            public void onRecognizeStart(float _soundTime) {
            	handler.sendMessage(handler.obtainMessage(MSG_RECG_START));
            }

            @Override
            public void onRecognizeEnd(float _soundTime, int _result, String _hexData) {
            	String data = "";
                if(_result == 0)
                {
                	byte[] hexData = _hexData.getBytes();
                	int infoType = DataDecoder.decodeInfoType(hexData);
                	if(infoType == DataDecoder.IT_STRING)
                	{
                		data = DataDecoder.decodeString(_result, hexData);
                	}
                	else if(infoType == DataDecoder.IT_SSID_WIFI)
                	{
                		SSIDWiFiInfo wifi = DataDecoder.decodeSSIDWiFi(_result,  hexData);
                		data = "ssid:" + wifi.ssid + ",pwd:" + wifi.pwd; 
                	}
                	else
                	{
                		data = "未知数据";
                	}
                }
                handler.sendMessage(handler.obtainMessage(MSG_RECG_TEXT, data));               
            }
        });
        final TextView palyTextTips = ((TextView) findViewById(R.id.playTextTips));
        ((EditText) findViewById(R.id.playText)).addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				palyTextTips.setText("播放内容：\n(" + s.toString().length() + ")");
			}
        	
        });
        handler = new MyHandler((TextView) findViewById(R.id.recognizeText), (TextView) findViewById(R.id.playButtonTips));

        ((Button) findViewById(R.id.voicePlay)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	String data = ((EditText)findViewById(R.id.playText)).getText().toString();
            	String encodeData = DataEncoder.encodeString(data);
            	Log.i(TAG, data + " encode to :" + encodeData);
                player.play(encodeData);
            	//player.play(DataEncoder.encodeMacWiFi(new byte[]{(byte)0xff, 0x0e, 0x01, 0x02, 0x03, 0x04}, "0123456789012345678901234567890123456789"));
            }
        });
        
        autoSetAudioVolumn();

        //开始识别
        recognizer.start();
	}
	
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    
    //把音量设为60%
    public void autoSetAudioVolumn()
    {
        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)(max*0.6), 0);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        recognizer.stop();//停止识别
    }

    @Override
    protected void onResume() {
        super.onResume();
        recognizer.start();//重新启动识别
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
