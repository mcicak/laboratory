//
//  MailAppApp.swift
//  MailApp
//
//  Created by Marko Cicak on 16.8.24..
//

import SwiftUI
import SwiftData

@main
struct MailApp: App {
    
    @StateObject private var appModel: AppModel
    
    init() {
        let modelContext = sharedModelContainer.mainContext
        _appModel = StateObject(wrappedValue: AppModel(modelContext: modelContext))
    }
    
    var sharedModelContainer: ModelContainer = {
        let schema = Schema([
            DBMail.self,
        ])
        let modelConfiguration = ModelConfiguration(schema: schema, isStoredInMemoryOnly: false)

        do {
            return try ModelContainer(for: schema, configurations: [modelConfiguration])
        } catch {
            fatalError("Could not create ModelContainer: \(error)")
        }
    }()

    var body: some Scene {
        WindowGroup {
            ContentView()
                .onAppear {
                    printAppDirectory()
                    initializeDataIfNeeded()
                    if let inbox = appModel.folders.first(where: { $0.name == "Inbox" }) {
                        appModel.mainMenuSelection = MainMenu.folder(inbox.id)
                    }
                }
        }
        .modelContainer(sharedModelContainer)
        .environmentObject(appModel)
    }
    
    private func printAppDirectory() {
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
        if let documentsDirectory = paths.first {
            print(documentsDirectory)
        }
    }
    
    // Function to check if the database is empty and insert initial data
    @MainActor private func initializeDataIfNeeded() {
        // Fetch DBFolder entities
        let fetchDescriptor = FetchDescriptor<DBFolder>()

        do {
            let folders = try sharedModelContainer.mainContext.fetch(fetchDescriptor)
            
            // If there are no folders, insert initial data
            if folders.isEmpty {
                insertInitialData()
                appModel.fetchFolders()
            }
        } catch {
            print("Error fetching folders: \(error)")
        }
    }
    
    // Function to insert initial data
    @MainActor private func insertInitialData() {
        // Create a folder
        let inbox = DBFolder(id: UUID(), name: "Inbox")
        let sent = DBFolder(id: UUID(), name: "Sent")

        // Create mails and assign them to the folder
        let mail1 = DBMail(subject: "Welcome!", date: Date(), from: "sender@example.com", to: "recipient@example.com", body: "This is your first email.", folder: inbox)
        let mail2 = DBMail(subject: "Hello!", date: Date(), from: "friend@example.com", to: "recipient@example.com", body: "How are you?", folder: inbox)
        let mail3 = DBMail(subject: "Submition", date: Date(), from: "sender@example.com", to: "recipient@example.com", body: "This is your first email.", folder: sent)
        
        // Save the folder and mails to the database
        do {
            sharedModelContainer.mainContext.insert(inbox)
            sharedModelContainer.mainContext.insert(sent)
            sharedModelContainer.mainContext.insert(mail1)
            sharedModelContainer.mainContext.insert(mail2)
            sharedModelContainer.mainContext.insert(mail3)
            try sharedModelContainer.mainContext.save()
            print("Mails and folder saved successfully.")
        } catch {
            print("Error saving data: \(error)")
        }
    }
}
