//
//  MoveCommand.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 12.9.24..
//

import Foundation

class MoveSymbolsCommand: Command {
    
    let symbols: Set<Symbol>
    let delta: CGPoint
    private var firstRun: Bool = true
    
    init(symbols: Set<Symbol>, delta: CGPoint) {
        self.symbols = symbols
        self.delta = delta
    }
    
    func doCommand(_ graph: GraphStateMachine) {
        if firstRun {
            firstRun = false
            return
        }
        
        symbols.forEach { $0.position += delta }
    }
    
    func undoCommand(_ graph: GraphStateMachine) {
        symbols.forEach { $0.position -= delta }
    }
}
