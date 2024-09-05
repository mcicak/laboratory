//
//  Item.swift
//  ChatApp
//
//  Created by Marko Cicak on 23.8.24..
//

import Foundation
import SwiftData

protocol Entity: Identifiable, Hashable {}

@Model
final class Chat: Entity {
    let id = UUID()
    let name: String
    
    init(name: String) {
        self.name = name
    }
}

@Model
final class Contact: Entity {
    let id = UUID()
    let name: String
    let phoneNumber: String
    
    init(name: String, phoneNumber: String) {
        self.name = name
        self.phoneNumber = phoneNumber
    }
}

@Model
final class AppUser: Entity {
    let id = UUID()
    let username: String
    let password: String
    
    init(username: String, password: String) {
        self.username = username
        self.password = password
    }
}
