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
    
    let contacts = [
        Contact(name: "Alice", phoneNumber: "123-456-7890"),
        Contact(name: "Bob", phoneNumber: "987-654-3210"),
        Contact(name: "Charlie", phoneNumber: "555-555-5555")
    ]
    
    var body: some View {
        List(contacts) { contact in
            Button(action: {
                //selectedContact = contact
                appModel.selectedItem = contact
                appModel.mainPath = [contact]
            }) {
                Text(contact.name)
            }
        }
        .navigationTitle("Contacts")
    }
}
