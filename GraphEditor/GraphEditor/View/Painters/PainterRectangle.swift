//
//  PainterRectangle.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/2/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class PainterRectangle: SymbolPainter {
    
    // MARK: - Override method
    override func draw(context: CGContext, isMoving: Bool, isZooming: Bool) {
        context.saveGState()
        context.setFillColor(red: 0.0, green: 0.0, blue: 0.0, alpha: 1.0)
        let ctx = SREG.context
        let point = self.symbol.position
        let transformedPosition = point.applying(ctx.transform)
        let size = self.symbol.size
        let transformedSize = size.applying(ctx.transform)
        
        let rect = CGRect(x: transformedPosition.x, y: transformedPosition.y, width: transformedSize.width, height: transformedSize.height)
        context.stroke(rect, width: 3.4)
        
        if !isMoving {
            context.setFillColor(red: 0.00, green: 0.45, blue: 0.85, alpha: 1.0)
            let contextRect = CGRect(x: transformedPosition.x + 2, y: transformedPosition.y + 2, width: transformedSize.width - 4, height: transformedSize.height - 4)
            context.fill(contextRect)
            
            drawCenteredTextWithContext(context, inRectangle: contextRect, isZooming: isZooming)
        }
        context.restoreGState()
    }
}

// MARK: - Private func
extension PainterRectangle {
    private func drawCenteredTextWithContext(_ context: CGContext, inRectangle contextRect: CGRect, isZooming: Bool) {
        var textSize: CGFloat
        let yOffset: CGFloat
        
        if isZooming {
            textSize = contextRect.size.width * 0.1
            yOffset = contextRect.origin.y
        } else {
            textSize = 13
            yOffset = contextRect.origin.y + textSize
        }
        
        let textRect = CGRect(x: contextRect.origin.x, y: yOffset, width: contextRect.size.width, height: contextRect.size.height)
        let transform = SREG.context.transform
        let textTransform = CGAffineTransform(a: transform.a, b: transform.b, c: transform.c, d: -transform.d, tx: transform.tx, ty: transform.ty)
        context.textMatrix = textTransform
        
        let text = symbol.text
        let attributedString = getAttributedString(text, with: textSize)
        attributedString.draw(in: textRect)
    }
    
    private func getAttributedString(_ text: String, with size: CGFloat) -> NSAttributedString {
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = .center
        
        let attributes = [
            NSAttributedString.Key.paragraphStyle: paragraphStyle,
            NSAttributedString.Key.font: UIFont.systemFont(ofSize: size),
            NSAttributedString.Key.foregroundColor: UIColor.white
        ]
        
        let attributedString = NSAttributedString(string: text, attributes: attributes)
        return attributedString
    }
}
