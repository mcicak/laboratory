//
//  SplitView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct SplitView: View {
    
    @EnvironmentObject var appModel: AppModel
    @State var cv: NavigationSplitViewVisibility = .doubleColumn
    
    var body: some View {
        NavigationSplitView(columnVisibility: $cv) {
            TabView(selection: $appModel.selectedTab) {

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

                MyProfileView()
                    .tabItem {
                        Label("My Profile", systemImage: "person.crop.circle")
                    }
                    .tag(MainTab.profile)
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
