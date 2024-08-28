//
//  TabView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct MyTabView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        NavigationStack(path: $appModel.mainPath) {
            TabView(selection: $appModel.selectedTab) {
                ChatsView()
                    .tabItem {
                        Label("Chats", systemImage: "message")
                    }
                    .tag(MainTab.chats)
                
                ContactsView()
                    .tabItem {
                        Label("Contacts", systemImage: "person.2")
                    }
                    .tag(MainTab.contacts)
                
                MyProfileView()
                    .tabItem {
                        Label("My Profile", systemImage: "person.crop.circle")
                    }
                    .tag(MainTab.profile)
            }
            .navigationTitle(appModel.selectedTab.title)
            .navigationDestination(for: AnyHashable.self) { entity in
                if let chat = entity as? Chat {
                    ChatMessagesView(chat: chat)
                } else if let contact = entity as? Contact {
                    ContactDetailView(contact: contact)
                } else {
                    Text("Unknown item")
                }
            }
            .navigationDestination(for: Contact.self) { contact in
                ContactDetailView(contact: contact)
            }
        }
    }
}
