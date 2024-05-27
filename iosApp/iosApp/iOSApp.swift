import SwiftUI
import Firebase
import ComposeApp

@main
struct iOSApp: App {
    
    init(){
        FirebaseApp.configure()
        DataModuleKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
