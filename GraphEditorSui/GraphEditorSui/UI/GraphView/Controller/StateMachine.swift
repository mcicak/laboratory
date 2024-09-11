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
    
    func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState?
    func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) -> GenericState?
    func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState?
    func magnifyEnded(viewModel: GraphViewModel) -> GenericState?
    
    func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint
}

class GestureState: GenericState {
    
    func stateWillStart() {}
    
    func stateDidStart() {}
    
    func stateWillFinish() {}
    
    func stateDidFinish() {}
    
    func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {nil}
    
    func dragEnded(value: DragGesture.Value, viewModel: GraphViewModel) -> GenericState? {nil}
    
    func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState? {nil}
    
    func magnifyEnded(viewModel: GraphViewModel) -> GenericState? {nil}
    
    func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint {
        point.applying(transform.inverted())
    }
}

@Observable
class GraphStateMachine {
    
    var viewModel: GraphViewModel
    var selectionModel: SelectionModel
    private var _currentState: GenericState = SelectionState()
    var currentState: GenericState {
        get {
            _currentState
        }
        set {
            let oldState = _currentState
            
            oldState.stateWillFinish()
            newValue.stateWillStart()
            
            _currentState = newValue
            print("NEW STATE: \(newValue)")
            
            oldState.stateDidFinish()
            newValue.stateDidStart()
        }
    }
    
    init(viewModel: GraphViewModel, selectionModel: SelectionModel) {
        self.viewModel = viewModel
        self.selectionModel = selectionModel
    }

    func dragChanged(value: DragGesture.Value) {
        if let nextState = currentState.dragChanged(value: value, viewModel: viewModel, selection: selectionModel) {
            currentState = nextState
        }
    }
    
    func dragEnded(value: DragGesture.Value) {
        if let nextState = currentState.dragEnded(value: value, viewModel: viewModel) {
            currentState = nextState
        }
    }
    
    func magnifyChanged(value: MagnifyGesture.Value) {
        if let nextState = currentState.magnifyChanged(value: value, viewModel: viewModel) {
            currentState = nextState
        }
    }
    
    func magnifyEnded() {
        if let nextState = currentState.magnifyEnded(viewModel: viewModel) {
            currentState = nextState
        }
    }
    
    private func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint {
        return point.applying(transform.inverted())
    }
}
