//
//  ChatsView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct ChatsView: View {

    @EnvironmentObject var appModel: AppModel
    
    let chats = [
        Chat(name: "Chat with Alice"),
        Chat(name: "Chat with Bob"),
        Chat(name: "Chat with Charlie")
    ]
    
    var body: some View {
        List(chats) { chat in
            Button(action: {
                appModel.selectedItem = chat
                appModel.mainPath = [chat]
            }) {
                Text(chat.name)
            }
        }
    }
}
