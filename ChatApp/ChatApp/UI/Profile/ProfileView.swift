//
//  ProfileView.swift
//  ChatApp
//
//  Created by Marko Cicak on 28.8.24..
//

import Foundation
import SwiftUI

struct MyProfileView: View {
    var body: some View {
        VStack {
            Text("My Profile")
                .font(.largeTitle)
                .padding()
            Text("Profile details go here...")
        }
        .navigationTitle("My Profile")
    }
}
