//
//  CommandManager.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 12.9.24..
//

import Foundation

protocol Command {
    func doCommand()
    func undoCommand()
}

class CommandManager {
    
    var commands = [Command]()
    
    var currentCommandIndex = 0
    
    var count: Int {
        commands.count
    }
    
    var isEmpty: Bool {
        count == 0
    }
    
    func addCommand(command: Command) {
        while currentCommandIndex < commands.count {
            commands.remove(at: currentCommandIndex)
        }
        commands.append(command)
        doCommand()
    }
    
    func doCommand() {
        if currentCommandIndex < commands.count {
            let currentCommand = commands[currentCommandIndex]
            currentCommandIndex += 1
            currentCommand.doCommand()
        }
    }
    
    func undoCommand() {
        if currentCommandIndex > 0 {
            currentCommandIndex -= 1
            let currentCommand = commands[currentCommandIndex]
            currentCommand.undoCommand()
        }
    }
    
    func currentCommandIsFirst() -> Bool {
        return currentCommandIndex == 0
    }
    
    func currentCommandIsLast() -> Bool {
        return currentCommandIndex == commands.count
    }
}
