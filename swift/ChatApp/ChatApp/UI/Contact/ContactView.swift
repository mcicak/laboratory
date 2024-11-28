//
//  ContactView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct ContactDetailRootView: View {
    
    @Environment(AppModel.self) private var appModel
    
    var body: some View {
        if let contact = appModel.selectedItem as? Contact {
            ContactDetailView(contact: contact)
        }else{
            Text("No selected contact")
        }
    }
}

struct ContactDetailView: View {
    let contact: Contact
    
    var body: some View {
        VStack {
            Text(contact.name)
                .font(.largeTitle)
            Text("Phone: \(contact.phoneNumber)")
                .padding()
            Spacer()
        }
        .navigationTitle(contact.name)
    }
}
