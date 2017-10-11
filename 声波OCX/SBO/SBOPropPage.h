#pragma once

// SBOPropPage.h : CSBOPropPage 属性页类的声明。


// CSBOPropPage : 有关实现的信息，请参阅 SBOPropPage.cpp。

class CSBOPropPage : public COlePropertyPage
{
	DECLARE_DYNCREATE(CSBOPropPage)
	DECLARE_OLECREATE_EX(CSBOPropPage)

// 构造函数
public:
	CSBOPropPage();

// 对话框数据
	enum { IDD = IDD_PROPPAGE_SBO };

// 实现
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

// 消息映射
protected:
	DECLARE_MESSAGE_MAP()
};

