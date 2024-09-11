//
//  LasoSelectionState.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/13/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class LasoSelectionState: State {
    var startingPoint: CGPoint?
    
    override func stateDidStart() {
        SREG.context.laso = CGRect(x: -10, y: -10, width: 0, height: 0)
        SREG.selection.clearSelection()
        SREG.context.lasoOn = true
        startingPoint = SREG.context.lastPosition?.applying(SREG.context.transform)
    }
    
    override func stateDidFinish() {
        let toSelect = NSMutableArray()
        let lasoUserSpace = SREG.context.laso!.applying(SREG.context.transform.inverted())
        
        for symbol in (SREG.model.symbols as Any as! [Symbol]) {
            if lasoUserSpace.intersects(symbol.getAsRectangle()) {
                toSelect.add(symbol)
            }
        }
        SREG.selection.addMultipleSelection(symbols: toSelect)
        SREG.context.lasoOn = false
    }
    
    override func panBegan(recognizer: UIPanGestureRecognizer) {
        super.panBegan(recognizer: recognizer)
    }
    
    override func panChanged(recognizer: UIPanGestureRecognizer) {
        let newPosition: CGPoint = recognizer.location(in: SREG.context.graphView?.transitionView)
        
        SREG.context.laso = CGRect(x: startingPoint!.x, y: startingPoint!.y, width: newPosition.x - startingPoint!.x, height: newPosition.y - startingPoint!.y)
        SREG.context.graphView?.transitionView.setNeedsDisplay()
    }
    
    override func panEnded(recognizer: UIPanGestureRecognizer) {
        SREG.context.graphView?.setCurrentState(currentState: SelectionState())
    }
}
