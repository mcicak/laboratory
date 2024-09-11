//
//  DragState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class DragState: GestureState {
    
    override func stateDidStart() {
        
    }
    
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) {
        // Adjust the translation by the current scale to ensure consistent movement
        let scaledTranslation = CGSize(
            width: value.translation.width / viewModel.transform.a,
            height: value.translation.height / viewModel.transform.d
        )
        
        // Apply the adjusted translation to the initial transform
        viewModel.transform = viewModel.initialTransform.translatedBy(x: scaledTranslation.width, y: scaledTranslation.height)
    }
    
    override func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) {
        viewModel.initialTransform = viewModel.transform
    }
    
    override func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) {
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
