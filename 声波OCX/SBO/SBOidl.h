

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


 /* File created by MIDL compiler version 7.00.0555 */
/* at Wed May 31 10:20:21 2017
 */
/* Compiler settings for SBO.idl:
    Oicf, W1, Zp8, env=Win32 (32b run), target_arch=X86 7.00.0555 
    protocol : dce , ms_ext, c_ext, robust
    error checks: allocation ref bounds_check enum stub_data 
    VC __declspec() decoration level: 
         __declspec(uuid()), __declspec(selectany), __declspec(novtable)
         DECLSPEC_UUID(), MIDL_INTERFACE()
*/
/* @@MIDL_FILE_HEADING(  ) */

#pragma warning( disable: 4049 )  /* more than 64k source lines */


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 475
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__


#ifndef __SBOidl_h__
#define __SBOidl_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef ___DSBO_FWD_DEFINED__
#define ___DSBO_FWD_DEFINED__
typedef interface _DSBO _DSBO;
#endif 	/* ___DSBO_FWD_DEFINED__ */


#ifndef ___DSBOEvents_FWD_DEFINED__
#define ___DSBOEvents_FWD_DEFINED__
typedef interface _DSBOEvents _DSBOEvents;
#endif 	/* ___DSBOEvents_FWD_DEFINED__ */


#ifndef __SBO_FWD_DEFINED__
#define __SBO_FWD_DEFINED__

#ifdef __cplusplus
typedef class SBO SBO;
#else
typedef struct SBO SBO;
#endif /* __cplusplus */

#endif 	/* __SBO_FWD_DEFINED__ */


#ifdef __cplusplus
extern "C"{
#endif 



#ifndef __SBOLib_LIBRARY_DEFINED__
#define __SBOLib_LIBRARY_DEFINED__

/* library SBOLib */
/* [control][version][uuid] */ 


EXTERN_C const IID LIBID_SBOLib;

#ifndef ___DSBO_DISPINTERFACE_DEFINED__
#define ___DSBO_DISPINTERFACE_DEFINED__

/* dispinterface _DSBO */
/* [uuid] */ 


EXTERN_C const IID DIID__DSBO;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("FD73CF57-9A76-47AC-BE96-07ACE771450C")
    _DSBO : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _DSBOVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            _DSBO * This,
            /* [in] */ REFIID riid,
            /* [annotation][iid_is][out] */ 
            __RPC__deref_out  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            _DSBO * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            _DSBO * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            _DSBO * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            _DSBO * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            _DSBO * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            _DSBO * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        END_INTERFACE
    } _DSBOVtbl;

    interface _DSBO
    {
        CONST_VTBL struct _DSBOVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _DSBO_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define _DSBO_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define _DSBO_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define _DSBO_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define _DSBO_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define _DSBO_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define _DSBO_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___DSBO_DISPINTERFACE_DEFINED__ */


#ifndef ___DSBOEvents_DISPINTERFACE_DEFINED__
#define ___DSBOEvents_DISPINTERFACE_DEFINED__

/* dispinterface _DSBOEvents */
/* [uuid] */ 


EXTERN_C const IID DIID__DSBOEvents;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("61789752-B8A7-4C1E-92BF-C9D1C1235BBA")
    _DSBOEvents : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _DSBOEventsVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            _DSBOEvents * This,
            /* [in] */ REFIID riid,
            /* [annotation][iid_is][out] */ 
            __RPC__deref_out  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            _DSBOEvents * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            _DSBOEvents * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            _DSBOEvents * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            _DSBOEvents * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            _DSBOEvents * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            _DSBOEvents * This,
            /* [in] */ DISPID dispIdMember,
            /* [in] */ REFIID riid,
            /* [in] */ LCID lcid,
            /* [in] */ WORD wFlags,
            /* [out][in] */ DISPPARAMS *pDispParams,
            /* [out] */ VARIANT *pVarResult,
            /* [out] */ EXCEPINFO *pExcepInfo,
            /* [out] */ UINT *puArgErr);
        
        END_INTERFACE
    } _DSBOEventsVtbl;

    interface _DSBOEvents
    {
        CONST_VTBL struct _DSBOEventsVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _DSBOEvents_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define _DSBOEvents_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define _DSBOEvents_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define _DSBOEvents_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define _DSBOEvents_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define _DSBOEvents_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define _DSBOEvents_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___DSBOEvents_DISPINTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_SBO;

#ifdef __cplusplus

class DECLSPEC_UUID("B908D0E2-38C8-44AB-A934-D52BF429D075")
SBO;
#endif
#endif /* __SBOLib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


