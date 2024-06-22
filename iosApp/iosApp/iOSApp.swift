import SwiftUI
import Firebase
import ComposeApp

@main
struct iOSApp: App {
    
    init(){
        FirebaseApp.configure()
        InitKoinKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
