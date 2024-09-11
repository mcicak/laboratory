//
//  SelectionHandleHandler.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/5/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class SelectionHandleHandler: GenericSelectionHandler {
    func paintSelectionHandle(context: CGContext, position: CGPoint) {
        let size: CGFloat = 16
        context.setFillColor(red: 0, green: 0, blue: 0, alpha: 1)
        let newPosition = position.applying(SREG.context.transform)
        context.fill(CGRect(x: newPosition.x - size / 2, y: newPosition.y - size / 2, width: size, height: size))
    }
    
    func getHandlePoint(topLeft: CGPoint, size: CGSize, handlePosition: SymbolHandle) -> CGPoint {
        var x: CGFloat = 0
        var y: CGFloat = 0
        
        if (handlePosition == .northWest ||
            handlePosition == .north ||
            handlePosition == .northEast) {
            y = topLeft.y
        }
        
        if (handlePosition == .east || handlePosition == .west) {
            y = topLeft.y + size.height / 2
        }
        
        if (handlePosition == .southWest ||
            handlePosition == .south ||
            handlePosition == .southEast) {
            y = topLeft.y + size.height
        }
        
        //determine x coordinate
        if (handlePosition == .northWest ||
            handlePosition == .west ||
            handlePosition == .southWest) {
            x = topLeft.x
        }
        
        if (handlePosition == .north || handlePosition == .south) {
            x = topLeft.x + size.width / 2
        }
        
        if (handlePosition == .northEast ||
            handlePosition == .east ||
            handlePosition == .southEast) {
            x = topLeft.x + size.width
        }
        
        return CGPoint(x: x, y: y)
    }
    
    func isPointInHandle(symbol: Symbol, point: CGPoint, handle: SymbolHandle, scale: CGFloat) -> Bool {
        let handleCenter: CGPoint = getHandlePoint(topLeft: symbol.position, size: symbol.size, handlePosition: handle)

        return abs(point.x - handleCenter.x) <= (44 / 2) / scale &&
            abs(point.y - handleCenter.y) <= (44 / 2) / scale
    }
    
    func paintSelectionHandles(context: CGContext, selection: SelectionModel, scale: CGFloat) {
        for symbol in (selection.selection as Any as! [Symbol]) {
            for symbolHandler in SymbolHandle.allCases {
                if symbolHandler != .none {
                    paintSelectionHandle(context: context, position: getHandlePoint(topLeft: symbol.position, size: symbol.size, handlePosition: symbolHandler))
                }
            }
        }
    }
    
    func getHandleForSymbol(symbol: Symbol, point: CGPoint, scale: CGFloat) -> SymbolHandle {
        for symbolHandler in SymbolHandle.allCases {
            if symbolHandler != .none {
                let value = isPointInHandle(symbol: symbol, point: point, handle: symbolHandler, scale: scale)
                if value {
                    return symbolHandler
                }
            }
        }
        return .none
    }
}
