//
//  CommandManager.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 12.9.24..
//

import Foundation

protocol Command {
    func doCommand(_ graph: GraphStateMachine)
    func undoCommand(_ graph: GraphStateMachine)
}

@Observable
class CommandManager {
    
    var commands = [Command]()
    
    var currentCommandIndex = 0

    var count: Int { commands.count }
    var isEmpty: Bool { count == 0 }
    var currentCommandIsFirst: Bool { currentCommandIndex == 0 }
    var currentCommandIsLast: Bool { currentCommandIndex == commands.count }
    
    func addCommand(command: Command, graph: GraphStateMachine) {
        while currentCommandIndex < commands.count {
            commands.remove(at: currentCommandIndex)
        }
        commands.append(command)
        doCommand(graph)
    }
    
    func doCommand(_ graph: GraphStateMachine) {
        if currentCommandIndex < commands.count {
            let currentCommand = commands[currentCommandIndex]
            currentCommandIndex += 1
            currentCommand.doCommand(graph)
        }
    }
    
    func undoCommand(_ graph: GraphStateMachine) {
        if currentCommandIndex > 0 {
            currentCommandIndex -= 1
            let currentCommand = commands[currentCommandIndex]
            currentCommand.undoCommand(graph)
        }
    }
}
