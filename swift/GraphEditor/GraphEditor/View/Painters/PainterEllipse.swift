//
//  PainterEllipse.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/23/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class PainterEllipse: SymbolPainter {
    override func draw(context: CGContext, isMoving: Bool, isZooming: Bool) {
        context.saveGState()
        context.setFillColor(red: 1.00, green: 0.25, blue: 0.21, alpha: 1.0)
        context.setStrokeColor(UIColor.black.cgColor)
        context.setLineWidth(3.4)
        let ctx = SREG.context
        let point = self.symbol.position
        let transformedPosition = point.applying(ctx.transform)
        let size = self.symbol.size
        let transformedSize = size.applying(ctx.transform)
        
        let rect = CGRect(x: transformedPosition.x, y: transformedPosition.y, width: transformedSize.width, height: transformedSize.height)
        context.addEllipse(in: rect)

        if !isMoving {
            context.drawPath(using: .fillStroke)
            drawCenteredTextWithContext(context, inRectangle: rect, isZooming: isZooming)
        } else {
            context.drawPath(using: .stroke)
        }
        context.restoreGState()
    }
}

extension PainterEllipse {
    private func drawCenteredTextWithContext(_ context: CGContext, inRectangle contextRect: CGRect, isZooming: Bool) {
        var textSize: CGFloat
        let yOffset: CGFloat
        
        if isZooming {
            textSize = contextRect.size.width * 0.1
            yOffset = contextRect.origin.y + 10
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


