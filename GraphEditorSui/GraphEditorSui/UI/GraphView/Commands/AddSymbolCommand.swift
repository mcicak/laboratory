//
//  AddSymbolCommand.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 12.9.24..
//

import Foundation

class AddSymbolCommand: Command {
    
    let symbols: Set<Symbol>
    
    init(symbols: Set<Symbol>) {
        self.symbols = symbols
    }
    
    func doCommand(_ graph: GraphStateMachine) {
        graph.selectionModel.clearSelection()
        graph.viewModel.symbols.append(contentsOf: symbols)
    }
    
    func undoCommand(_ graph: GraphStateMachine) {
        graph.selectionModel.clearSelection()
        graph.viewModel.symbols.removeObjects(in: symbols)
    }
}
