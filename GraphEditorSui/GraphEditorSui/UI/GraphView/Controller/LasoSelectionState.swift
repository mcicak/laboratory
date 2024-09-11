//
//  LasoSelectionState.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 11.9.24..
//

import Foundation
import SwiftUI

class LasoSelectionState: GestureState {
    
    private var startPoint: CGPoint = .zero
    
    override func stateDidStart(viewModel: GraphViewModel, selection: SelectionModel) {
        startPoint = .zero
        viewModel.lasoRect = CGRect.zero
        viewModel.isLasoOn = true
    }
    
    override func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {
        if startPoint == .zero {
            startPoint = transformToUserSpace(point: value.startLocation, transform: viewModel.transform)
        }
        
        // Calculate the current point based on drag gesture location
        let currentPoint = transformToUserSpace(point: value.location, transform: viewModel.transform)
        
        
        // Calculate the lasso rect based on the start point and current drag position
        let origin = CGPoint(
            x: min(startPoint.x, currentPoint.x),
            y: min(startPoint.y, currentPoint.y)
        )
        
        let size = CGSize(
            width: abs(currentPoint.x - startPoint.x),
            height: abs(currentPoint.y - startPoint.y)
        )
        
        // Update the lasso rect in the viewModel
        viewModel.lasoRect = CGRect(origin: origin, size: size)
        
        return nil
    }
    
    override func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {
        // End the lasso selection
        viewModel.isLasoOn = false
        
        // Check which elements are within the lasso rectangle
        let lasoRect = viewModel.lasoRect
        
        // Filter elements that are inside or intersect with the lasso rect
        let selectedElements = viewModel.symbols.filter { element in
            return element.asRectangle.intersects(lasoRect)
        }
        
        selection.addMultipleSelection(symbols: selectedElements)
        return SelectionState()
    }
}
