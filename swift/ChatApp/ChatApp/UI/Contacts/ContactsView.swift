//
//  ContactsView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct ContactsView: View {
    
    @Environment(AppModel.self) private var appModel
    @EnvironmentObject var uiModel: UIModel
    
    var body: some View {
        if appModel.contacts.isEmpty {
            Text("No contacts")
        } else {
            List(appModel.contacts) { contact in
                Button(action: {
                    //selectedContact = contact
                    appModel.selectedItem = contact
                    uiModel.mainPath = [contact]
                }) {
                    Text(contact.name)
                }
            }
        }
    }
}

struct ContactView: View {
    
    @Environment(AppModel.self) private var appModel
    
    var body: some View {
        if let contact = appModel.selectedItem as? Contact {
            Text(contact.name)
        } else {
            Text("No selected contact")
        }
    }
}
