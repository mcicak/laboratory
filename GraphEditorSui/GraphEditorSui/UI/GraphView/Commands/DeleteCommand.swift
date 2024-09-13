//
//  DeleteCommand.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 13.9.24..
//

import Foundation

class DeleteCommand: Command {
    
    let symbols: Set<Symbol>
    
    init(symbols: Set<Symbol>) {
        self.symbols = Set(symbols)
    }
    
    func doCommand(_ graph: GraphStateMachine) {
        graph.selectionModel.clearSelection()
        graph.viewModel.symbols.removeObjects(in: symbols)
    }
    
    func undoCommand(_ graph: GraphStateMachine) {
        graph.selectionModel.clearSelection()
        graph.viewModel.symbols.append(contentsOf: symbols)
    }
}
