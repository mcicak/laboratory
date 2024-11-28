import SwiftUI
import Firebase
import GoogleSignIn
import FirebaseMessaging
import ComposeApp

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    func application(_ application: UIApplication,
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()
                        
        Task {
            do {
                let result = try await UNUserNotificationCenter.current().requestAuthorization(options:[.alert, .badge, .sound])
                print("APPROVED: \(result)")
            } catch {
                print("Error requesting notification")
            }
        }
        
        UNUserNotificationCenter.current().delegate = self
                
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
        var handled: Bool
        
        handled = GIDSignIn.sharedInstance.handle(url)
        if handled {
            return true
        }
        
        return false
    }
    
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }
    
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any]) async -> UIBackgroundFetchResult {
        print("didReceiveRemoteNotification: \(userInfo)")
        NotifierManager.shared.onApplicationDidReceiveRemoteNotification(userInfo: userInfo)
        return .newData
    }
    
    // MARK: UNUserNotificationCenterDelegate
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification) async -> UNNotificationPresentationOptions {
        [ .banner, .badge, .sound ]
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse) async {
        let productId = "apple_mac_128k"
        
        if let rootVC = UIApplication.shared.windows.first?.rootViewController {
            let vc = MainViewControllerKt.ProductDetailsViewController(productId: productId, parent: rootVC)
            let nc = UINavigationController(rootViewController: vc)
            rootVC.present(nc, animated: true)
        }
    }
}

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onOpenURL(perform: { url in
                    GIDSignIn.sharedInstance.handle(url)
                })
        }
    }
}
