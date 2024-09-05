//
//  SplitView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct SplitView: View {
    
    @Environment(AppModel.self) private var appModel
    @EnvironmentObject var uiModel: UIModel
    @State var cv: NavigationSplitViewVisibility = .doubleColumn
    
    var body: some View {
        NavigationSplitView(columnVisibility: $cv) {
            TabView(selection: $uiModel.selectedTab) {
                Text("") // needed when Tab is in SplitView

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
        } detail: {
            if let selectedChat = appModel.selectedItem as? Chat {
                ChatMessagesView(chat: selectedChat)
            } else if let selectedContact = appModel.selectedItem as? Contact {
                ContactDetailView(contact: selectedContact)
            } else {
                Text("Select a chat or contact")
                    .foregroundColor(.gray)
            }
        }
        .navigationSplitViewStyle(.balanced)
    }
}
