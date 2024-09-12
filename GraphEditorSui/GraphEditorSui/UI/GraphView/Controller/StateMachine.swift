//
//  StateMachine.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 2.9.24..
//

import Foundation
import SwiftUI

protocol GenericState {
    func stateWillStart(viewModel: GraphViewModel, selection: SelectionModel)
    func stateDidStart(viewModel: GraphViewModel, selection: SelectionModel)
    func stateWillFinish(viewModel: GraphViewModel, selection: SelectionModel)
    func stateDidFinish(viewModel: GraphViewModel, selection: SelectionModel)
    
    func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState?
    func dragEnded(value: DragGesture.Value, 
                   viewModel: GraphViewModel,
                   selection: SelectionModel,
                   commandManager: CommandManager) -> GenericState?
    func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState?
    func magnifyEnded(viewModel: GraphViewModel) -> GenericState?
    
    func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint
}

class GestureState: GenericState {
    
    func stateWillStart(viewModel: GraphViewModel, selection: SelectionModel) {}
    
    func stateDidStart(viewModel: GraphViewModel, selection: SelectionModel) {}
    
    func stateWillFinish(viewModel: GraphViewModel, selection: SelectionModel) {}
    
    func stateDidFinish(viewModel: GraphViewModel, selection: SelectionModel) {}
    
    func dragChanged(value: DragGesture.Value, viewModel: GraphViewModel, selection: SelectionModel) -> GenericState? {nil}
    
    func dragEnded(value: DragGesture.Value, 
                   viewModel: GraphViewModel,
                   selection: SelectionModel,
                   commandManager: CommandManager) -> GenericState? {nil}
    
    func magnifyChanged(value: MagnifyGesture.Value, viewModel: GraphViewModel) -> GenericState? {nil}
    
    func magnifyEnded(viewModel: GraphViewModel) -> GenericState? {nil}
    
    func transformToUserSpace(point: CGPoint, transform: CGAffineTransform) -> CGPoint {
        point.applying(transform.inverted())
    }
}

@Observable
class GraphStateMachine {
    
    var viewModel = GraphViewModel()
    var selectionModel = SelectionModel()
    var commandManager = CommandManager()
    
    private var _currentState: GenericState = SelectionState()
    var currentState: GenericState {
        get {
            _currentState
        }
        set {
            let oldState = _currentState
            
            oldState.stateWillFinish(viewModel: viewModel, selection: selectionModel)
            newValue.stateWillStart(viewModel: viewModel, selection: selectionModel)
            
            _currentState = newValue
            print("NEW STATE: \(newValue)")
            
            oldState.stateDidFinish(viewModel: viewModel, selection: selectionModel)
            newValue.stateDidStart(viewModel: viewModel, selection: selectionModel)
        }
    }

    func dragChanged(value: DragGesture.Value) {
        if let nextState = currentState.dragChanged(value: value, viewModel: viewModel, selection: selectionModel) {
            currentState = nextState
        }
    }
    
    func dragEnded(value: DragGesture.Value) {
        if let nextState = currentState.dragEnded(value: value, 
                                                  viewModel: viewModel,
                                                  selection: selectionModel,
                                                  commandManager: commandManager) {
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
