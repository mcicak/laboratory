//
//  ProfileView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct ProfileView: View {
    
    @Environment(AppModel.self) private var appModel
    @EnvironmentObject var uiModel: UIModel
    @Environment(\.modelContext) private var modelContext
    
    var body: some View {
        VStack {
            Text("My Profile")
                .font(.largeTitle)
                .padding()
            Text("Profile details go here...")
            
            Spacer()
            
            Button(action: {
                            logout()
                        }) {
                            Text("Logout")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.red)
                                .foregroundColor(.white)
                                .cornerRadius(10)
                        }
                        .padding()
        }
        .navigationTitle("My Profile")
    }
    
    private func logout() {
        appModel.reset()
        uiModel.reset()
        do {
            try modelContext.delete(model: AppUser.self)
            try modelContext.delete(model: Chat.self)
            try modelContext.delete(model: Contact.self)
        } catch {
            print("Failed to delete entities: \(error.localizedDescription)")
        }
    }
}
