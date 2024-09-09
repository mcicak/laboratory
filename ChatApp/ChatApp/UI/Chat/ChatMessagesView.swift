//
//  ChatMessagesView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI
import ExyteChat
import SwiftData

struct ChatMessagesRootView: View {
    
    @Environment(AppModel.self) private var appModel
    
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
    @Environment(AppModel.self) private var appModel
    @Environment(\.apiService) private var api
    @Environment(\.modelContext) private var modelContext
    
    var interactor: ChatInteractor {
        ChatInteractor(chat: chat, appModel: appModel, api: api, moc: modelContext)
    }
    
    var body: some View {
        let user1 = User(id: "1", name: "John Doe", avatarURL: nil, isCurrentUser: true)
        
        let messages = appModel.messages.map {
            Message(id: $0.id.uuidString, user: user1, status: $0.exyteStatus, text: $0.text)
        }
        
        ChatView(messages: messages, didSendMessage: { draft in
            interactor.send(draft: draft)
        })
        .navigationTitle(chat.name)
    }
}

extension DBMessage {
    
    var exyteStatus: Message.Status {
        switch status {
        case .pending: return .sending
        case .sending: return .sending
        case .sent: return .sent
        case .read: return .read
        case .error: return .error(DraftMessage(text: text, medias: [], recording: nil, replyMessage: nil, createdAt: Date()))
        }
    }
}
