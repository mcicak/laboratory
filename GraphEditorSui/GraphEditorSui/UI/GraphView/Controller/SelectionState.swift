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
        
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {
        let position = transformToUserSpace(point: value.location, transform: viewModel.transform)
        let symbol = viewModel.symbolAt(point: position)
        
        if value.translation == .zero {
            if !selection.isEmpty {
                for s in selection.elements {
                    let handle = selectionHandler.getHandleForSymbol(symbol: s, point: position, scale: viewModel.transform.scale())
                    if handle != .none {
                        return nil
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
                        return nil
                    }
                }
            }
            
            if let _ = symbol {
                return MoveState()
            } else {
                print("GOTO LASO SELTION STATE")
            }
        }
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) -> GenericState? {
        var position = transformToUserSpace(point: value.location, transform: viewModel.transform)
        position -= CGPoint(x: 37, y: 37)
        
        if shouldAddNewSymbol {
            let newSymbol = Symbol(position: position, size: CGSize(width: 75, height: 75), type: .rectangle)
            viewModel.symbols.append(newSymbol)
        } else {
            shouldAddNewSymbol = true
        }
        return nil
    }
    
    override func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState? {
        ZoomState()
    }
}
