#pragma once

// SBOCtrl.h : CSBOCtrl ActiveX 控件类的声明。

#include "testVoice.h"
// CSBOCtrl : 有关实现的信息，请参阅 SBOCtrl.cpp。
class CTestVoice;
class CSBOCtrl : public COleControl
{
	DECLARE_DYNCREATE(CSBOCtrl)

// 构造函数
public:
	CSBOCtrl();

// 重写
public:
	virtual void OnDraw(CDC* pdc, const CRect& rcBounds, const CRect& rcInvalid);
	virtual void DoPropExchange(CPropExchange* pPX);
	virtual void OnResetState();

// 实现
protected:
	~CSBOCtrl();

	DECLARE_OLECREATE_EX(CSBOCtrl)    // 类工厂和 guid
	DECLARE_OLETYPELIB(CSBOCtrl)      // GetTypeInfo
	DECLARE_PROPPAGEIDS(CSBOCtrl)     // 属性页 ID
	DECLARE_OLECTLTYPE(CSBOCtrl)		// 类型名称和杂项状态

// 消息映射
	DECLARE_MESSAGE_MAP()

// 调度映射
	DECLARE_DISPATCH_MAP()

	afx_msg void AboutBox();

// 事件映射
	DECLARE_EVENT_MAP()

// 调度和事件 ID
public:
	CTestVoice *testVoice;
	int recoderFlag;
	int playerFlag;
public:
	void OpenRecorder(void);

	enum 
	{
		eventidOnGetLog = 2L,
		dispidCloseRecorder = 6L,
		eventidASDFSA = 2L,
		dispidCloseVoicePlayer = 5L,
		dispidOpenVoicePlayer = 4L,
		dispidPlayVoice = 3L,
		eventidOnGetVoiceData = 1L,
		dispidOpenRecorder = 1L
	};

	void OnGetVoiceData(BSTR data)
	{
		FireEvent(eventidOnGetVoiceData, EVENT_PARAM(VTS_BSTR), data);
	}
	void OnGetLog(BSTR data)
	{
		FireEvent(eventidOnGetLog, EVENT_PARAM(VTS_BSTR),data);
	}
	void PlayVoice(BSTR data);
	void OpenVoicePlayer(void);
	void CloseVoicePlayer(void);
protected:
	afx_msg LRESULT OnGetvoicedata(WPARAM wParam, LPARAM lParam);
	void CloseRecorder(void);
};

