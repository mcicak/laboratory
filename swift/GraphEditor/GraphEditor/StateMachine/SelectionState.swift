//
//  SelectionState.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/1/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class SelectionState: State {
    private var freshlySelected: Bool = false
    private var freeToAdd: Bool = true
    private var graphView: GraphView? {
       return SREG.context.graphView
    }
    var model: Model {
        return SREG.model
    }
    var count: String {
        return String(SREG.model.symbols.count)
    }
    let selectionHandler = SelectionHandleHandler()
    
    override func stateDidStart() {
        freshlySelected = false
        freeToAdd = true
    }
    
    override func stateDidFinish() {}
    
    override func tapBegan(recognizer: UITapGestureRecognizer) {
        let position = recognizer.location(in: graphView)
        SREG.context.lastPosition = transformToUserSpace(point: position)
        SREG.context.symbolHit = ModelHelper.symbolAtPoint(SREG.context.lastPosition!)
        
        if SREG.selection.count > 0 {
            for symbol in (SREG.selection.elements as Any as! [Symbol]) {
                let handle = selectionHandler.getHandleForSymbol(symbol: symbol, point: SREG.context.lastPosition!, scale: 1)
                if handle != .none {
                    return
                }
            }
            if SREG.context.symbolHit == nil {
                freeToAdd = false
                SREG.selection.clearSelection()
                return
            }
        }
        
        if SREG.context.symbolHit != nil {
            if !SREG.selection.elements.contains(SREG.context.symbolHit!) {
                freeToAdd = false
                SREG.selection.addToSelection(symbol: SREG.context.symbolHit!)
                freshlySelected = true
            }
            return
        }
    }
    
    override func tapChanged(recognizer: UITapGestureRecognizer) {}
    
    override func pinchBegan(recognizer: UIPinchGestureRecognizer) {
        SREG.context.graphView?.setCurrentState(currentState: ZoomState())
    }
    
    override func tapEnded(recognizer: UITapGestureRecognizer) {
        let position = recognizer.location(in: graphView)
        
        if !freshlySelected && SREG.context.symbolHit != nil {
            SREG.selection.removeFromSelection(symbol: SREG.context.symbolHit!)
            return
        }
        freshlySelected = false
        
        if freeToAdd {
            addNewSymbol(position)
        } else {
            freeToAdd = true
        }
    }
    
    override func panBegan(recognizer: UIPanGestureRecognizer) {
        if SREG.selection.count > 0 {
            for symbol in (SREG.selection.elements as Any as! [Symbol]) {
                let handle = selectionHandler.getHandleForSymbol(symbol: symbol, point: SREG.context.lastPosition!, scale: SREG.context.scale)
                if handle != .none {
                    SREG.context.symbolHit = symbol
                    // TODO: move to resize state
                    let resizeState = ResizeState()
                    resizeState.handle = handle
                    SREG.context.graphView?.setCurrentState(currentState: resizeState)
                    return
                }
            }
        }
        
        if SREG.context.symbolHit == nil {
            // move to lasoSelectionState
            SREG.context.graphView?.setCurrentState(currentState: LasoSelectionState())
        } else {
            SREG.context.graphView?.setCurrentState(currentState: MoveState())
        }
    }
}

// MARK: - Private func
extension SelectionState {
    private func addNewSymbol(_ position: CGPoint) {
        let symbol = SymbolFactory.createSymbol()
        SREG.commandManager.addCommand(command: CommandAddSymbols(symbols: [symbol]))
    }
}
