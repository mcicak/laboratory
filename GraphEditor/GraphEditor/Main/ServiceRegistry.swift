//
//  ServiceRegistry.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/1/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ServiceRegistry {
    let model: Model
    let context: Context
    let selection: SelectionModel
    let commandManager: CommandManager
    let actionManager: ActionManager
    let clipboard: Clipboard
    
    init() {
        model = Model()
        context = Context()
        selection = SelectionModel()
        commandManager = CommandManager()
        actionManager = ActionManager()
        clipboard = Clipboard()
    }
}
