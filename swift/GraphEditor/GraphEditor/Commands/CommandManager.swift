//
//  CommandManager.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/7/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class CommandManager {
    var commands: NSMutableArray
    var currentCommandIndex: Int
    var count: Int {
        return commands.count
    }
    
    init() {
        commands = NSMutableArray()
        currentCommandIndex = 0
    }
    
    func addCommand(command: Command) {
        while currentCommandIndex < commands.count {
            commands.removeObject(at: currentCommandIndex)
        }
        commands.add(command)
        doCommand()
    }
    
    func doCommand() {
        if currentCommandIndex < commands.count {
            let currentCommand = commands[currentCommandIndex] as! Command
            currentCommandIndex += 1
            currentCommand.doCommand()
        }
    }
    
    func undoCommand() {
        if currentCommandIndex > 0 {
            currentCommandIndex -= 1
            let currentCommand = commands[currentCommandIndex] as! Command
            currentCommand.undoCommand()
        }
    }
    
    func currentCommandIsFirst() -> Bool {
        return currentCommandIndex == 0
    }
    
    func currentCommandIsLast() -> Bool {
        return currentCommandIndex == commands.count
    }
    
    func isEmpty() -> Bool {
        return commands.count == 0
    }
}
