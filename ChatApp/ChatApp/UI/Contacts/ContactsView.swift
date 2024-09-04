//
//  ContactsView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct ContactsView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        if appModel.contacts.isEmpty {
            Text("No contacts")
        } else {
            List(appModel.contacts) { contact in
                Button(action: {
                    //selectedContact = contact
                    appModel.selectedItem = contact
                    appModel.mainPath = [contact]
                }) {
                    Text(contact.name)
                }
            }
        }
    }
}

struct ContactView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        if let contact = appModel.selectedItem as? Contact {
            Text(contact.name)
        } else {
            Text("No selected contact")
        }
    }
}
