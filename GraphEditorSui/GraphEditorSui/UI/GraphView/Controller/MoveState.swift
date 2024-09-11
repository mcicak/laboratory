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
    
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> (any GenericState)? {
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
    
    override func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) -> (any GenericState)? {
        return SelectionState()
    }
}
