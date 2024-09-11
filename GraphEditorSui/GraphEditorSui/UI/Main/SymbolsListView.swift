//
//  MasterView.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 10.9.24..
//

import Foundation
import SwiftUI

struct SymbolsListView: View {
    
    @Binding var symbols: [Symbol]
    @Binding var selectionModel: SelectionModel
    
    var body: some View {
        List {
            ForEach(symbols) { symbol in
                HStack {
                    Text(symbol.type == .rectangle ? "Rectangle" : "Oval")
                        .bold(symbol.isSelected)
                    Spacer()
                    if symbol.isSelected {
                        Image(systemName: "checkmark")
                            .foregroundColor(.blue)
                    }
                }
                .contentShape(Rectangle()) // Makes the entire row tappable
                .onTapGesture {
                    if symbol.isSelected {
                        selectionModel.removeFromSelection(symbol: symbol)
                    } else {
                        selectionModel.addToSelection(symbol: symbol)
                    }
                }
            }
        }
    }
}
