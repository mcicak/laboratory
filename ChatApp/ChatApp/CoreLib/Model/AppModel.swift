//
//  AppModel.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import Foundation
import SwiftUI

class UIModel: ObservableObject {
    @Published var selectedTab: MainTab = .chats
    @Published var mainPath: [AnyHashable] = []
    
    func reset() {
        selectedTab = .chats
        mainPath.removeAll()
    }
}

@Observable
class AppModel {
    
    var context = AppContext()
    
    var chats = [Chat]()
    var contacts = [Contact]()    
    var selectedItem: (any Entity)?
    
    func reset() {
        chats.removeAll()
        contacts.removeAll()
        selectedItem = nil
        context = AppContext()
    }
}

@Observable
class AppContext {
    
    var user: AppUser?
}
