//
//  AppModel.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import Foundation
import SwiftUI
import SwiftData

@MainActor
class AppModel: ObservableObject {
    
    @StateObject var context = AppContext()
    
    @Published var chats = [Chat]()
    @Published var contacts = [Contact]()
    
    @Published var selectedItem: (any Entity)?
    @Published var selectedTab: MainTab = .chats
    
    @Published var mainPath: [AnyHashable] = []
    
    var modelContext: ModelContext
    
    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }
}

class AppContext: ObservableObject {
    
    @Published var user: AppUser?
}

struct AppUser {
    
    let username: String
    let password: String
}
