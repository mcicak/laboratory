//
//  LoginView.swift
//  ChatApp
//
//  Created by Marko Cicak on 4.9.24..
//

import Foundation
import SwiftUI
import ActivityIndicatorView

struct LoginRootView: View {
    
    @Environment(AppModel.self) private var appModel
    
    var body: some View {
        if let _ = appModel.context.user {
            Text("User logged in")
        } else {
            LoginView()
        }
    }
}

struct LoginView: View {
    
    @State private var username: String = ""
    @State private var password: String = ""
    @State private var loginInProgress = false

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
    
    fileprivate func loginForm() -> some View {
        return VStack {
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
                .autocapitalization(.none)
                .submitLabel(.next)
                .onSubmit {
                    focusedField = .password
                }
            
            // Password TextField
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 40)
                .padding(.top, 20)
                .autocapitalization(.none)
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
            .disabled(!canLogin || loginInProgress)
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
    
    var body: some View {
        ZStack {
            loginForm()
            
            if loginInProgress {
                ActivityIndicatorView(isVisible: $loginInProgress, type: .gradient([.white, .blue]))
                    .frame(width: 50, height: 50)
            }
        }
    }
    
    private func hideKeyboardAndLogin() {
        loginInProgress = true
        hideKeyboard()
        Task {
            do {
                let response = try await api.login(username:username, password:password)
                if response.success {
                    DispatchQueue.main.async {
                        let user = AppUser(username: username, password: password)
                        
                        let chat1 = Chat(name: "C1")
                        let chat2 = Chat(name: "C2")
                        
                        let contact1 = Contact(name: "Ali Baba 1", phoneNumber: "+381637364533")
                        let contact2 = Contact(name: "Ali Baba 2", phoneNumber: "+381637364533")
                        
                        modelContext.insert(chat1)
                        modelContext.insert(chat2)
                        modelContext.insert(contact1)
                        modelContext.insert(contact2)
                        
                        do{
                            try modelContext.save()
                            appModel.chats = [chat1, chat2]
                            appModel.contacts = [contact1, contact2]
                        }catch{
                            print("Error: \(error.localizedDescription)")
                        }
                        
                        modelContext.insert(user)
                        try? modelContext.save()
                        withAnimation {
                            appModel.context.user = user
                        }
                        loginInProgress = false
                        print("Login completed")
                    }
                }
            } catch {
                print("Login error: \(error.localizedDescription)")
                loginInProgress = false
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
