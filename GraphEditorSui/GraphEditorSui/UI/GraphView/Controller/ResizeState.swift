//
//  ResizeState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 12.9.24..
//

import Foundation
import SwiftUI

class ResizeState: GestureState {
    
    let symbol: Symbol
    let handle: SymbolHandle
    let oldFrame: CGRect
    
    init(_ symbol: Symbol, _ handle: SymbolHandle) {
        self.symbol = symbol
        self.handle = handle
        self.oldFrame = symbol.asRectangle
    }
    
    let minimumSize: CGFloat = 50
    var newRect: CGRect?
    
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> (any GenericState)? {
        let location = transformToUserSpace(point: value.location, transform: viewModel.transform)
        let oldPosition = oldFrame.origin

        switch handle {
        case .north:
            // Adjust height and move the top side up or down
            let newHeight = oldFrame.size.height + oldPosition.y - location.y
            if newHeight > minimumSize {
                symbol.position.y = location.y
                symbol.size.height = newHeight
            }

        case .south:
            let newHeight = location.y - oldPosition.y
            symbol.size.height = max(newHeight, minimumSize)

        case .west:
            let newWidth = oldFrame.size.width + oldPosition.x - location.x
            if newWidth > minimumSize {
                symbol.position.x = location.x
                symbol.size.width = newWidth
            }

        case .east:
            // Adjust width and expand the right side
            let newWidth = location.x - oldPosition.x
            symbol.size.width = max(newWidth, minimumSize)

        case .southEast:
            // Adjust both width and height from the bottom-right corner
            let newWidth = location.x - oldPosition.x
            let newHeight = location.y - oldPosition.y
            symbol.size.width = max(newWidth, minimumSize)
            symbol.size.height = max(newHeight, minimumSize)

        case .southWest:
            // Adjust width from the left side and height from the bottom side
            let newWidth = oldFrame.size.width + oldPosition.x - location.x
            let newHeight = location.y - oldPosition.y
            if newWidth > minimumSize {
                symbol.position.x = location.x
                symbol.size.width = newWidth
            }
            symbol.size.height = max(newHeight, minimumSize)

        case .northEast:
            // Adjust width from the right side and height from the top side
            let newWidth = location.x - oldPosition.x
            let newHeight = oldFrame.size.height + oldPosition.y - location.y
            if newHeight > minimumSize {
                symbol.position.y = location.y
                symbol.size.height = newHeight
            }
            symbol.size.width = max(newWidth, minimumSize)

        case .northWest:
            // Adjust width from the left side and height from the top side
            let newWidth = oldFrame.size.width + oldPosition.x - location.x
            let newHeight = oldFrame.size.height + oldPosition.y - location.y
            if newWidth > minimumSize {
                symbol.position.x = location.x
                symbol.size.width = newWidth
            }
            
            if newHeight > minimumSize {
                symbol.position.y = location.y
                symbol.size.height = newHeight
            }

        case .none:
            break
        }
        
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, 
                            viewModel: GraphViewModel,
                            selection: SelectionModel,
                            commandManager: CommandManager) -> (any GenericState)? {
        // TODO: add resize command
        return SelectionState()
    }
}
