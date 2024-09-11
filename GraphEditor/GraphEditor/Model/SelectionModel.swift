//
//  SelectionModel.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/5/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class SelectionModel {
    var selection = NSMutableArray()
    var count: Int {
        return selection.count
    }
    var elements: NSMutableArray {
        let duplicate = NSMutableArray()
        for element in selection {
            duplicate.add(element)
        }
        
        return duplicate
    }
    
    func addToSelection(symbol: Symbol) {
        symbol.isSelected = true
        selection.add(symbol)
        fireSelectionChanged(symbols: [symbol])
    }
    
    func addMultipleSelection(symbols: NSMutableArray) {
        for symbol in (symbols as! [Symbol]) {
            if !selection.contains(symbol) {
                symbol.isSelected = true
                selection.add(symbol)
            }
        }
        fireSelectionChanged(symbols: symbols)
    }
    
    func removeFromSelection(symbol: Symbol) {
        symbol.isSelected = false
        selection.remove(symbol)
        fireSelectionChanged(symbols: [])
    }
    
    func clearSelection() {
        for symbol in (selection as! [Symbol]) {
            symbol.isSelected = false
        }
        selection.removeAllObjects()
        fireSelectionChanged(symbols: [])
    }
}

extension SelectionModel {
    private func fireSelectionChanged(symbols: NSMutableArray) {
        let nc = NotificationCenter.default
        nc.post(name: .notificationSelectionChanged, object: nil, userInfo: ["symbols": symbols])
    }
}
