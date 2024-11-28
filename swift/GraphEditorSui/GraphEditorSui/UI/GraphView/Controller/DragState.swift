//
//  DragState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class DragState: GestureState {
    
    var initialTransform: CGAffineTransform!
    let zoomState = ZoomState()
    var magnifyStarted = false
    
    override func stateWillStart(viewModel: GraphViewModel, selection: SelectionModel) {
        self.initialTransform = viewModel.transform
    }
        
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {
        if value.translation == .zero {
            initialTransform = viewModel.transform
        }
        
        // Adjust the translation by the current scale to ensure consistent movement
        let scaledTranslation = CGSize(
            width: value.translation.width / viewModel.transform.a,
            height: value.translation.height / viewModel.transform.d
        )
        
        // Apply the adjusted translation to the initial transform
        viewModel.transform = initialTransform.translatedBy(x: scaledTranslation.width, y: scaledTranslation.height)
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, graph: GraphStateMachine) -> GenericState? {
        initialTransform = graph.viewModel.transform
        return nil
    }
    
    override func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState? {
        if !magnifyStarted {
            magnifyStarted = true
            zoomState.initialTransform = viewModel.transform
        }
        
        let _ = zoomState.magnifyChanged(value: value, viewModel: viewModel)
        return nil
    }
    
    override func magnifyEnded(viewModel: GraphViewModel) -> GenericState? {
        magnifyStarted = false
        return DragState()
    }
}
