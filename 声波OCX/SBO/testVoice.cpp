#include "stdafx.h"
#include "testVoice.h"

#include "CmnHdr.h" 
#include <Windowsx.h>

#include "voicePlay.h"
#include "voiceRecog.h"
#include "audioRecorder.h"

#include <stdio.h>
#include <assert.h>

#pragma comment(lib, "cvoiceRecognizeLib.lib")

#define M_SEND_TEXT    (WM_USER + 100)

CString resultData;
HANDLE handle=CreateEvent(NULL, TRUE, FALSE, NULL);

CTestVoice::CTestVoice()
{
	player=NULL;
	recognizer=NULL;
	recorder=NULL;
}

CTestVoice::~CTestVoice()
{
	player=NULL;
	recognizer=NULL;
	recorder=NULL;
	SBOCtrl=NULL;
}

//识别开始的回调函数
void recorderRecognizerStart(void *_listener, float _soundTime)
{
}

//识别结束的回调函数
void recorderRecognizerEnd(void *_listener, float _soundTime, int _recogStatus, char *_data, int _dataLen)
{
	if (_recogStatus == VR_SUCCESS)
	{				
		char result[100];
		int infoType = vr_decodeInfoType(_data, _dataLen);
		if(infoType == IT_STRING)
		{
			vr_decodeString(_recogStatus, _data, _dataLen, result, sizeof(result));
		}
		else if(infoType == IT_SSID_WIFI)//ssid+pwd的wifi
		{
			SSIDWiFiInfo wifi;
			vr_decodeSSIDWiFi(_recogStatus, _data, _dataLen, &wifi);
			sprintf_s(result, sizeof(result), "ssid:%s,pwd:%s", wifi.ssid, wifi.pwd);
		}
		else if(infoType == IT_WIFI)//mac+pwd的wifi
		{
			WiFiInfo macWifi;
			int i;
			vr_decodeWiFi(_recogStatus, _data, _dataLen, &macWifi);
			sprintf(result, "mac wifi:");
			for (i = 0; i < macWifi.macLen; i ++)
			{
				sprintf(result+ strlen(result), "0x%.2x ", macWifi.mac[i] & 0xff);
			}
			sprintf(result+ strlen(result), ", %s\n", macWifi.pwd);
		}
		if(infoType == IT_STRING || infoType == IT_SSID_WIFI || infoType == IT_WIFI)
		{
			resultData=result;
			SetEvent(handle);
		}
	}
	else
	{
		//printf("------------------recognize invalid data, errorCode:%d, error:%s\n", _recogStatus, recorderRecogErrorMsg(_recogStatus));
	}
}

//播放开始的回调
void onPlayStart(void *_listener)
{
}

//播放结束的回调
void onPlayEnd(void *_listener)
{
}

//运行识别器
void runRecorderVoiceRecognize( void * _recognizer)  
{
	vr_runRecognizer(_recognizer);
}

//录音机回调函数
int recorderShortWrite(void *_writer, const void *_data, unsigned long _sampleCout)
{
	char *data = (char *)_data;
	void *recognizer = _writer;

	return vr_writeData(recognizer, data, ((int)_sampleCout) * 2);
}

//int playFreqs[] = {6500,6700,6900,7100,7300,7500,7700,7900,8100,8300,8500,8700,8900,9100,9300,9500,9700,9900,10100};
int playFreqs[] = {4000,4150,4300,4450,4600,4750,4900,5050,5200,5350,5500,5650,5800,5950,6100,6250,6400,6550,6700};
void CTestVoice::init()
{


	//创建识别器，并设置监听器
	recognizer = vr_createVoiceRecognizer(MemoryUsePriority);
	int r;

	vr_setRecognizeFreqs(recognizer, playFreqs, sizeof(playFreqs)/sizeof(int));
	vr_setRecognizerListener(recognizer, NULL, recorderRecognizerStart, recorderRecognizerEnd);//设置识别器回调函数
	//创建录音机	
	r = initRecorder(44100, 1, 16, 512, &recorder);//要求录取short数据
	if(r != 0)
	{
		printf("recorder init error:%d", r);
		return;
	}
	//开始录音
	r = startRecord(recorder, recognizer, recorderShortWrite);//short数据
	if(r != 0)
	{
		printf("recorder record error:%d", r);
		return;
	}
	//开始识别
	_beginthread(runRecorderVoiceRecognize, 0, recognizer);
	_beginthread(WaitForVoiceData, 0, this);

	CString abc("recognize start");
	SetData(abc);

}

void WaitForVoiceData(LPVOID lpParam)
{
	CTestVoice* pParent = (CTestVoice*)lpParam;
 	if (pParent == NULL)
 	{
 		return;
 	}

	while(true)
	{
		ResetEvent(handle);
		WaitForSingleObject(handle, INFINITE);
		::PostMessage(pParent->SBOCtrl->m_hWnd,WM_GetVoiceData,NULL,(LPARAM)resultData.AllocSysString());
	}
}


void CTestVoice::destory()
{
	//停止录音
	int r = stopRecord(recorder);
	if(r != 0)
	{
	}
	r = releaseRecorder(recorder);
	if(r != 0)
	{
	}

	//通知识别器停止，并等待识别器真正退出
	vr_stopRecognize(recognizer);
	do 
	{	
		CString abc("recognizer is quiting");
		SetData(abc);
		Sleep(100);
	} while (!vr_isRecognizerStopped(recognizer));

	//销毁识别器
	vr_destroyVoiceRecognizer(recognizer);
}

void CTestVoice::SetData(CString data)
{
	SBOCtrl->OnGetLog(data.AllocSysString());
}

void CTestVoice::playVoice(char* hwnd)
{
	char msg[100];
	strcpy(msg,hwnd);
	msg[strlen(hwnd)]=0;
	vp_playString(player, msg, 1, 0);
}

void CTestVoice::OpenVoicePlayer()
{
	//创建声波播放器
	player = vp_createVoicePlayer();
	vp_setFreqs(player, playFreqs, sizeof(playFreqs)/sizeof(int));
	vp_setPlayerListener(player, NULL, onPlayStart, onPlayEnd);//设置播放器回调函数
	CString abc("VoicePlayer Start");
	SetData(abc);
}

void CTestVoice::CloseVoicePlayer()
{
	vp_destoryVoicePlayer(player);
	CString abc("VoicePlayer is quiting");
	SetData(abc);
}