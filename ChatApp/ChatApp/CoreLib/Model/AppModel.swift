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
}

@Observable
class AppModel {
    
    var context = AppContext()
    
    var chats = [Chat]()
    var contacts = [Contact]()    
    var selectedItem: (any Entity)?
}

@Observable
class AppContext {
    
    //var user: AppUser?
    var user: AppUser? = AppUser(username: "u1", password: "p1")
}

@Observable
class AppUser {
    
    let username: String
    let password: String
    
    init(username: String, password: String) {
        self.username = username
        self.password = password
    }
}
