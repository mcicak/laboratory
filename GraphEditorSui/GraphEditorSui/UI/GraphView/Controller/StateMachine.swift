//
//  StateMachine.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 2.9.24..
//

import Foundation
import SwiftUI

protocol GenericState {
    func stateWillStart()
    func stateDidStart()
    func stateWillFinish()
    func stateDidFinish()
    
    func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel)
    func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel)
    func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel)
    func magnifyEnded(viewModel: GraphViewModel)
    
    func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint
}

class GestureState {
    
    func stateWillStart() {}
    
    func stateDidStart() {}
    
    func stateWillFinish() {}
    
    func stateDidFinish() {}
    
    func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) {}
    
    func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) {}
    
    func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) {}
    
    func magnifyEnded(viewModel: GraphViewModel) {}
    
    func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint {
        point.applying(transform.inverted())
    }
}

@Observable
class GraphStateMachine {
    
    var viewModel: GraphViewModel
    var selectionModel: SelectionModel
    private var _currentState: GestureState = SelectionState()
    var currentState: GestureState {
        get {
            _currentState
        }
        set {
            let oldState = _currentState
            
            oldState.stateWillFinish()
            newValue.stateWillStart()
            
            _currentState = newValue
            
            oldState.stateDidFinish()
            newValue.stateDidStart()
        }
    }
    
    init(viewModel: GraphViewModel, selectionModel: SelectionModel) {
        self.viewModel = viewModel
        self.selectionModel = selectionModel
    }

    func dragChanged(value: DragGesture.Value) {
        currentState.dragChanged(value: value, viewModel: viewModel, selection: selectionModel)
    }
    
    func dragEnded(value: DragGesture.Value) {
        currentState.dragEnded(value: value, viewModel: viewModel)
    }
    
    func magnifyChanged(value: MagnifyGesture.Value) {
        currentState.magnifyChanged(value: value, viewModel: viewModel)
    }
    
    func magnifyEnded() {
        currentState.magnifyEnded(viewModel: viewModel)
    }
    
    private func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint {
        return point.applying(transform.inverted())
    }
}

