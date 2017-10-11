// SBO.cpp : CSBOApp 和 DLL 注册的实现。

#include "stdafx.h"
#include "SBO.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


CSBOApp theApp;

const GUID CDECL _tlid = { 0x5231766C, 0x4B82, 0x40D7, { 0x8E, 0xA2, 0xFE, 0xDD, 0x9C, 0xFA, 0x56, 0x25 } };
const WORD _wVerMajor = 1;
const WORD _wVerMinor = 0;



// CSBOApp::InitInstance - DLL 初始化

BOOL CSBOApp::InitInstance()
{
	BOOL bInit = COleControlModule::InitInstance();

	if (bInit)
	{
		// TODO: 在此添加您自己的模块初始化代码。
	}

	return bInit;
}



// CSBOApp::ExitInstance - DLL 终止

int CSBOApp::ExitInstance()
{
	// TODO: 在此添加您自己的模块终止代码。

	return COleControlModule::ExitInstance();
}



// DllRegisterServer - 将项添加到系统注册表

STDAPI DllRegisterServer(void)
{
	AFX_MANAGE_STATE(_afxModuleAddrThis);

	if (!AfxOleRegisterTypeLib(AfxGetInstanceHandle(), _tlid))
		return ResultFromScode(SELFREG_E_TYPELIB);

	if (!COleObjectFactoryEx::UpdateRegistryAll(TRUE))
		return ResultFromScode(SELFREG_E_CLASS);

	return NOERROR;
}



// DllUnregisterServer - 将项从系统注册表中移除

STDAPI DllUnregisterServer(void)
{
	AFX_MANAGE_STATE(_afxModuleAddrThis);

	if (!AfxOleUnregisterTypeLib(_tlid, _wVerMajor, _wVerMinor))
		return ResultFromScode(SELFREG_E_TYPELIB);

	if (!COleObjectFactoryEx::UpdateRegistryAll(FALSE))
		return ResultFromScode(SELFREG_E_CLASS);

	return NOERROR;
}
