//
//  ActionEditPaste.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/15/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class ActionEditPaste: ActionAbstract {
    override func perform() {
        let pastedElements: NSMutableArray = SREG.clipboard.content
        let duplicatedElements = NSMutableArray()
        
        let pastedElementsArray = pastedElements as! [Symbol]
        for symbol in pastedElementsArray {
            duplicatedElements.add(copy(symbol))
        }
        
        let pasteCommand = CommandAddSymbols(symbols: duplicatedElements)
        SREG.commandManager.addCommand(command: pasteCommand)
        SREG.selection.addMultipleSelection(symbols: duplicatedElements)
        SREG.clipboard.clear()
    }
    
    private func copy<T: Symbol>(_ symbol: T) -> T {
        let copiedSymbol = symbol.copy() as! T
        let originalPosition = symbol.position
        copiedSymbol.position = CGPoint(x: originalPosition.x + 50, y: originalPosition.y + 50)
        return copiedSymbol
    }
}
