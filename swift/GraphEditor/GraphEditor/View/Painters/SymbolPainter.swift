//
//  SymbolPainter.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/2/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class SymbolPainter: NSObject {
    let symbol: Symbol
    
    init(_ symbol: Symbol) {
        self.symbol = symbol
    }
    
    func draw(context: CGContext, isMoving: Bool, isZooming: Bool) {
        // abstract
    }
}
