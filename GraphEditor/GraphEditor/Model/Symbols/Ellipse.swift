//
//  Ellipse.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/23/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class Ellipse: Symbol {
    override init(with position: CGPoint, size: CGSize, color: UIColor, text: String) {
        super.init(with: position, size: size, color: color, text: text)
    }
    
    override func getAsRectangle() -> CGRect {
        return CGRect(origin: position, size: size)
    }
    
    override func copy() -> Any {
        let ellipse = Ellipse(with: position, size: size, color: color, text: text)
        return ellipse
    }
}
