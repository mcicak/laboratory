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

struct ContentView: View {
    
    @State var stateMachine = GraphStateMachine()
    
    var body: some View {
        NavigationSplitView {
            SymbolsListView(symbols: $stateMachine.viewModel.symbols, selectionModel: $stateMachine.selectionModel)
        } detail: {
            ZStack {
                GraphView(symbols: stateMachine.viewModel.symbols,
                          selection: stateMachine.selectionModel.elements,
                          transform: stateMachine.viewModel.transform,
                          stateMachine: stateMachine)
                if stateMachine.viewModel.isLasoOn {
                    LasoSelectionView(transform: stateMachine.viewModel.transform,
                                      rect: stateMachine.viewModel.lasoRect)
                    
                }
            }
            .navigationBarItems(
                leading: HStack {
                    Button(action: {
                        print("UNDO")
                    }, label: {
                        Image(systemName: "arrow.uturn.backward")
                    })
                    .disabled(stateMachine.commandManager.currentCommandIsFirst())
                    
                    Button(action: {
                        print("REDO")
                    }, label: {
                        Image(systemName: "arrow.uturn.forward")
                    })
                    .disabled(stateMachine.commandManager.currentCommandIsLast())
                    
                    Spacer(minLength: 40)
                    
                    Button(action: {
                        print("CUT")
                    }, label: {
                        Image(systemName: "scissors")
                    })
                    //.disabled(stateMachine.commandManager.currentCommandIsLast())
                    
                    Button(action: {
                        print("COPY")
                    }, label: {
                        Image(systemName: "square.2.layers.3d")
                    })
                    //.disabled(stateMachine.commandManager.currentCommandIsLast())
                    
                    Button(action: {
                        print("PASTE")
                    }, label: {
                        Image(systemName: "list.clipboard")
                    })
                    //.disabled(stateMachine.commandManager.currentCommandIsLast())
                    
                    Spacer(minLength: 40)
                    
                    Button(action: {
                        print("DELETE")
                    }, label: {
                        Image(systemName: "trash")
                    })
                    
                },
                trailing: HStack {
                    Button(action: {
                        print("RESET-ZOOM")
                    }, label: {
                        Image(systemName: "1.magnifyingglass")
                    })
                    
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
                Symbol(position: CGPoint(x: 100, y: 100), size: CGSize(width: 100, height: 100), type: .oval),
                Symbol(position: CGPoint(x: 150, y: 250), size: CGSize(width: 200, height: 100), type: .rectangle),
                Symbol(position: CGPoint(x: 200, y: 400), size: CGSize(width: 200, height: 100), type: .rectangle)
            ]
            
            stateMachine.selectionModel.addToSelection(symbol: stateMachine.viewModel.symbols[1])
        }
    }
}

#Preview {
    ContentView()
}
