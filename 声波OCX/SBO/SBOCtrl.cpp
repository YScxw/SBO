// SBOCtrl.cpp : CSBOCtrl ActiveX 控件类的实现。

#include "stdafx.h"
#include "SBO.h"
#include "SBOCtrl.h"
#include "SBOPropPage.h"
#include "afxdialogex.h"
 
#include <comutil.h>
#pragma comment(lib, "comsuppw.lib")  
#pragma comment(lib,"comsuppwd.lib")  

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


IMPLEMENT_DYNCREATE(CSBOCtrl, COleControl)



// 消息映射

BEGIN_MESSAGE_MAP(CSBOCtrl, COleControl)
	ON_OLEVERB(AFX_IDS_VERB_PROPERTIES, OnProperties)
	ON_MESSAGE(WM_GetVoiceData, &CSBOCtrl::OnGetvoicedata)
END_MESSAGE_MAP()



// 调度映射

BEGIN_DISPATCH_MAP(CSBOCtrl, COleControl)
	DISP_FUNCTION_ID(CSBOCtrl, "AboutBox", DISPID_ABOUTBOX, AboutBox, VT_EMPTY, VTS_NONE)
	DISP_FUNCTION_ID(CSBOCtrl, "OpenRecorder", dispidOpenRecorder, OpenRecorder, VT_EMPTY, VTS_NONE)
	DISP_FUNCTION_ID(CSBOCtrl, "PlayVoice", dispidPlayVoice, PlayVoice, VT_EMPTY, VTS_BSTR)
	DISP_FUNCTION_ID(CSBOCtrl, "OpenVoicePlayer", dispidOpenVoicePlayer, OpenVoicePlayer, VT_EMPTY, VTS_NONE)
	DISP_FUNCTION_ID(CSBOCtrl, "CloseVoicePlayer", dispidCloseVoicePlayer, CloseVoicePlayer, VT_EMPTY, VTS_NONE)
	DISP_FUNCTION_ID(CSBOCtrl, "CloseRecorder", dispidCloseRecorder, CloseRecorder, VT_EMPTY, VTS_NONE)
END_DISPATCH_MAP()



// 事件映射

BEGIN_EVENT_MAP(CSBOCtrl, COleControl)
	EVENT_CUSTOM_ID("OnGetVoiceData", eventidOnGetVoiceData, OnGetVoiceData, VTS_BSTR)
	EVENT_CUSTOM_ID("OnGetLog", eventidOnGetLog, OnGetLog, VTS_BSTR)
END_EVENT_MAP()



// 属性页

// TODO: 按需要添加更多属性页。请记住增加计数!
BEGIN_PROPPAGEIDS(CSBOCtrl, 1)
	PROPPAGEID(CSBOPropPage::guid)
END_PROPPAGEIDS(CSBOCtrl)



// 初始化类工厂和 guid

IMPLEMENT_OLECREATE_EX(CSBOCtrl, "SBO.SBOCtrl.1",
	0xb908d0e2, 0x38c8, 0x44ab, 0xa9, 0x34, 0xd5, 0x2b, 0xf4, 0x29, 0xd0, 0x75)



// 键入库 ID 和版本

IMPLEMENT_OLETYPELIB(CSBOCtrl, _tlid, _wVerMajor, _wVerMinor)



// 接口 ID

const IID IID_DSBO = { 0xFD73CF57, 0x9A76, 0x47AC, { 0xBE, 0x96, 0x7, 0xAC, 0xE7, 0x71, 0x45, 0xC } };
const IID IID_DSBOEvents = { 0x61789752, 0xB8A7, 0x4C1E, { 0x92, 0xBF, 0xC9, 0xD1, 0xC1, 0x23, 0x5B, 0xBA } };


// 控件类型信息

static const DWORD _dwSBOOleMisc =
	OLEMISC_ACTIVATEWHENVISIBLE |
	OLEMISC_SETCLIENTSITEFIRST |
	OLEMISC_INSIDEOUT |
	OLEMISC_CANTLINKINSIDE |
	OLEMISC_RECOMPOSEONRESIZE;

IMPLEMENT_OLECTLTYPE(CSBOCtrl, IDS_SBO, _dwSBOOleMisc)



// CSBOCtrl::CSBOCtrlFactory::UpdateRegistry -
// 添加或移除 CSBOCtrl 的系统注册表项

