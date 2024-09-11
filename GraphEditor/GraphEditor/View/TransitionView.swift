//
//  TransitionView.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/13/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class TransitionView: UIView {
    var currentState = SREG.context.graphView?.currentState
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    override func draw(_ rect: CGRect) {
        guard let currentContext = UIGraphicsGetCurrentContext() else { return }
        currentContext.saveGState()
        
        if (SREG.context.lasoOn) {
            let lenghts: [CGFloat] = [4, 2]
            currentContext.setLineDash(phase: 0.0, lengths: lenghts)
            currentContext.setLineWidth(2)
            currentContext.setStrokeColor(red: 0, green: 0, blue: 0, alpha: 1)
            currentContext.stroke(SREG.context.laso!)
        }
        currentContext.restoreGState()
    }
}
