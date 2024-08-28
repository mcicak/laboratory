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
    
    @Published var selectedItem: (any Identifiable)?
    @Published var selectedTab: MainTab = .chats
    
    @Published var mainPath: [AnyHashable] = []
    
    var modelContext: ModelContext
    
    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }
}

protocol Entity: Identifiable, Hashable {
    
}

struct Chat: Entity {
    let id = UUID()
    let name: String
}

struct Contact: Entity {
    let id = UUID()
    let name: String
    let phoneNumber: String
}
