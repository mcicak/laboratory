//
//  ActionResetZoom.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/20/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionResetZoom: ActionAbstract {
    override func perform() {
        SREG.context.scale = 1.0
        SREG.context.transform = .identity
        SREG.model.fireModelUpdate()
    }
}
