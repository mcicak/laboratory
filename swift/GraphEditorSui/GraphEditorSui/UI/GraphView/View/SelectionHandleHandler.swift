//
//  SelectionHandleHandler.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 10.9.24..
//

import Foundation
import SwiftUI

enum SymbolHandle: CaseIterable {
    case none, north, south, east, west, southEast, southWest, northEast, northWest
}

protocol GenericSelectionHandler {
    func paintSelectionHandles(context: GraphicsContext, selection: Set<Symbol>, transform: CGAffineTransform)
    func paintSelectionHandle(context: GraphicsContext, position: CGPoint, transform: CGAffineTransform)
    func getHandlePoint(topLeft: CGPoint, size: CGSize, handlePosition: SymbolHandle) -> CGPoint
    func isPointInHandle(symbol: Symbol, point: CGPoint, handle: SymbolHandle, scale: CGFloat) -> Bool
    func getHandleForSymbol(symbol: Symbol, point: CGPoint, scale: CGFloat) -> SymbolHandle
}

class SelectionHandleHandler: GenericSelectionHandler {
    
    func paintSelectionHandles(context: GraphicsContext, selection: Set<Symbol>, transform: CGAffineTransform) {
        for symbol in selection {
            SymbolHandle.allCases
                .filter { $0 != .none }
                .forEach { symbolHandler in
                    let position = getHandlePoint(topLeft: symbol.position, size: symbol.size, handlePosition: symbolHandler)
                    paintSelectionHandle(context: context, position: position, transform: transform)
                }
        }
    }
    
    func paintSelectionHandle(context: GraphicsContext, position: CGPoint, transform: CGAffineTransform) {
        let size: CGFloat = 16
        
        let handleSize = size / transform.scale()  // Adjust size to compensate for scaling
        let rect = CGRect(x: position.x - handleSize / 2, y: position.y - handleSize / 2, width: handleSize, height: handleSize)
        context.fill(Path(roundedRect: rect, cornerSize: CGSizeZero), with: .color(.black))
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

        return abs(point.x - handleCenter.x) <= (16 / 2) / scale &&
            abs(point.y - handleCenter.y) <= (16 / 2) / scale
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
