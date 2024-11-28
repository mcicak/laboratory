//
//  TabView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct MyTabView: View {
    
    @Environment(AppModel.self) private var appModel
    @EnvironmentObject var uiModel: UIModel
    
    static func mainTabsToolbar(_ uiModel: UIModel) -> ToolbarItemGroup<TupleView<(Button<Label<Text, Image>>?, Button<Label<Text, Image>>?)>> {
        return ToolbarItemGroup {
            if uiModel.selectedTab == .chats {
                Button {
                    // TODO: show new chat dialog
                } label: {
                    Label("New Chat", systemImage: "plus.bubble")
                }
            }
            
            if uiModel.selectedTab == .contacts {
                Button {
                    // TODO: show new chat dialog
                } label: {
                    Label("New Contact", systemImage: "person.badge.plus")
                }
            }
        }
    }
    
    var body: some View {
        NavigationStack(path: $uiModel.mainPath) {
            TabView(selection: $uiModel.selectedTab) {
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
                
                ProfileView()
                    .tabItem {
                        Label("My Profile", systemImage: "person.crop.circle")
                    }
                    .tag(MainTab.profile)
            }
            .navigationTitle(uiModel.selectedTab.title)
            .toolbar {
                MyTabView.mainTabsToolbar(uiModel)
            }
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
