//
//  ContentView.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import SwiftUI
import SwiftData
import ExyteChat

enum MainTab {
    case chats
    case contacts
    case profile
    
    var title: String {
            switch self {
            case .chats:
                return "Chats"
            case .contacts:
                return "Contacts"
            case .profile:
                return "My Profile"
            }
        }
}

struct RootView: View {
    
    @Environment(\.modelContext) private var modelContext
    @Environment(\.horizontalSizeClass) var horizontalSizeClass
    @EnvironmentObject var appModel: AppModel

    var body: some View {
        Group {
            if horizontalSizeClass == .regular {
                SplitView()
            } else {
                MyTabView()
            }
        }
    }
}

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

// Views for the tabs
struct ChatsView: View {
//    @Binding var selectedChat: (any Identifiable)?
    @EnvironmentObject var appModel: AppModel
    
    let chats = [
        Chat(name: "Chat with Alice"),
        Chat(name: "Chat with Bob"),
        Chat(name: "Chat with Charlie")
    ]
    
    var body: some View {
        List(chats) { chat in
            Button(action: {
//                selectedChat = chat
                appModel.selectedItem = chat
                appModel.mainPath = [chat]
            }) {
                Text(chat.name)
            }
        }
        //.navigationTitle("Chats")
    }
}

struct ContactsView: View {

    @EnvironmentObject var appModel: AppModel
    
    let contacts = [
        Contact(name: "Alice", phoneNumber: "123-456-7890"),
        Contact(name: "Bob", phoneNumber: "987-654-3210"),
        Contact(name: "Charlie", phoneNumber: "555-555-5555")
    ]
    
    var body: some View {
        List(contacts) { contact in
            Button(action: {
                //selectedContact = contact
                appModel.selectedItem = contact
                appModel.mainPath = [contact]
            }) {
                Text(contact.name)
            }
        }
        .navigationTitle("Contacts")
    }
}

struct MyProfileView: View {
    var body: some View {
        VStack {
            Text("My Profile")
                .font(.largeTitle)
                .padding()
            Text("Profile details go here...")
        }
        .navigationTitle("My Profile")
    }
}

// Views for the detail section
struct ChatMessagesView: View {
    let chat: Chat
    
    var body: some View {
        let user1 = User(id: "1", name: "John Doe", avatarURL: nil, isCurrentUser: true)
        let user2 = User(id: "2", name: "Alexander McWallace", avatarURL: nil, isCurrentUser: false)
        
        let messages = [
            Message(id: "1", user: user1, text: "Hello!"),
            Message(id: "2", user: user2, text: "Hey man, 'sup"),
            Message(id: "3", user: user1, text: "Nadda"),
            Message(id: "4", user: user1, text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."),
        ]
        
        ChatView(messages: messages, didSendMessage: { draft in
            print("Sending message: \(draft.text)")
        })
        .navigationTitle(chat.name)
    }
}

struct ContactDetailView: View {
    let contact: Contact
    
    var body: some View {
        VStack {
            Text(contact.name)
                .font(.largeTitle)
            Text("Phone: \(contact.phoneNumber)")
                .padding()
            Spacer()
        }
        .navigationTitle(contact.name)
    }
}

#Preview {
    RootView()
        .modelContainer(for: Item.self, inMemory: true)
}
