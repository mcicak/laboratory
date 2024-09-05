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
    
    @State private var appModel = AppModel()
    @StateObject private var uiModel = UIModel()
    
    var sharedModelContainer: ModelContainer = {
        let schema = Schema([
            Contact.self,
            Chat.self,
            AppUser.self
        ])
        let modelConfiguration = ModelConfiguration(schema: schema, isStoredInMemoryOnly: false)

        do {
            return try ModelContainer(for: schema, configurations: [modelConfiguration])
        } catch {
            fatalError("Could not create ModelContainer: \(error)")
        }
    }()
    
    init() {
        printAppDirectory()
        
        let userFetch = FetchDescriptor<AppUser>()
        let chatsFetch = FetchDescriptor<Chat>()
        let contactsFetch = FetchDescriptor<Contact>()
        
        do {
            let users = try sharedModelContainer.mainContext.fetch(userFetch)
            let chats = try sharedModelContainer.mainContext.fetch(chatsFetch)
            let contacts = try sharedModelContainer.mainContext.fetch(contactsFetch)
            
            if let user = users.first {
                appModel.context.user = user
                appModel.chats = chats
                appModel.contacts = contacts
                return
            }
        } catch {
            print("Error fetching chats: \(error)")
        }
        
        let chat1 = Chat(name: "C1")
        let chat2 = Chat(name: "C2")
        
        let contact1 = Contact(name: "Ali Baba 1", phoneNumber: "+381637364533")
        let contact2 = Contact(name: "Ali Baba 2", phoneNumber: "+381637364533")
        
        sharedModelContainer.mainContext.insert(chat1)
        sharedModelContainer.mainContext.insert(chat2)
        sharedModelContainer.mainContext.insert(contact1)
        sharedModelContainer.mainContext.insert(contact2)
        
        do{
            try sharedModelContainer.mainContext.save()
            appModel.chats = [chat1, chat2]
            appModel.contacts = [contact1, contact2]
        }catch{
            print("Error: \(error.localizedDescription)")
        }
    }
    
    
    var body: some Scene {
        WindowGroup {
            RootView()
                .modelContainer(sharedModelContainer)
                .environmentObject(uiModel)
                .environment(appModel)
                .environment(\.apiService, ApiServiceMock())
        }
    }
    
    private func printAppDirectory() {
        let paths = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)
        if let documentsDirectory = paths.first {
            print(documentsDirectory)
        }
    }
}
