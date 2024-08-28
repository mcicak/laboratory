//
//  ChatAppApp.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import SwiftUI
import SwiftData

@main
struct ChatApp: App {
    
    @StateObject private var appModel: AppModel
    
    var sharedModelContainer: ModelContainer = {
        let schema = Schema([
            Item.self,
        ])
        let modelConfiguration = ModelConfiguration(schema: schema, isStoredInMemoryOnly: false)

        do {
            return try ModelContainer(for: schema, configurations: [modelConfiguration])
        } catch {
            fatalError("Could not create ModelContainer: \(error)")
        }
    }()
    
    init() {
        let modelContext = sharedModelContainer.mainContext
        _appModel = StateObject(wrappedValue: AppModel(modelContext: modelContext))
    }

    var body: some Scene {
        WindowGroup {
            RootView()
        }
        .modelContainer(sharedModelContainer)
        .environmentObject(appModel)
    }
}
