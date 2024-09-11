//
//  SelectionState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class SelectionState: GestureState {
    
    let selectionHandler = SelectionHandleHandler()
    var shouldAddNewSymbol = true
    
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) {
        let position = transformToUserSpace(point: value.location, transform: viewModel.transform)
        let symbol = viewModel.symbolAt(point: position)
        
        if value.translation == .zero {
            if !selection.isEmpty {
                for s in selection.elements {
                    let handle = selectionHandler.getHandleForSymbol(symbol: s, point: position, scale: viewModel.transform.scale())
                    if handle != .none {
                        return
                    }
                }
            }
            
            if let symbol = symbol {
                if !selection.elements.contains(symbol) {
                    shouldAddNewSymbol = false
                    selection.addToSelection(symbol: symbol)
                }
            } else {
                if !selection.isEmpty {
                    shouldAddNewSymbol = false
                    selection.clearSelection()
                }
            }
        } else {
            if !selection.isEmpty {
                for s in selection.elements {
                    let handle = selectionHandler.getHandleForSymbol(symbol: s, point: position, scale: viewModel.transform.scale())
                    if handle != .none {
                        print("GOTO RESIZE STATE")
                        return
                    }
                }
            }
            
            if let symbol = symbol {
                print("GOTO MOVE ELEMENTS STATE")
            } else {
                print("GOTO LASO SELTION STATE")
            }
        }
    }
    
    override func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) {
        var position = transformToUserSpace(point: value.location, transform: viewModel.transform)
        position -= CGPoint(x: 37, y: 37)
        
        if shouldAddNewSymbol {
            let newSymbol = Symbol(position: position, size: CGSize(width: 75, height: 75), type: .rectangle)
            viewModel.symbols.append(newSymbol)
        } else {
            shouldAddNewSymbol = true
        }
    }
    
    override func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) {
        print("GOTO ZOOM STATE")
        
        let middlePoint = value.startLocation
        
        // Calculate the new scale based on the magnification gesture
        let scale = viewModel.transform.a * value.magnification
        if scale < 0.3 { return }
        
        // Calculate the old position before applying the new scale
        let oldPosition = transformToUserSpace(point: middlePoint, transform: viewModel.transform)
        
        // Update the transform with the new scale
        var newTransform = viewModel.initialTransform.scaledBy(x: value.magnification, y: value.magnification)
        
        // Calculate the new position after scaling
        let newPosition = transformToUserSpace(point: middlePoint, transform: newTransform)
        
        // Adjust the transform to keep the middle point in place
        newTransform = newTransform.translatedBy(x: newPosition.x - oldPosition.x, y: newPosition.y - oldPosition.y)
        
        // Update the view model with the new transform
        viewModel.transform = newTransform
    }
    
    override func magnifyEnded(viewModel: GraphViewModel) {
        viewModel.initialTransform = viewModel.transform
    }
}
