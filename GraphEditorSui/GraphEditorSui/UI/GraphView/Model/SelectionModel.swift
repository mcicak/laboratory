//
//  SelectionModel.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 30.8.24..
//

import Foundation

@Observable
class SelectionModel: ObservableObject {
    
    var elements: Set<Symbol> = []

    var count: Int {
        return elements.count
    }
    
    var isEmpty: Bool { elements.isEmpty }

    func addToSelection(symbol: Symbol) {
        symbol.isSelected = true
        elements.insert(symbol)
    }

    func addMultipleSelection(symbols: Set<Symbol>) {
        for symbol in symbols {
            if !elements.contains(symbol) {
                symbol.isSelected = true
                elements.insert(symbol)
            }
        }
    }

    func removeFromSelection(symbol: Symbol) {
        symbol.isSelected = false
        elements.remove(symbol)
    }

    func clearSelection() {
        for symbol in elements {
            symbol.isSelected = false
        }
        elements.removeAll()
    }
    
    func toggleSelectionFor(_ symbol: Symbol) {
        if elements.contains(symbol) {
            removeFromSelection(symbol: symbol)
        } else {
            addToSelection(symbol: symbol)
        }
    }
}
