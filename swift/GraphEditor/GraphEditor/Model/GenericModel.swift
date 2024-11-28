//
//  Modelable.swift
//  GraphEditor
//
//  Created by Marko Cicak on 10/30/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

protocol GenericModel: class {
    var symbols: NSMutableArray { get }
    func addSymbol(symbol: Symbol)
    func addSymbols(symbols: NSMutableArray)
    func removeSymbol(symbol: Symbol)
    func removeSymbols(symbols: NSMutableArray)
    func removeAllSymbols()
    func removeSelected()
    func fireModelUpdate()
}
