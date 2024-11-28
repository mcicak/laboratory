//
//  ContentView.swift
//  FlipViewAnimation
//
//  Created by Marko Cicak on 6.9.24..
//

import SwiftUI

struct ContentView: View {
    
    @State var backDegree = 0.0
    @State var frontDegree = -90.0
    @State var isFlipped = false
    let durationAndDelay : CGFloat = 0.2
    @State var layoutResetId = UUID()
    
    func flipViews () {
        isFlipped = !isFlipped
        if isFlipped {
            withAnimation(.linear(duration: durationAndDelay)) {
                backDegree = 90
            }completion: {
                withAnimation(.linear(duration: durationAndDelay)){
                    frontDegree = 0
                }completion: {
                    layoutResetId = UUID()
                }
            }
            
        } else {
            withAnimation(.linear(duration: durationAndDelay)) {
                frontDegree = -90
            }completion: {
                withAnimation(.linear(duration: durationAndDelay)) {
                    backDegree = 0
                }
            }
            
        }
    }
    
    var body: some View {
        ZStack {
            LoginView(onLogin: { flipViews() }, degree: $backDegree)
            MainView(onLogout: { flipViews() }, degree: $frontDegree, layoutResetId: $layoutResetId)
        }
        .background(Color.black)
    }
}

struct LoginView: View {
    
    var onLogin: () -> Void = {}
    
    @Binding var degree : Double
    
    var body: some View {
        ZStack{
            VStack {
                Spacer()
                Text("Login")
                    .padding(.bottom, 40)
                Button("Login") {
                    onLogin()
                }
                Spacer()
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color.white)
        }
        .rotation3DEffect(Angle(degrees: degree), axis: (x: 0, y: 1, z: 0))
        .ignoresSafeArea(edges: degree != 0 ? .all : [])
        .allowsHitTesting(degree == 0)
    }
}

struct MainView: View {
    
    var onLogout: () -> Void = {}
    @Binding var degree : Double
    @Binding var layoutResetId : UUID
    
    @State var cv: NavigationSplitViewVisibility = .doubleColumn
    
    var body: some View {
        ZStack{
            NavigationSplitView(columnVisibility: $cv) {
                VStack {
                    Text("Main View")
                        .font(.largeTitle)
                        .padding()
                    Text("Some content goes here...")
                    
                    Spacer()
                    
                    Button(action: {
                        onLogout()
                    }) {
                        Text("Logout")
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Color.red)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
                    .padding()
                }
            } detail: {
                ZStack {
                    Text("Details")
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
            .navigationSplitViewStyle(.balanced)
            .id(layoutResetId)
        }
        .rotation3DEffect(Angle(degrees: degree), axis: (x: 0, y: 1, z: 0))
        .ignoresSafeArea(edges: degree != 0 ? .all : [])
        .allowsHitTesting(degree == 0)
    }
}
