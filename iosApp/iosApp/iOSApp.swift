import SwiftUI
import composeApp

@main
struct iOSApp: App {
    init() {
        // Initialize Koin as early as possible
        KoinKt.doInitKoinIos()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
