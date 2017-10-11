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

#import "ViewController.h"
#include "voiceRecog.h"

#import <AVFoundation/AVFoundation.h>

//根据错误编号，获得错误信息，该函数不是必需的
const char *recorderRecogErrorMsg(int _recogStatus)
{
	char *r = (char *)"unknow error";
	switch(_recogStatus)
	{
        case VD_ECCError:
            r = (char *)"ecc error";
            break;
        case VD_NotEnoughSignal:
            r = (char *)"not enough signal";
            break;
        case VD_NotHeaderOrTail:
            r = (char *)"signal no header or tail";
            break;
        case VD_RecogCountZero:
            r = (char *)"trial has expires, please try again";
            break;
	}
	return r;
}

//重载VoiceDecoder，主要是实现onRecognizerStart，onRecognizerEnd
@interface MyVoiceRecog : VoiceRecog
{
    ViewController *ui;
}

- (id)init:(ViewController *)_ui vdpriority:(VDPriority)_vdpriority;
@end

@implementation MyVoiceRecog

- (id)init:(ViewController *)_ui vdpriority:(VDPriority)_vdpriority
{
    id r = [super init:_vdpriority];
    if (r != nil) {
        ui = _ui;
    }
    return r;
}

- (void) onRecognizerStart
{
    [ui onRecognizerStart];
}

- (void) onRecognizerEnd:(int)_result data:(char *)_data dataLen:(int)_dataLen
{
    [ui onRecognizerEnd:_result data:_data dataLen:_dataLen];
}

@end

@implementation ViewController

- (void)viewDidLoad
{
    [super viewDidLoad];

    AVAudioSession *mySession = [AVAudioSession sharedInstance];
    [mySession setCategory:AVAudioSessionCategoryPlayAndRecord withOptions:AVAudioSessionCategoryOptionDefaultToSpeaker error:nil];
    
    int base = 4000;
    for (int i = 0; i < sizeof(freqs)/sizeof(int); i ++) {
        freqs[i] = base + i *150;
    }
        
    recog = [[MyVoiceRecog alloc] init:self vdpriority:VD_MemoryUsePriority];
    [recog setFreqs:freqs freqCount:sizeof(freqs)/sizeof(int)];
    player=[[VoicePlayer alloc] init];
    [player setFreqs:freqs freqCount:sizeof(freqs)/sizeof(int)];
}

- (void) viewWillAppear:(BOOL)animated
{
    NSLog(@"viewVillAppear");
    [recog start];
    [super viewWillAppear:animated];
}

- (void) viewWillDisappear:(BOOL)animated
{
    NSLog(@"viewWillDisappear");
    [recog stop];
    [super viewWillDisappear:animated];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

- (IBAction)startPlayAction:(id)sender {
    NSString *text = [_playText text];
    text = [text stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceCharacterSet]];
    if ([text length] == 0) {
        return;
    }
    
    [player playString:text playCount:1 muteInterval:0];
}

- (void) onRecognizerStart
{
    printf("------------------recognize start\n");
}

- (void) showRecogResult:(NSString *)_msg
{
    UIAlertView *helloworldAlert = [[UIAlertView alloc] initWithTitle:@"recognize result" message:_msg delegate:nil cancelButtonTitle:@"ok" otherButtonTitles:nil, nil];
    [helloworldAlert show];
}

- (void) onRecognizerEnd:(int)_result data:(char *)_data dataLen:(int)_dataLen
{
    NSString *title = nil, *msg = nil;
    struct SSIDWiFiInfo wifi;
	struct WiFiInfo macWifi;
	int i;
	enum InfoType it;
	struct PhoneInfo phone;
	char s[100];
    if (_result == VD_SUCCESS)
	{
		printf("------------------recognized data:%s\n", _data);
        //title = @"recognize ok";
		enum InfoType infoType = vr_decodeInfoType(_data, _dataLen);
		if(infoType == IT_PHONE)
		{
			vr_decodePhone(_result, _data, _dataLen, &phone);
			printf("imei:%s, phoneName:%s", phone.imei, phone.phoneName);
            msg = [NSString stringWithFormat:@"recognized imei:%s, %s", phone.imei, phone.phoneName];
		}
		else if(infoType == IT_SSID_WIFI)
		{
			vr_decodeSSIDWiFi(_result, _data, _dataLen, &wifi);
			printf("ssid:%s, pwd:%s\n", wifi.ssid, wifi.pwd);
            msg = [NSString stringWithFormat:@"recognized ssid wifi:%s, %s", wifi.ssid, wifi.pwd];
		}
		else if(infoType == IT_STRING)
		{
			vr_decodeString(_result, _data, _dataLen, s, sizeof(s));
			printf("string:%s\n", s);
            msg = [NSString stringWithFormat:@"recognized string:%s", s];
		}
		else if(infoType == IT_WIFI)
		{
            char mac[30];
            memset(mac, 0, sizeof(mac));
			vr_decodeWiFi(_result, _data, _dataLen, &macWifi);
			printf("mac wifi:");
			for (i = 0; i < macWifi.macLen; i ++)
			{
				printf("0x%.2x ", macWifi.mac[i] & 0xff);
                sprintf(mac+ strlen(mac), "0x%.2x ", macWifi.mac[i] & 0xff);
			}
			printf(", %s\n", macWifi.pwd);
            msg = [NSString stringWithFormat:@"recognized mac wifi:%s, %s", mac, macWifi.pwd];
		}
		else
		{
			printf("------------------recognized data:%s\n", _data);
            msg = [NSString stringWithFormat:@"recognized data:%s", _data];
		}
	}
	else
	{
		printf("------------------recognize invalid data, errorCode:%d, error:%s\n", _result, recorderRecogErrorMsg(_result));
	}
    if(msg != nil)[self performSelectorOnMainThread:@selector(showRecogResult:) withObject:msg waitUntilDone:NO];
}


@end
