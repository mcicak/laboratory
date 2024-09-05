//
//  LoginView.swift
//  ChatApp
//
//  Created by Marko Cicak on 4.9.24..
//

import Foundation
import SwiftUI

struct LoginView: View {
    
    @State private var username: String = ""
    @State private var password: String = ""
    @Environment(\.apiService) var api
    @Environment(AppModel.self) private var appModel
    @Environment(\.modelContext) private var modelContext
    
    @FocusState private var focusedField: Field? // Manage focus between text fields
    
    private var canLogin: Bool {
        return !username.trim().isEmpty && !password.trim().isEmpty
    }
    
    enum Field {
        case username
        case password
    }
    
    var body: some View {
        VStack {
            Spacer()
            
            // App logo (system image)
            Image(systemName: "bubble.left.and.bubble.right")
                .resizable()
                .aspectRatio(contentMode: .fit) // Maintain aspect ratio
                .frame(height: 100) // Set the height
                .padding(.bottom, 40)
            
            // Username TextField
            TextField("Username", text: $username)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 40)
                .focused($focusedField, equals: .username)
                .submitLabel(.next)
                .onSubmit {
                    focusedField = .password
                }
            
            // Password TextField
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 40)
                .padding(.top, 20)
                .focused($focusedField, equals: .password)
                .submitLabel(.go)
                .onSubmit {
                    hideKeyboardAndLogin()
                }
            
            // Login Button
            Button(action: {
                hideKeyboardAndLogin()
            }) {
                Text("Login")
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(10)
            }
            .disabled(!canLogin)
            .opacity(canLogin ? 1.0 : 0.5)
            .frame(height: 50)
            .padding(.horizontal, 40)
            .padding(.top, 40)
            
            Spacer()
        }
        .contentShape(Rectangle()) // Detect taps on empty areas
        .onTapGesture {
            hideKeyboard() // Dismiss the keyboard when the background is tapped
        }
    }
    
    private func hideKeyboardAndLogin() {
        hideKeyboard()
        Task {
            do {
                let response = try await api.login(username:username, password:password)
                if response.success {
                    DispatchQueue.main.async {
                        let user = AppUser(username: username, password: password)
                        modelContext.insert(user)
                        try? modelContext.save()
                        appModel.context.user = user
                    }
                }
            } catch {
                print("Login error: \(error.localizedDescription)")
            }
        }
    }
    
    private func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

extension UIApplication {
    func endEditing() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

extension String {
    func trim() -> String {
        self.trimmingCharacters(in: .whitespacesAndNewlines)
    }
}
