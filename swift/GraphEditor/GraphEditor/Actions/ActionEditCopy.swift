//
//  ActionEditCopy.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/15/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionEditCopy: ActionAbstract {
    override func perform() {
        SREG.clipboard.setContent(elements: SREG.selection.elements)
    }
}
