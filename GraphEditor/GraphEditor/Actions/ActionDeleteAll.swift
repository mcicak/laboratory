//
//  ActionDeleteAll.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/20/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionDeleteAll: ActionAbstract {
    override func perform() {
        let deleteCommand = CommandDeleteSymbols(symbols: SREG.model.symbols)
        SREG.commandManager.addCommand(command: deleteCommand)
    }
}
