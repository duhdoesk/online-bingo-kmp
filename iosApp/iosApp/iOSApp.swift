import SwiftUI
import Firebase
import ComposeApp
import GoogleSignIn
import FirebaseCore

@main
struct iOSApp: App {
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    init(){
        FirebaseApp.configure()
        InitKoinKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView().ignoresSafeArea(.all)
		}
	}
    
}

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, 
                     open url: URL,
                     options: [UIApplication.OpenURLOptionsKey : Any] = [:],
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        print(url)
        return true
    }
}
