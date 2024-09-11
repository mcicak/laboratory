//
//  Model.swift
//  GraphEditor
//
//  Created by Marko Cicak on 10/31/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

final class Model: GenericModel {
    private(set) var symbols = NSMutableArray()
    private(set) var selection: [Symbol] = []
    
    func addSymbol(symbol: Symbol) {
        symbols.add(symbol)
        fireSymbolsAdded(symbols: [symbol])
    }
    
    func addSymbols(symbols: NSMutableArray) {
        self.symbols.addObjects(from: symbols as! [Symbol])
        fireSymbolsAdded(symbols: symbols)
    }
    
    func removeSymbol(symbol: Symbol) {
        symbols.remove(symbol)
        fireSymbolsRemoved(symbols: [symbol])
    }
    
    func removeSymbols(symbols: NSMutableArray) {
        self.symbols.removeObjects(in: symbols as! [Symbol])
        fireSymbolsRemoved(symbols: symbols)
    }
    
    func removeAllSymbols() {
        symbols.removeAllObjects()
        fireSymbolsRemoved(symbols: symbols)
    }
    
    func removeSelected() {
        // TODO: need to implement selection
    }
    
    func logSymbolsToConsole() {
        symbols.forEach { (symbol) in
            print(symbol as! Symbol)
        }
    }
}

// MARK: - Notification methods
extension Model {
    func fireSymbolsAdded(symbols: NSMutableArray) {
        let nc = NotificationCenter.default
        nc.post(name: .notificationSymbolsAdded, object: nil, userInfo: ["symbols": symbols])
    }
    
    func fireSymbolsRemoved(symbols: NSMutableArray) {
        let nc = NotificationCenter.default
        nc.post(name: .notificationSymbolsRemoved, object: nil, userInfo: ["symbols": symbols])
    }
    
    func fireModelUpdate() {
        let nc = NotificationCenter.default
        nc.post(name: .notificationModelUpdated, object: nil, userInfo: nil)
    }
}

// MARK: - UserDefaults methods
extension Model {
    
}

