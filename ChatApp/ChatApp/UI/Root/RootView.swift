//
//  ContentView.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import SwiftUI

@Observable
class SelectedTab {
    var selection: MainTab = .chats
}

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
    @Environment(AppModel.self) private var appModel
    @EnvironmentObject var uiModel: UIModel

    
    var body: some View {
        Group {
            if isRunningOnMac {
                MacGridLayout()
            } else {
                if let _ = appModel.context.user {
                    if horizontalSizeClass == .regular {
                        SplitView()
                    } else {
                        MyTabView()
                    }
                } else {
                    LoginView()
                }
            }
        }
    }
    
    var isRunningOnMac: Bool {
        ProcessInfo.processInfo.isMacCatalystApp
    }
}

struct MacGridLayout: View {
    
    // Array of views, each corresponding to a screen
    private let views: [AnyView] = [
        AnyView(LoginView().frame(width: 375, height: 667)),
        AnyView(MyTabView().frame(width: 375, height: 667)),
        AnyView(ChatsView().frame(width: 375, height: 667)),
        AnyView(ChatMessagesRootView().frame(width: 375, height: 667)),
        AnyView(ContactsView().frame(width: 375, height: 667)),
        AnyView(ContactDetailRootView().frame(width: 375, height: 667)),
        AnyView(ProfileView().frame(width: 375, height: 667))
    ]

    var body: some View {
        ScrollView([.horizontal, .vertical]) {
            LazyVGrid(columns: Array(repeating: GridItem(.fixed(375)), count: 4), spacing: 20) {
                ForEach(0..<views.count, id: \.self) { index in
                    VStack {
                        views[index] // Render view from array
                    }
                    .border(Color.gray)
                }
            }
            .padding()
        }
    }
}
