//
//  Clipboard.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/15/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class Clipboard {
    var elements = NSMutableArray()
    var content: NSMutableArray {
        let duplicateArray = NSMutableArray()
        for element in self.elements {
            duplicateArray.add(element)
        }
        print("Clipboard Content set with: ", duplicateArray.count)
        return duplicateArray
    }
    
    func setContent(elements: NSMutableArray) {
        for element in elements {
            self.elements.add(element)
        }
    }
    
    func clear() {
        self.elements.removeAllObjects()
    }
}
