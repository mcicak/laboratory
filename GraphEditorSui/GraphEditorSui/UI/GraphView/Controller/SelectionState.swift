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
    private var shouldAddNewSymbol = true
    private var freshlySelected = false
    private var symbolHit: Symbol?
        
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {
        let position = transformToUserSpace(point: value.location, transform: viewModel.transform)
        symbolHit = viewModel.symbolAt(point: position)
        
        if value.translation == .zero {
            if !selection.isEmpty {
                for s in selection.elements {
                    let handle = selectionHandler.getHandleForSymbol(symbol: s, point: position, scale: viewModel.transform.scale())
                    if handle != .none {
                        return nil
                    }
                }
            }
            
            if let symbol = symbolHit {
                if !selection.elements.contains(symbol) {
                    shouldAddNewSymbol = false
                    freshlySelected = true
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
                        return ResizeState(s, handle)
                    }
                }
            }
            
            if let _ = symbolHit {
                return MoveState()
            } else {
                return LasoSelectionState()
            }
        }
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, 
                            viewModel: GraphViewModel,
                            selection: SelectionModel,
                            commandManager: CommandManager) -> GenericState? {
        var position = transformToUserSpace(point: value.location, transform: viewModel.transform)
        
        if let symbol = symbolHit {
            if !freshlySelected {
                selection.removeFromSelection(symbol: symbol)
                return nil
            }
        }
        freshlySelected = false
        
        if shouldAddNewSymbol {
            position -= CGPoint(x: 37, y: 37)
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
