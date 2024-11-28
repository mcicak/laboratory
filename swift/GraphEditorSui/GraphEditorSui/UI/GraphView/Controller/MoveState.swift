//
//  MoveState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class MoveState: GestureState {
    
    private var initialPositions: [UUID: CGPoint] = [:]
    
    override func stateDidStart(viewModel: GraphViewModel, selection: SelectionModel) {
        for element in selection.elements {
            initialPositions[element.id] = element.position
        }
    }
    
    override func dragChanged(value: DragGesture.Value, 
                              viewModel: GraphViewModel,
                              selection: SelectionModel) -> (any GenericState)? {
        let scale = viewModel.transform.scale()
        let translation = CGSize(
            width: value.translation.width / scale,
            height: value.translation.height / scale
        )
        
        selection.elements.forEach { element in
            if let initialPosition = initialPositions[element.id] {
                element.position = CGPoint(
                    x: initialPosition.x + translation.width,
                    y: initialPosition.y + translation.height
                )
            }
        }
        
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, graph: GraphStateMachine) -> GenericState? {
        guard let symbol = graph.selectionModel.elements.first,
              let startPosition = initialPositions[symbol.id] else {
            return SelectionState()
        }
        
        let endPosition = symbol.position
        let delta: CGPoint = endPosition - startPosition
        
        let moveCommand = MoveSymbolsCommand(symbols: Set(graph.selectionModel.elements), delta: delta)
        graph.commandManager.addCommand(command: moveCommand, graph: graph)
        return SelectionState()
    }
}
