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

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, VDPriority)
{
    VD_CPUUsePriority//不占内存，但CPU消耗比较大一些
    , VD_MemoryUsePriority//不占CPU，但内存消耗大一些
};

typedef NS_ENUM(NSInteger, VDErrorCode)
{
    VD_SUCCESS = 0, VD_NoSignal = -1, VD_ECCError = -2, VD_NotEnoughSignal = 100
    , VD_NotHeaderOrTail = 101, VD_RecogCountZero = 102
};

@interface VoiceRecog : NSObject
{
    void *recognizer;
    void *recorder;
    NSThread *recogThread;
}

- (id)init:(VDPriority)_vdpriority;

- (void) start;
- (void) stop;
- (void) pause:(int)_ms;
- (bool) isStopped;

//- (void) sendWave:(NSString *)_wavePath;

- (void) setFreqs:(int *)_freqs freqCount:(int)_freqCount;

- (void) onRecognizerStart;
- (void) onRecognizerEnd:(int)_result data:(char *)_data dataLen:(int)_dataLen;

+ (int) infoType:(char *)_data dataLen:(int)_dataLen;

@end
