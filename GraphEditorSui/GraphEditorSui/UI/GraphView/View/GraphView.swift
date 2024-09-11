//
//  GraphView.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 10.9.24..
//

import Foundation
import SwiftUI

struct GraphView: View {
    
    @Binding var viewModel: GraphViewModel
    let selectionHandler = SelectionHandleHandler()
    @Bindable var stateMachine: GraphStateMachine
    
    var body: some View {
        Canvas { context, size in
            
            // either set transform on entire drawing context,
            // or use it when drawing elements to calculate proper positions
            context.transform = viewModel.transform
            
            for symbol in viewModel.symbols {
                let rect = CGRect(origin: symbol.position, size: symbol.size)
                
                switch symbol.type {
                case .rectangle:
                    context.stroke(Path(rect), with: .color(.blue), lineWidth: 3)
                    context.fill(Path(rect), with: .color(.green))
                case .oval:
                    context.stroke(Path(ellipseIn: rect), with: .color(.blue), lineWidth: 3)
                    context.fill(Path(ellipseIn: rect), with: .color(.green))
                }
            }
            
            selectionHandler.paintSelectionHandles(context: context,
                                                   selection: stateMachine.selectionModel,
                                                   transform: viewModel.transform)
        }
        .gesture(
            DragGesture(minimumDistance: 0)
                .onChanged { value in
                    stateMachine.dragChanged(value: value)
                }
                .onEnded { value in
                    print("gesture end")
                    stateMachine.dragEnded(value: value)
                }
        )
        .gesture(
            MagnifyGesture()
                .onChanged { value in
                    print("magnification update")
                    
                    stateMachine.magnifyChanged(value: value)
                }
                .onEnded { _ in
                    print("magnification end")
                    stateMachine.magnifyEnded()
                }
        )
        //.background(Color.yellow)
    }
}
