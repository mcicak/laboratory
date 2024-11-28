//
//  Clipboard.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 13.9.24..
//

import Foundation

@Observable
class Clipboard {
    
    private var elements: Set<Symbol> = []
    
    var isEmpty: Bool {
        elements.isEmpty
    }
    
    func setElements(_ elements: Set<Symbol>) {
        // Copy each element and store in the clipboard
        self.elements = Set(elements.map { $0.copy() })
    }
    
    func getElements() -> Set<Symbol> {
        // Return a set of copied elements to avoid modifying the clipboard's stored elements
        return Set(self.elements.map { $0.copy() })
    }
}
