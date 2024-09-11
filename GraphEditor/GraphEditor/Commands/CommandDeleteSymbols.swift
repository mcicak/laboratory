//
//  CommandDeleteSymbols.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/14/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class CommandDeleteSymbols: CommandAbstract {
    var symbols: NSMutableArray {
        didSet {
            print("symbols in CommandDeleteSymbols array: ", symbols)
        }
    }
    
    init(symbols: NSMutableArray) {
        self.symbols = NSMutableArray()
        for symbol in symbols {
            self.symbols.add(symbol)
        }
    }
    
    override func doCommand() {
        SREG.selection.clearSelection()
        SREG.model.removeSymbols(symbols: self.symbols)
    }
    
    override func undoCommand() {
        SREG.selection.clearSelection()
        SREG.model.addSymbols(symbols: self.symbols)
    }
}
