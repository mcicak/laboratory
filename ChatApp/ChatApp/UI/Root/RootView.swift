//
//  ContentView.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import SwiftUI

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
