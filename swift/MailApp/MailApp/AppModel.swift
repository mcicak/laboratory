//
//  Model.swift
//  MailApp
//
//  Created by Marko Cicak on 19.8.24..
//

import Foundation
import SwiftUI
import SwiftData

@MainActor
class AppModel: ObservableObject {
    @Published var folders: [DBFolder] = []
    @Published var mainMenuSelection: MainMenu?
    @Published var selectedFolder: DBFolder?
    @Published var selectedMail: DBMail?
    @Published var path = NavigationPath()

    var modelContext: ModelContext
    
    init(modelContext: ModelContext) {
        self.modelContext = modelContext
        fetchFolders()
    }
    
    func fetchFolders() {
        let fetchDescriptor = FetchDescriptor<DBFolder>()
        do {
            folders = try modelContext.fetch(fetchDescriptor)
        } catch {
            print("Error fetching folders: \(error)")
        }
    }
    
    func addFolder(name: String) {
        let newFolder = DBFolder(id: UUID(), name: name)
        modelContext.insert(newFolder)
        
        do {
            try modelContext.save()
            fetchFolders() // Refresh folders list
        } catch {
            print("Error saving folder: \(error)")
        }
    }
    
    func deleteFolder(at indexSet: IndexSet) {
        for index in indexSet {
            let folderToDelete = folders[index]
            modelContext.delete(folderToDelete)
        }
        
        do {
            try modelContext.save()
            fetchFolders() // Refresh folders list
        } catch {
            print("Error deleting folder: \(error)")
        }
    }
}
