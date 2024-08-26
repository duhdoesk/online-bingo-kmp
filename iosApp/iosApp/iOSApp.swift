import SwiftUI
import Firebase
import ComposeApp
import GoogleSignIn
import FirebaseCore

@main
struct iOSApp: App {
    
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
