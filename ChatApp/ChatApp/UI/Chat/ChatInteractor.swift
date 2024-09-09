//
//  ChatInteractor.swift
//  ChatApp
//
//  Created by Marko Cicak on 9.9.24..
//

import Foundation
import SwiftData
import ExyteChat

@MainActor
struct ChatInteractor {
    
    let chat: Chat
    let appModel: AppModel
    let api: ApiService
    let moc: ModelContext
    
    func send(draft: DraftMessage) {
        let msg = DBMessage(serverId: nil, text: draft.text, chat: chat.id.uuidString, rawStatus: DBMessage.Status.sending.rawValue)
        moc.insert(msg)
        
        do {
            try moc.save()
        } catch {
            print("Error saving MOC: \(error)")
        }
        
        appModel.messages.append(msg)
        
        Task {
            do {
                let result = try await api.sendMessage(text: draft.text, chatId: chat.id.uuidString)
                msg.serverId = result.id
                msg.rawStatus = DBMessage.Status.sent.rawValue
                try moc.save()
                
                try await Task.sleep(nanoseconds: 1000000000)
                msg.rawStatus = DBMessage.Status.read.rawValue
                try moc.save()
            } catch {
                print("Error sending chat message: \(error)")
                msg.rawStatus = DBMessage.Status.error.rawValue
                try? moc.save()
            }
        }
    }
}
