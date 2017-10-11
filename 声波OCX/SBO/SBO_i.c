

/* this ALWAYS GENERATED file contains the IIDs and CLSIDs */

/* link this file in with the server and any clients */


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


#ifdef __cplusplus
extern "C"{
#endif 


#include <rpc.h>
#include <rpcndr.h>

#ifdef _MIDL_USE_GUIDDEF_

#ifndef INITGUID
#define INITGUID
#include <guiddef.h>
#undef INITGUID
#else
#include <guiddef.h>
#endif

#define MIDL_DEFINE_GUID(type,name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8) \
        DEFINE_GUID(name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8)

#else // !_MIDL_USE_GUIDDEF_

#ifndef __IID_DEFINED__
#define __IID_DEFINED__

typedef struct _IID
{
    unsigned long x;
    unsigned short s1;
    unsigned short s2;
    unsigned char  c[8];
} IID;

#endif // __IID_DEFINED__

#ifndef CLSID_DEFINED
#define CLSID_DEFINED
typedef IID CLSID;
#endif // CLSID_DEFINED

#define MIDL_DEFINE_GUID(type,name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8) \
        const type name = {l,w1,w2,{b1,b2,b3,b4,b5,b6,b7,b8}}

#endif !_MIDL_USE_GUIDDEF_

MIDL_DEFINE_GUID(IID, LIBID_SBOLib,0x5231766C,0x4B82,0x40D7,0x8E,0xA2,0xFE,0xDD,0x9C,0xFA,0x56,0x25);


MIDL_DEFINE_GUID(IID, DIID__DSBO,0xFD73CF57,0x9A76,0x47AC,0xBE,0x96,0x07,0xAC,0xE7,0x71,0x45,0x0C);


MIDL_DEFINE_GUID(IID, DIID__DSBOEvents,0x61789752,0xB8A7,0x4C1E,0x92,0xBF,0xC9,0xD1,0xC1,0x23,0x5B,0xBA);


MIDL_DEFINE_GUID(CLSID, CLSID_SBO,0xB908D0E2,0x38C8,0x44AB,0xA9,0x34,0xD5,0x2B,0xF4,0x29,0xD0,0x75);

#undef MIDL_DEFINE_GUID

#ifdef __cplusplus
}
#endif



