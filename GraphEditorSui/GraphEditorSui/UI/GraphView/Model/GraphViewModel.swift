//
//  GraphViewModel.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 10.9.24..
//

import Foundation

enum SymbolType {
    case rectangle
    case oval
}

@Observable
class GraphViewModel: ObservableObject {
    var symbols: [Symbol] = []
    var transform: CGAffineTransform = .identity
    
    var initialTransform: CGAffineTransform = .identity
    var initialOffset: CGSize = .zero
    var initialScale: CGFloat = 1.0
    var lastLocation: CGPoint = .zero
}

extension GraphViewModel {
    
    func symbolAt(point: CGPoint) -> Symbol? {
        // Iterate over all symbols to find the first one containing the point
        for symbol in symbols.reversed() { // Use reversed to prioritize symbols on top
            let rect = symbol.asRectangle
            
            switch symbol.type {
            case .rectangle:
                // Check if the point is inside the rectangle
                if rect.contains(point) {
                    return symbol
                }
                
            case .oval:
                // Check if the point is inside the oval (ellipse within the bounding rect)
                let center = CGPoint(x: rect.midX, y: rect.midY)
                let normalizedPoint = CGPoint(x: (point.x - center.x) / (rect.width / 2),
                                              y: (point.y - center.y) / (rect.height / 2))
                // If the normalized point is within the unit circle, it's inside the oval
                if normalizedPoint.x * normalizedPoint.x + normalizedPoint.y * normalizedPoint.y <= 1 {
                    return symbol
                }
            }
        }
        
        // Return nil if no symbol contains the point
        return nil
    }
}
