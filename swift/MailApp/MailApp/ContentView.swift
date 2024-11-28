//
//  ContentView.swift
//  MailApp
//
//  Created by Marko Cicak on 16.8.24..
//

import SwiftUI
import SwiftData

struct ContentView: View {
    
    @Environment(\.modelContext) private var modelContext
    @EnvironmentObject var appModel: AppModel
    //@State private var columnVisibility = NavigationSplitViewVisibility.doubleColumn
    
    var body: some View {
        NavigationSplitView() {
            SidebarView()
        } content: {
            MasterView()
        } detail: {
            DetailView()
        }
        .navigationSplitViewStyle(.automatic)
    }
}

struct SidebarView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        List(selection: $appModel.mainMenuSelection) {
            
            ForEach(appModel.folders) { folder in
                NavigationLink(value: MainMenu.folder(folder.id)) {
                    Label(folder.name, systemImage: "folder")
                }
            }
            
            Section("Other") {
                NavigationLink(value: MainMenu.contacts) {
                    Label("Contacts", systemImage: "person.2")
                }
                
                NavigationLink(value: MainMenu.settings) {
                    Label("Settings", systemImage: "gearshape")
                }
            }
        }
        .navigationTitle("Mail")
        .onChange(of: appModel.selectedFolder) {
            appModel.selectedMail = nil
        }
    }
}

struct MasterView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        switch appModel.mainMenuSelection {
        case .folder(let id):
            MailsView(folder: appModel.folders.first { $0.id == id }!)
        case .contacts:
            ContactsView()
        case .settings:
            SettingsView()
        case .none:
            Text("Select item")
        }
    }
}

struct MailsView: View {
        
    @EnvironmentObject var appModel: AppModel
    let folder: DBFolder
    
    var body: some View {
        
        List(selection: $appModel.selectedMail) {
            ForEach(folder.mails) { mail in
                NavigationLink(value: mail) {
                    Text(mail.subject)
                        .bold(mail.unread)
                }
            }
        }
        .navigationTitle(folder.name)
    }
}

struct DetailView: View {
    
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        ZStack {
            if let mail = appModel.selectedMail {
                MailView(mail: mail)
            } else {
                Text("No mail selected")
            }
        }
    }
}

struct MailView: View {
    
    let mail: DBMail
    @EnvironmentObject var appModel: AppModel
    
    var body: some View {
        ZStack {
            Text(mail.subject)
            
            Text("Did read: \(mail.unread ? "no" : "yes")")
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .topLeading)
                .padding()
        }
        .background()
        #if os(iOS)
        .toolbarRole(.browser)
        #endif
        .toolbar {
            ToolbarItemGroup {
                toolbarItems
            }
        }
    }
    
    @ViewBuilder
    var toolbarItems: some View {
        Button {
            mail.unread = !mail.unread
            do {
                try appModel.modelContext.save()
            } catch {
                print("Error saving mail: \(error.localizedDescription)")
            }
        } label: {
            Label("Mark as read", systemImage: mail.unread ? "envelope.open" : "envelope")
        }
    }
}

struct ContactsView: View {
    var body: some View {
        Text("Contacts")
    }
}

struct SettingsView: View {
    var body: some View {
        Text("Settings")
    }
}

enum MainMenu: Hashable {
    case folder(DBFolder.ID)
    case contacts
    case settings
}
