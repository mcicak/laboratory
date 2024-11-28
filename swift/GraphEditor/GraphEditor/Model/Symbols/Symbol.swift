//
//  Symbol.swift
//  GraphEditor
//
//  Created by Marko Cicak on 10/30/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class Symbol: NSObject {
    var position: CGPoint
    var size: CGSize
    var color: UIColor
    var text: String
    
    var isSelected: Bool = false
    
    init(with position: CGPoint, size: CGSize, color: UIColor, text: String) {
        self.position = position
        self.size = size
        self.color = color
        self.text = text
    }
    
    func getAsRectangle() -> CGRect {
        fatalError("Abstract method called")
    }
    
    override func copy() -> Any {
        let symbol = Symbol(with: position, size: size, color: color, text: text)
        return symbol
    }
    
    func setSelectedSymbol(_ symbolType: SymbolType) {
        switch symbolType {
        case .rectangle:
            SREG.context.selectedSymbol = .rectangle
        case .ellipse:
            SREG.context.selectedSymbol = .ellipse
        }
    }
}

extension Symbol {
    enum SymbolType {
        case rectangle
        case ellipse
    }
}


