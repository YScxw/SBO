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

#import "AppDelegate.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    // Override point for customization after application launch.
    return YES;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
