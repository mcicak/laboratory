//
//  ChatsView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct ChatsView: View {

    @Environment(AppModel.self) private var appModel
    @EnvironmentObject var uiModel: UIModel
        
    var body: some View {
        if appModel.chats.isEmpty {
            Text("No chats")
        } else {
            List(appModel.chats) { chat in
                Button(action: {
                    appModel.selectedItem = chat
                    uiModel.mainPath = [chat]
                }) {
                    Text(chat.name)
                }
            }
        }
    }
}
