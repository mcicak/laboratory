//
//  State.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/1/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class State: GenericState {
    func stateWillStart() {}
    
    func stateDidStart() {}
    
    func stateWillFinish() {}
    
    func stateDidFinish() {}
    
    func transformToUserSpace(point: CGPoint) -> CGPoint {
        return point.applying(SREG.context.transform.inverted())
    }
    
    func tapBegan(recognizer: UITapGestureRecognizer) {}
    
    func tapChanged(recognizer: UITapGestureRecognizer) {}
    
    func tapEnded(recognizer: UITapGestureRecognizer) {}
    
    func panBegan(recognizer: UIPanGestureRecognizer) {}
    
    func panChanged(recognizer: UIPanGestureRecognizer) {}
    
    func panEnded(recognizer: UIPanGestureRecognizer) {}
    
    func pinchBegan(recognizer: UIPinchGestureRecognizer) {}
    
    func pinchChanged(recognizer: UIPinchGestureRecognizer) {}
    
    func pinchEnded(recognizer: UIPinchGestureRecognizer) {}
}
