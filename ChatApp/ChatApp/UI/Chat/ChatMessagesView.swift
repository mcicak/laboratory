//
//  ChatMessagesView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI
import ExyteChat

struct ChatMessagesRootView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        if let chat = appModel.selectedItem as? Chat {
            ChatMessagesView(chat: chat)
        } else {
            Text("No selected chat")
        }
    }
}

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
