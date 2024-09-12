//
//  Sequence.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 12.9.24..
//

import Foundation

extension Array where Element: Equatable {
    
    mutating func removeObjects(in otherArray: any Sequence<Element>) {
        self.removeAll { otherArray.contains($0) }
    }
}
