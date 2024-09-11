//
//  ActionEditSelectAll.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/15/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionEditSelectAll: ActionAbstract {
    override func perform() {
        SREG.selection.addMultipleSelection(symbols: SREG.model.symbols)
    }
}
