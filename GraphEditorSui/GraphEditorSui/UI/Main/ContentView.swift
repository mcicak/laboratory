//
//  ContentView.swift
//  GraphEditorSui
//
//  Created by Marko Cicak on 30.8.24..
//

import SwiftUI

@Observable
class Symbol: Identifiable, Equatable, Hashable {
    let id = UUID()
    var position: CGPoint
    var size: CGSize
    var isSelected: Bool = false
    var type: SymbolType
    
    init(position: CGPoint, size: CGSize, type: SymbolType) {
        self.position = position
        self.size = size
        self.type = type
    }
    
    // MARK: - Equatable
    static func == (lhs: Symbol, rhs: Symbol) -> Bool {
        return lhs.id == rhs.id
    }
    
    // MARK: - Hashable
    func hash(into hasher: inout Hasher) {
        hasher.combine(id)
    }
    
    var asRectangle: CGRect { return CGRect(origin: position, size: size) }
}

struct DummyView: View {
    
    var body: some View {
        Canvas { context, size in
            print("DUMMY REDRAW!")
            context.fill(Path(CGRect(x: 10, y: 10, width: 50, height: 50)), with: .color(.cyan))
        }
    }
}

struct ContentView: View {
    
    @State var stateMachine = GraphStateMachine(viewModel: GraphViewModel(), selectionModel: SelectionModel())
    
    var body: some View {
        NavigationSplitView {
            SymbolsListView(symbols: $stateMachine.viewModel.symbols, selectionModel: $stateMachine.selectionModel)
        } detail: {
            ZStack {
                DummyView()
                GraphView(symbols: stateMachine.viewModel.symbols,
                          selection: stateMachine.selectionModel.elements,
                          transform: stateMachine.viewModel.transform,
                          stateMachine: stateMachine)
//                GraphView(viewModel: $stateMachine.viewModel, stateMachine: stateMachine)
//                    .drawingGroup()
                if stateMachine.viewModel.isLasoOn {
                    LasoSelectionView(transform: stateMachine.viewModel.transform,
                                      rect: stateMachine.viewModel.lasoRect)
                    
                }
            }
            .navigationBarItems(trailing: HStack{
                Button(action: {
                    stateMachine.currentState = SelectionState()
                }) {
                    Image(systemName: "hand.point.up.left")
                        .padding(8)
                        .background(!(stateMachine.currentState is DragState) ? Color.blue.opacity(0.3) : Color.clear)
                        .cornerRadius(8)
                }
                
                Button(action: {
                    stateMachine.currentState = DragState()
                }) {
                    Image(systemName: "arrow.up.and.down.and.arrow.left.and.right")
                        .padding(8)
                        .background(stateMachine.currentState is DragState ? Color.blue.opacity(0.3) : Color.clear)
                        .cornerRadius(8)
                }
            })
        }
        .onAppear {
            // Add some initial symbols
            stateMachine.viewModel.symbols = [
                Symbol(position: CGPoint(x: 100, y: 100), size: CGSize(width: 50, height: 50), type: .oval),
                Symbol(position: CGPoint(x: 200, y: 200), size: CGSize(width: 75, height: 75), type: .rectangle),
                Symbol(position: CGPoint(x: 300, y: 300), size: CGSize(width: 75, height: 75), type: .rectangle)
            ]
            
            stateMachine.selectionModel.addToSelection(symbol: stateMachine.viewModel.symbols.first!)
        }
    }
}

#Preview {
    ContentView()
}
