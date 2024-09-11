//
//  GenericSelectionHandler.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/5/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

enum SymbolHandle: CaseIterable {
    case none, north, south, east, west, southEast, southWest, northEast, northWest
}

protocol GenericSelectionHandler {
    func paintSelectionHandle(context: CGContext, position: CGPoint)
    func getHandlePoint(topLeft: CGPoint, size: CGSize, handlePosition: SymbolHandle) -> CGPoint
    func isPointInHandle(symbol: Symbol, point: CGPoint, handle: SymbolHandle, scale: CGFloat) -> Bool
    func paintSelectionHandles(context: CGContext, selection: SelectionModel, scale: CGFloat)
    func getHandleForSymbol(symbol: Symbol, point: CGPoint, scale: CGFloat) -> SymbolHandle
}

