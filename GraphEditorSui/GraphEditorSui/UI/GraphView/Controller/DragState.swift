//
//  DragState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class DragState: GestureState {
        
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {
        // Adjust the translation by the current scale to ensure consistent movement
        let scaledTranslation = CGSize(
            width: value.translation.width / viewModel.transform.a,
            height: value.translation.height / viewModel.transform.d
        )
        
        // Apply the adjusted translation to the initial transform
        viewModel.transform = viewModel.initialTransform.translatedBy(x: scaledTranslation.width, y: scaledTranslation.height)
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, graph: GraphStateMachine) -> GenericState? {
        graph.viewModel.initialTransform = graph.viewModel.transform
        return nil
    }
    
    override func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState? {
        return ZoomState()
    }
}
