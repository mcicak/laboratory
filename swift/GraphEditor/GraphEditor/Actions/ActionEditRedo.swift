//
//  ActionEditRedo.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/7/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionEditRedo: ActionAbstract {
    override func perform() {
        SREG.commandManager.doCommand()
    }
}
