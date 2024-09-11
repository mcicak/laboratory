//
//  Rectangle.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/22/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class Rectangle: Symbol {
    override init(with position: CGPoint, size: CGSize, color: UIColor, text: String) {
        super.init(with: position, size: size, color: color, text: text)
    }
    
    override func getAsRectangle() -> CGRect {
        return CGRect(origin: position, size: size)
    }
    
    override func copy() -> Any {
        let rect = Rectangle(with: position, size: size, color: color, text: text)
        return rect
    }
}
