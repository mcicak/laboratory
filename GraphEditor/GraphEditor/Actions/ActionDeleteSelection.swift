//
//  ActionDeleteSelection.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/14/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionDeleteSelection: ActionAbstract {
    override func perform() {
        let deleteCommand = CommandDeleteSymbols(symbols: SREG.selection.elements)
        SREG.commandManager.addCommand(command: deleteCommand)
    }
}