BOOL CSBOCtrl::CSBOCtrlFactory::UpdateRegistry(BOOL bRegister)
{
	// TODO: 验证您的控件是否符合单元模型线程处理规则。
	// 有关更多信息，请参考 MFC 技术说明 64。
	// 如果您的控件不符合单元模型规则，则
	// 必须修改如下代码，将第六个参数从
	// afxRegApartmentThreading 改为 0。

	if (bRegister)
		return AfxOleRegisterControlClass(
			AfxGetInstanceHandle(),
			m_clsid,
			m_lpszProgID,
			IDS_SBO,
			IDB_SBO,
			afxRegApartmentThreading,
			_dwSBOOleMisc,
			_tlid,
			_wVerMajor,
			_wVerMinor);
	else
		return AfxOleUnregisterClass(m_clsid, m_lpszProgID);
}



// CSBOCtrl::CSBOCtrl - 构造函数

CSBOCtrl::CSBOCtrl()
{
	InitializeIIDs(&IID_DSBO, &IID_DSBOEvents);
	// TODO: 在此初始化控件的实例数据。
	testVoice=new CTestVoice();
	testVoice->SBOCtrl=this;
	recoderFlag=FALSE;
	playerFlag=FALSE;
}



// CSBOCtrl::~CSBOCtrl - 析构函数

CSBOCtrl::~CSBOCtrl()
{
	// TODO: 在此清理控件的实例数据。
	if(testVoice!=NULL){
		testVoice=NULL;
	}
}



// CSBOCtrl::OnDraw - 绘图函数

void CSBOCtrl::OnDraw(
			CDC* pdc, const CRect& rcBounds, const CRect& rcInvalid)
{
	if (!pdc)
		return;

	// TODO: 用您自己的绘图代码替换下面的代码。
	pdc->FillRect(rcBounds, CBrush::FromHandle((HBRUSH)GetStockObject(WHITE_BRUSH)));
	pdc->Ellipse(rcBounds);
}



// CSBOCtrl::DoPropExchange - 持久性支持

void CSBOCtrl::DoPropExchange(CPropExchange* pPX)
{
	ExchangeVersion(pPX, MAKELONG(_wVerMinor, _wVerMajor));
	COleControl::DoPropExchange(pPX);

	// TODO: 为每个持久的自定义属性调用 PX_ 函数。
}



// CSBOCtrl::OnResetState - 将控件重置为默认状态

void CSBOCtrl::OnResetState()
{
	COleControl::OnResetState();  // 重置 DoPropExchange 中找到的默认值

	// TODO: 在此重置任意其他控件状态。
}



// CSBOCtrl::AboutBox - 向用户显示“关于”框

void CSBOCtrl::AboutBox()
{
	CDialogEx dlgAbout(IDD_ABOUTBOX_SBO);
	dlgAbout.DoModal();
}




void CSBOCtrl::OpenRecorder(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());
	if(recoderFlag)
	{
		CString abc("已开启录音机");
		OnGetLog(abc.AllocSysString());
		return;
	}
	else
	{
		recoderFlag=TRUE;
		testVoice->init();
	}
	// TODO: 在此添加调度处理程序代码
}



void CSBOCtrl::PlayVoice(BSTR data)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	// TODO: 在此添加调度处理程序代码
	if(!playerFlag)
	{
		CString abc("未开启播放器");
		OnGetLog(abc.AllocSysString());
		return;
	}
	else
	{
		char* lpszText2 = _com_util::ConvertBSTRToString(data); 
		testVoice->playVoice(lpszText2);
		SysFreeString(data);
		delete[] lpszText2;
	}
}


void CSBOCtrl::OpenVoicePlayer(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());
	// TODO: 在此添加调度处理程序代码
	if(playerFlag)
	{
		CString abc("已开启播放器");
		OnGetLog(abc.AllocSysString());
		return;
	}
	else
	{
		playerFlag=TRUE;
		testVoice->OpenVoicePlayer();
	}
}


void CSBOCtrl::CloseVoicePlayer(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	// TODO: 在此添加调度处理程序代码
	if(!playerFlag)
	{
		CString abc("未开启播放器");
		OnGetLog(abc.AllocSysString());
		return;
	}
	else
	{
		playerFlag=FALSE;
		testVoice->CloseVoicePlayer();
	}
}


afx_msg LRESULT CSBOCtrl::OnGetvoicedata(WPARAM wParam, LPARAM lParam)
{
	BSTR data=(BSTR)lParam;
	OnGetVoiceData(data);
	SysFreeString(data);
	return 0;
}


void CSBOCtrl::CloseRecorder(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());
	if(!recoderFlag)
	{
		CString abc("未开启录音机");
		OnGetLog(abc.AllocSysString());
		return;
	}
	else
	{
		testVoice->destory();
		recoderFlag=FALSE;
	}
	// TODO: 在此添加调度处理程序代码
}
