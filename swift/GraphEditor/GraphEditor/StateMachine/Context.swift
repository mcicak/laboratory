//
//  Context.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/1/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class Context {
    var lastPosition: CGPoint?
    var symbolHit: Symbol?
    var graphView: GraphView?
    var scale: CGFloat
    var transform: CGAffineTransform
    var symbolIndex: Int
    var selectedSymbol: Symbol.SymbolType = .rectangle
    
    // laso
    var lasoOn: Bool = false
    var laso: CGRect?
    
    init() {
        scale = 1.0
        transform = CGAffineTransform.identity
        symbolIndex = 1
        lasoOn = false
    }
}
