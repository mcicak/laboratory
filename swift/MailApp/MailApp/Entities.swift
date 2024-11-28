//
//  Item.swift
//  MailApp
//
//  Created by Marko Cicak on 16.8.24..
//

import Foundation
import SwiftData

@Model
final class DBFolder: Identifiable, Hashable {
    @Attribute(.unique) var id: UUID
    var name: String
    @Relationship(deleteRule: .cascade, inverse: \DBMail.folder) var mails: [DBMail] = []
    
    init(id: UUID, name: String) {
        self.id = id
        self.name = name
    }
}

@Model
final class DBMail {
    var subject: String
    
        @Attribute(.unique) var id: UUID
        var date: Date
        var from: String
        var to: String
        var body: String
        var flagged: Bool
        var unread: Bool
        var folder: DBFolder
        
    init(subject: String, date: Date, from: String, to: String, body: String, flagged: Bool = false, unread: Bool = true, folder: DBFolder) {
            self.id = UUID()
            self.subject = subject
            self.date = date
            self.from = from
            self.to = to
            self.body = body
            self.flagged = flagged
            self.unread = unread
            self.folder = folder
        }
}
