//
//  GraphView.swift
//  GraphEditor
//
//  Created by Marko Cicak on 10/31/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class GraphView: UIView {
    var model: Model {
       return SREG.model
    }
    var currentState: State = SelectionState() {
        didSet {
            if currentState is LasoSelectionState {
                showTransitionView()
            } else {
                hideTransitionView()
            }
        }
    }
    var elementPainters = [SymbolPainter]()
    let selectionHandler = SelectionHandleHandler()
    var gesture: TapBeganGesture?
    var transitionView: UIView = TransitionView()
    
    var isMoving: Bool {
        if currentState is MoveState {
            return true
        }
        return false
    }
    
    var isZooming: Bool {
        if currentState is ZoomState {
            return true
        }
        return false
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupInstance()
        setCurrentState(currentState: SelectionState())
    }
    
    override func draw(_ rect: CGRect) {
        super.draw(rect)
        guard let currentContext = UIGraphicsGetCurrentContext() else { return }
        currentContext.saveGState()
        
        for painter in elementPainters {
            painter.draw(context: currentContext, isMoving: isMoving, isZooming: isZooming)
        }
        
        // draws selection handles
        selectionHandler.paintSelectionHandles(context: currentContext,
                                               selection: SREG.selection,
                                               scale: SREG.context.scale)
        
        currentContext.restoreGState()
    }
}

