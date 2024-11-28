//
//  SymbolFactory.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/28/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class SymbolFactory {
    static var count: String {
        return String(SREG.model.symbols.count)
    }
    
    static func createSymbol() -> Symbol {
        let size = CGSize(width: 200, height: 100)
        
        let centerPoint = CGPoint(x: SREG.context.lastPosition!.x - (size.width / 2), y: SREG.context.lastPosition!.y - (size.height / 2))
        
        let selectedSymbol: Symbol
        let selectedSymbolType = SREG.context.selectedSymbol
        switch selectedSymbolType {
        case .rectangle:
            selectedSymbol = Rectangle(with: centerPoint, size: size, color: .black, text: "Symbol # \(count)")
        case .ellipse:
            selectedSymbol = Ellipse(with: centerPoint, size: size, color: .black, text: "Symbol # \(count)")
        }
        return selectedSymbol
    }
}
