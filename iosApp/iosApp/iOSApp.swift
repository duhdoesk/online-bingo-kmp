import SwiftUI
import Firebase

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
