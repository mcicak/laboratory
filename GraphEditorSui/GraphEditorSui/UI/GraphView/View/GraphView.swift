//
//  GraphView.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 10.9.24..
//

import Foundation
import SwiftUI

struct LasoSelectionView: View {
    
    let transform: CGAffineTransform
    let rect: CGRect
    
    var body: some View {
        Canvas { context, size in
            let transformedRect = rect.applying(transform)
            
            let path = Path { p in
                p.addRect(transformedRect)
            }
            
            let dashPattern: [CGFloat] = [5, 3] // dash length, gap length
            
            let strokeStyle = StrokeStyle(
                lineWidth: 2,
                dash: dashPattern,
                dashPhase: 0
            )
            
            context.stroke(path, with: .color(.blue), style: strokeStyle)
        }
    }
}

struct GraphView: View {
    
    let symbols: [Symbol]
    let selection: Set<Symbol>
    let transform: CGAffineTransform
    let stateMachine: GraphStateMachine
    let selectionHandler = SelectionHandleHandler()
    
    var body: some View {
        Canvas { context, size in
            // either set transform on entire drawing context,
            // or use it when drawing elements to calculate proper positions
            context.transform = transform
            
            for symbol in symbols {
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
                                                   selection: selection,
                                                   transform: transform)
        }
        .gesture(
            DragGesture(minimumDistance: 0)
                .onChanged { stateMachine.dragChanged(value: $0) }
                .onEnded { stateMachine.dragEnded(value: $0) }
        )
        .gesture(
            MagnifyGesture()
                .onChanged { stateMachine.magnifyChanged(value: $0) }
                .onEnded { _ in stateMachine.magnifyEnded() }
        )
    }
}
