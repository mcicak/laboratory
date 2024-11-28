//
//  ZoomState.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/19/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class ZoomState: State {
    var initialScale: CGFloat?
    
    override func stateDidStart() {
        initialScale = SREG.context.scale
    }
    
    override func pinchChanged(recognizer: UIPinchGestureRecognizer) {
        let middlePoint: CGPoint = recognizer.location(in: SREG.context.graphView)
        let scale: CGFloat = initialScale! + recognizer.scale - 1
        if scale < 0.1 {
            return
        }
        SREG.context.scale = scale
        
        let oldPosition: CGPoint = transformToUserSpace(point: middlePoint)
        SREG.context.transform = CGAffineTransform(scaleX: scale, y: scale)
        let newPosition: CGPoint = transformToUserSpace(point: middlePoint)
        SREG.context.transform = SREG.context.transform.translatedBy(x: newPosition.x - oldPosition.x, y: newPosition.y - oldPosition.y)
        SREG.model.fireModelUpdate()
    }
    
    override func pinchEnded(recognizer: UIPinchGestureRecognizer) {
        SREG.context.graphView?.setCurrentState(currentState: SelectionState())
    }
}
