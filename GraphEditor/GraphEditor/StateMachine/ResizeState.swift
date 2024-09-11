//
//  ResizeState.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/6/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class ResizeState: State {
    var handle: SymbolHandle?
    var oldPosition: CGPoint?
    var newPosition: CGPoint?
    var initialDimensions: CGRect?
    let minimumSize: CGFloat = 100
    
    override func stateDidStart() {
        guard let symbolPos = SREG.context.symbolHit?.position else { return }
        guard let lastPosition = SREG.context.lastPosition else { return }
        initialDimensions = SREG.context.symbolHit?.getAsRectangle()
        
        switch self.handle! {
        case .northWest:
            newPosition = symbolPos
            break
        case .west:
            newPosition = CGPoint(x: symbolPos.x, y: lastPosition.y)
            break
        case .southWest:
            newPosition = CGPoint(x: symbolPos.x, y: lastPosition.y)
            break
        case .north:
            newPosition = CGPoint(x: lastPosition.x, y: symbolPos.y)
            break
        case .northEast:
            newPosition = CGPoint(x: lastPosition.x, y: symbolPos.y)
            break
        default:
            newPosition = SREG.context.lastPosition
        }
    }
    
    override func panChanged(recognizer: UIPanGestureRecognizer) {
        guard let selected = SREG.context.symbolHit else { return }
        oldPosition = newPosition
        newPosition = transformToUserSpace(point: recognizer.location(in: SREG.context.graphView))
        
        let dx = newPosition!.x - oldPosition!.x
        let dy = newPosition!.y - oldPosition!.y
        
        switch self.handle! {
        case .northWest:
            if (selected.size.width < minimumSize && dx > 0) || (selected.size.height < minimumSize && dy > 0) {
                selected.size = CGSize(width: selected.size.width, height: selected.size.height)
            } else {
                selected.position = self.newPosition!
                selected.size = CGSize(width: selected.size.width - dx, height: selected.size.height - dy)
            }
            break
            
        case .north:
            if selected.size.height < minimumSize && dy > 0 {
                selected.size = CGSize(width: selected.size.width, height: selected.size.height)
                
            } else {
                selected.position = CGPoint(x: selected.position.x, y: self.newPosition!.y)
                selected.size = CGSize(width: selected.size.width, height: selected.size.height - dy)
            }
            break
            
        case .northEast:
            if (selected.size.width < minimumSize && dx < 0) || (selected.size.height < minimumSize && dy > 0) {
                selected.size = CGSize(width: selected.size.width, height: selected.size.height)
            } else {
                selected.position = CGPoint(x: selected.position.x, y: self.newPosition!.y)
                selected.size = CGSize(width: selected.size.width + dx, height: selected.size.height - dy)
            }
            break
            
        case .east:
            if selected.size.width < minimumSize && dx < 0 {
                return
            }
            selected.size = CGSize(width: selected.size.width + dx, height: selected.size.height)
            break
            
        case .southEast:
            if (selected.size.width < minimumSize && dx < 0) || (selected.size.height < minimumSize && dy < 0) {
                return
            }
            selected.size = CGSize(width: selected.size.width + dx, height: selected.size.height + dy)
            break
            
        case .south:
            if selected.size.height < minimumSize && dy < 0 {
                return
            }
            selected.size = CGSize(width: selected.size.width, height: selected.size.height + dy)
            break
            
        case .southWest:
            if (selected.size.width < minimumSize && dx > 0) || (selected.size.height < minimumSize && dy < 0) {
                selected.size = CGSize(width: selected.size.width, height: selected.size.height)
            } else {
                selected.position = CGPoint(x: self.newPosition!.x, y: selected.position.y)
                selected.size = CGSize(width: selected.size.width - dx, height: selected.size.height + dy)
            }
            break
            
        case .west:
            if selected.size.width < minimumSize && dx > 0 {
                selected.size = CGSize(width: selected.size.width, height: selected.size.height)
            } else {
                selected.position = CGPoint(x: self.newPosition!.x, y: selected.position.y)
                selected.size = CGSize(width: selected.size.width - dx, height: selected.size.height)
            }
            break
            
        default:
            return
        }
        SREG.model.fireModelUpdate()
    }
    
    override func panEnded(recognizer: UIPanGestureRecognizer) {
        let resizeCommand = CommandResizeSymbol(symbol: SREG.context.symbolHit!, startDimensions: initialDimensions!, endDimensions: (SREG.context.symbolHit?.getAsRectangle())!)
        SREG.commandManager.addCommand(command: resizeCommand)
        SREG.context.graphView?.setCurrentState(currentState: SelectionState())
    }
}
