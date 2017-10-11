#pragma once

#include "resource.h"
#include "SBOCtrl.h"

void WaitForVoiceData(LPVOID lpParam);
class CSBOCtrl;
class CTestVoice
{
	// 构造函数
public:
	CTestVoice();
	// 实现
protected:
	~CTestVoice();
public:
	void init();
	void destory();
	void playVoice(char* hwnd);
	void OpenVoicePlayer();
	void CloseVoicePlayer();
	void SetData(CString data);
public:
	CSBOCtrl* SBOCtrl;
	void *player ;
	void *recognizer ;
	void *recorder ;
};