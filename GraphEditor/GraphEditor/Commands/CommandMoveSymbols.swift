//
//  CommandMoveSymbols.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/13/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class CommandMoveSymbols: CommandAbstract {
    var symbols: NSMutableArray
    var dx: CGFloat
    var dy: CGFloat
    var firstRun: Bool
    
    init(symbols: NSMutableArray, deltaX x: CGFloat, deltaY y: CGFloat) {
        self.symbols = symbols
        dx = x
        dy = y
        firstRun = true
    }
    
    override func doCommand() {
        if firstRun {
            firstRun = false
            return
        }
        for symbol in (symbols as Any as! [Symbol]) {
            symbol.position = CGPoint(x: symbol.position.x + dx, y: symbol.position.y + dy)
        }
        SREG.model.fireModelUpdate()
    }
    
    override func undoCommand() {
        for symbol in (symbols as Any as! [Symbol]) {
            symbol.position = CGPoint(x: symbol.position.x - dx, y: symbol.position.y - dy)
        }
        SREG.model.fireModelUpdate()
    }
}
