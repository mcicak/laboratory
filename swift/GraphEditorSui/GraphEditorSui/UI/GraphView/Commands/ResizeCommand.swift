//
//  ResizeCommand.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 13.9.24..
//

import Foundation

class ResizeCommand: Command {
    
    let symbol: Symbol
    let startFrame: CGRect
    let endFrame: CGRect
    var firstRun = false
    
    init(symbol: Symbol, startFrame: CGRect, endFrame: CGRect) {
        self.symbol = symbol
        self.startFrame = startFrame
        self.endFrame = endFrame
    }
    
    func doCommand(_ graph: GraphStateMachine) {
        if firstRun {
            firstRun = false
            return
        }
        
        symbol.position = endFrame.origin
        symbol.size = endFrame.size
    }
    
    func undoCommand(_ graph: GraphStateMachine) {
        symbol.position = startFrame.origin
        symbol.size = startFrame.size
    }
}
