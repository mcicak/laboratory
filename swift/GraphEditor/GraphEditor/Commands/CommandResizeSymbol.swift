//
//  CommandResizeSymbol.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/14/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class CommandResizeSymbol: CommandAbstract {
    let symbol: Symbol
    let startDimensions: CGRect
    let endDimensions: CGRect
    var firstRun: Bool
    
    init(symbol: Symbol, startDimensions: CGRect, endDimensions: CGRect) {
        self.symbol = symbol
        self.startDimensions = startDimensions
        self.endDimensions = endDimensions
        self.firstRun = true
    }
    
    override func doCommand() {
        if firstRun {
            firstRun = false
            return
        }
        
        symbol.position = endDimensions.origin
        symbol.size = endDimensions.size
        SREG.model.fireModelUpdate()
    }
    
    override func undoCommand() {
        symbol.position = startDimensions.origin
        symbol.size = startDimensions.size
        SREG.model.fireModelUpdate()
    }
}