// MARK: - UIGestureRecognizer methods
extension GraphView {
    private func addTapGestureRecognizer() {
        let tap = UITapGestureRecognizer(target: self, action: #selector(handleTap(_:)))
        addGestureRecognizer(tap)
    }
    
    private func addPinchGestureRecognizer() {
        let pinch = UIPinchGestureRecognizer(target: self, action: #selector(handlePinch(_:)))
        addGestureRecognizer(pinch)
    }
    
    private func addPanGestureRecognizer() {
        let pan = UIPanGestureRecognizer(target: self, action: #selector(handlePan(_:)))
        addGestureRecognizer(pan)
    }
    
    @objc func handleTap(_ gestureRecognizer: UITapGestureRecognizer) {
        switch gestureRecognizer.state {
        case UIGestureRecognizer.State.began:
            currentState.tapBegan(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.changed:
            currentState.tapChanged(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.ended, .cancelled, .failed:
            currentState.tapEnded(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.possible:
            break
        }
    }
    
    @objc func handlePinch(_ gestureRecognizer: UIPinchGestureRecognizer) {
        switch gestureRecognizer.state {
        case UIGestureRecognizer.State.began:
            currentState.pinchBegan(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.changed:
            currentState.pinchChanged(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.ended, .cancelled, .failed:
            currentState.pinchEnded(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.possible:
            break
        }
    }
    
    @objc func handlePan(_ gestureRecognizer: UIPanGestureRecognizer) {
        switch gestureRecognizer.state {
        case UIGestureRecognizer.State.began:
            currentState.panBegan(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.changed:
            currentState.panChanged(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.ended, .cancelled, .failed:
            currentState.panEnded(recognizer: gestureRecognizer)
            break
        case UIGestureRecognizer.State.possible:
            break
        }
    }
    
    //since tapBegin is not called from gestureRecognizer, we have to send/simulate location from touchesBegan method
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        gesture = TapBeganGesture()
        gesture?.location = touches.first?.location(in: self)
        currentState.tapBegan(recognizer: gesture!)
    }
}

// MARK: - Private func
extension GraphView {
    private func setupView() {
        SREG.context.graphView = self
//        let patternImage = UIImage(named: "square")?.alpha(0.2)
//        backgroundColor = UIColor(patternImage: patternImage!)
    }
    
    private func setupInstance() {
        installTransitionView()
        addTapGestureRecognizer()
        addPinchGestureRecognizer()
        addPanGestureRecognizer()
        addObservers()
        setupView()
    }
    
    private func installTransitionView() {
        transitionView.isHidden = true
        transitionView.frame = bounds
        transitionView.backgroundColor = UIColor.clear
        addSubview(transitionView)
        
        transitionView.translatesAutoresizingMaskIntoConstraints = false
        transitionView.leftAnchor.constraint(equalTo: leftAnchor).isActive = true
        transitionView.rightAnchor.constraint(equalTo: rightAnchor).isActive = true
        transitionView.topAnchor.constraint(equalTo: topAnchor).isActive = true
        transitionView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
    }
    
    private func addObservers() {
        let nc = NotificationCenter.default
        nc.addObserver(self, selector: #selector(handleDidAddSymbols(_:)), name: .notificationSymbolsAdded, object: nil)
        nc.addObserver(self, selector: #selector(handleDidSelectSymbols(_:)), name: .notificationSelectionChanged, object: nil)
        nc.addObserver(self, selector: #selector(handleDidRemoveSymbols(_:)), name: .notificationSymbolsRemoved, object: nil)
        nc.addObserver(self, selector: #selector(handleDidUpdateModel(_:)), name: .notificationModelUpdated, object: nil)
    }
    
    private func redraw(_ superView: Bool = true) {
        if superView {
            setNeedsDisplay()
        } else {
            transitionView.setNeedsDisplay()
        }
    }
    
    private func drawLast(selectedSymbols: [Symbol]) {
        var selectedPainters = [SymbolPainter]()
        for symbol in selectedSymbols {
            for painter in elementPainters {
                if symbol.hashValue == painter.symbol.hashValue {
                    selectedPainters.append(painter)
                }
            }
        }
        if !selectedPainters.isEmpty {
            selectedPainters.forEach { (painter) in
                if !elementPainters.contains(painter) {
                    elementPainters.append(painter)
                }
            }
        }
        
        elementPainters.removeAll { (painter) -> Bool in
            return selectedPainters.contains(painter)
        }
        elementPainters.append(contentsOf: selectedPainters)
    }
    
    func setCurrentState(currentState: State) {
        self.currentState.stateWillFinish()
        self.currentState.stateDidFinish()
        self.currentState = currentState
        self.currentState.stateWillStart()
        self.currentState.stateDidStart()
        if currentState is LasoSelectionState {
            redraw(false)
        } else {
            redraw()
        }
    }
    
    private func showTransitionView() {
        transitionView.isHidden = false
        transitionView.setNeedsDisplay()
    }
    
    private func hideTransitionView() {
        transitionView.isHidden = true
    }
}

// MARK: - Used to override tapGesture locationInView method
class TapBeganGesture: UITapGestureRecognizer {
    var location: CGPoint?
    
    override func location(in view: UIView?) -> CGPoint {
        return self.location!
    }
}

// MARK: - Notification handler methods
extension GraphView {
    private func createPaintersFrom(_ symbols: [Symbol]) {
        var painter: SymbolPainter
        for symbol in symbols {
            switch symbol {
            case is Ellipse:
                painter = PainterEllipse(symbol)
                break
            case is Rectangle:
                painter = PainterRectangle(symbol)
                break
            default:
                fatalError("No painter defined")
            }
            
            if !elementPainters.contains(painter) {
                elementPainters.append(painter)
            }
        }
        redraw()
    }
    
    @objc func handleDidAddSymbols(_ notification: Notification) {
        guard let userInfo = notification.userInfo as? [String: Any] else { return }
        guard let symbols = userInfo["symbols"] as? [Symbol] else { return }
        createPaintersFrom(symbols)
    }
    
    @objc func handleDidSelectSymbols(_ notification: Notification) {
        if let userInfo = notification.userInfo as? [String: Any] {
            if let symbols = userInfo["symbols"] as? [Symbol] {
                drawLast(selectedSymbols: symbols)
                redraw()
            }
        }
    }
    
    @objc func handleDidRemoveSymbols(_ notification: Notification) {
        var painter: SymbolPainter
        elementPainters.removeAll()
        for symbol in (SREG.model.symbols as NSArray as! [Symbol]) {
            switch symbol {
            case is Ellipse:
                painter = PainterEllipse(symbol)
                break
            case is Rectangle:
                painter = PainterRectangle(symbol)
                break
            default:
                fatalError("No painter defined")
            }
            elementPainters.append(painter)
        }
        redraw()
    }
    
    @objc func handleDidUpdateModel(_ notification: Notification) {
        redraw()
    }
}
