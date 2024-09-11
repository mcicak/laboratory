//
//  Extensions.swift
//  GraphEditor
//
//  Created by Marko Cicak on 10/31/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

extension Notification.Name {
    static let notificationSymbolsAdded = NSNotification.Name("notificationSymbolAdded")
    static let notificationSelectionChanged = NSNotification.Name("notificationSelectionChanged")
    static let notificationSymbolsRemoved = NSNotification.Name("notificationSymbolRemoved")
    static let notificationModelUpdated = NSNotification.Name("notificationModelUpdated")
}

extension UIImage {
    func alpha(_ value: CGFloat) -> UIImage {
        UIGraphicsBeginImageContextWithOptions(size, false, scale)
        draw(at: CGPoint.zero, blendMode: .normal, alpha: value)
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return newImage!
    }
}

