// SBOPropPage.cpp : CSBOPropPage 属性页类的实现。

#include "stdafx.h"
#include "SBO.h"
#include "SBOPropPage.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


IMPLEMENT_DYNCREATE(CSBOPropPage, COlePropertyPage)



// 消息映射

BEGIN_MESSAGE_MAP(CSBOPropPage, COlePropertyPage)
END_MESSAGE_MAP()



// 初始化类工厂和 guid

IMPLEMENT_OLECREATE_EX(CSBOPropPage, "SBO.SBOPropPage.1",
	0xc8ac0c45, 0xd542, 0x4e1d, 0x96, 0xbe, 0x7d, 0x76, 0x70, 0x4a, 0xea, 0xb5)



// CSBOPropPage::CSBOPropPageFactory::UpdateRegistry -
// 添加或移除 CSBOPropPage 的系统注册表项

BOOL CSBOPropPage::CSBOPropPageFactory::UpdateRegistry(BOOL bRegister)
{
	if (bRegister)
		return AfxOleRegisterPropertyPageClass(AfxGetInstanceHandle(),
			m_clsid, IDS_SBO_PPG);
	else
		return AfxOleUnregisterClass(m_clsid, NULL);
}



// CSBOPropPage::CSBOPropPage - 构造函数

CSBOPropPage::CSBOPropPage() :
	COlePropertyPage(IDD, IDS_SBO_PPG_CAPTION)
{
}



// CSBOPropPage::DoDataExchange - 在页和属性间移动数据

void CSBOPropPage::DoDataExchange(CDataExchange* pDX)
{
	DDP_PostProcessing(pDX);
}



// CSBOPropPage 消息处理程序
