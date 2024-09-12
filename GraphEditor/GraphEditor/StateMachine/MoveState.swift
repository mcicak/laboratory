//
//  MoveState.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/13/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class MoveState: State {
    var oldPosition: CGPoint?
    var newPosition: CGPoint?
    var totalX: CGFloat?
    var totalY: CGFloat?
    let symbols = SREG.selection.elements
    
    override func stateDidStart() {
        newPosition = SREG.context.lastPosition
        totalX = 0.0
        totalY = 0.0
    }
    
    override func panChanged(recognizer: UIPanGestureRecognizer) {
        oldPosition = newPosition
        let point: CGPoint = recognizer.location(in: SREG.context.graphView)
        newPosition = transformToUserSpace(point: point)
        
        let dx = newPosition!.x - oldPosition!.x
        let dy = newPosition!.y - oldPosition!.y
        
        totalX! += dx
        totalY! += dy
        
        for symbol in (SREG.selection.elements as Any as! [Symbol]) {
            symbol.position = CGPoint(x: symbol.position.x + dx, y: symbol.position.y + dy)
        }
        
        SREG.model.fireModelUpdate()
    }
    
    override func panEnded(recognizer: UIPanGestureRecognizer) {
        let moveCommand = CommandMoveSymbols(symbols: SREG.selection.elements, deltaX: totalX!, deltaY: totalY!)
        SREG.commandManager.addCommand(command: moveCommand)
        SREG.context.graphView?.setCurrentState(currentState: SelectionState())
    }
}

