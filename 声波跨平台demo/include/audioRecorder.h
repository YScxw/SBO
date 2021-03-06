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

#ifdef VOICE_RECOG_DLL
#define VOICERECOGNIZEDLL_API __declspec(dllexport)
#else
#define VOICERECOGNIZEDLL_API
#endif

#ifndef AUDIO_RECORDER_H
#define AUDIO_RECORDER_H

#ifdef __cplusplus
extern "C" {
#endif

//_data的数据格式是根据initRecorder传入的数据类型定的，一般可能为short，或者是float。
//_sampleCout是表示_data中含有的样本数，不是指_data的长度
//返回已经处理的信号数，如果返回-1，则录音线程应退出
typedef int (*r_pwrite)(void *_writer, const void *_data, unsigned long _sampleCout);

/************************************************************************/
/* 创建录音机
/* _sampleRateInHz为44100
/* _channel为单声道，1为单声道，2为立体声
/* _audioFormat为一个信号的bit数，单声道为16
/************************************************************************/
VOICERECOGNIZEDLL_API int initRecorder(int _sampleRateInHz, int _channel, int _audioFormat, int _bufferSize, void **_precorder);

/************************************************************************/
/* 开始录音
/************************************************************************/
VOICERECOGNIZEDLL_API int startRecord(void *_recorder, void *_writer, r_pwrite _pwrite);

/************************************************************************/
/* 停止录音，该函数需同步返回（销毁函数是真正停止后才能释放内存）
/************************************************************************/
VOICERECOGNIZEDLL_API int stopRecord(void *_recorder);

/************************************************************************/
/* 释放录音器的资源
/************************************************************************/
VOICERECOGNIZEDLL_API int releaseRecorder(void *_recorder);

#ifdef __cplusplus
}
#endif


#endif
