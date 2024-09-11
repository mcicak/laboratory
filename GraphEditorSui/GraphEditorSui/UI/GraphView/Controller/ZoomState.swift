//
//  ZoomState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class ZoomState: GestureState {
    
    override func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState? {
        let middlePoint = value.startLocation
        
        // Calculate the new scale based on the magnification gesture
        let scale = viewModel.transform.a * value.magnification
        if scale < 0.3 { return nil }
        
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
        return nil
    }
    
    override func magnifyEnded(viewModel: GraphViewModel) -> GenericState? {
        viewModel.initialTransform = viewModel.transform
        return SelectionState()
    }
}
