//
//  CommandAddSymbols.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/7/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class CommandAddSymbols: CommandAbstract {
    var symbols: NSMutableArray
    
    init(symbols: NSMutableArray) {
        self.symbols = symbols
    }
    
    override func doCommand() {
        SREG.selection.clearSelection()
        SREG.model.addSymbols(symbols: symbols)
    }
    
    override func undoCommand() {
        SREG.selection.clearSelection()
        SREG.model.removeSymbols(symbols: symbols)
    }
}
