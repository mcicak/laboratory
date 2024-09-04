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
        
    var body: some View {
        if appModel.chats.isEmpty {
            Text("No chats")
        } else {
            List(appModel.chats) { chat in
                Button(action: {
                    appModel.selectedItem = chat
                    appModel.mainPath = [chat]
                }) {
                    Text(chat.name)
                }
            }
        }
    }
}
