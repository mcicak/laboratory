//
//  MainViewController.swift
//  GraphEditor
//
//  Created by Marko Cicak on 10/30/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class MainViewController: UIViewController {
    @IBOutlet weak var deleteButton: UIBarButtonItem!
    @IBOutlet weak var undoButton: UIBarButtonItem!
    @IBOutlet weak var redoButton: UIBarButtonItem!
    @IBOutlet weak var editOptionsButton: UIBarButtonItem!
    @IBOutlet weak var deleteAllButton: UIBarButtonItem!
    @IBOutlet weak var symbolSelectionControl: UISegmentedControl!

    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        addObservers()
        refreshView()
        setup()
    }
}

// MARK: - Actions
extension MainViewController {
    @IBAction func undoAction(_ sender: UIBarButtonItem) {
        print("undoAction tapped")
        SREG.actionManager.undoAction().perform()
    }
    
    @IBAction func redoAction(_ sender: UIBarButtonItem) {
        print("redoAction tapped")
        SREG.actionManager.redoAction().perform()
    }
    
    @IBAction func deleteAction(_ sender: UIBarButtonItem) {
        SREG.actionManager.deleteSelectionAction().perform()
    }
    
    @IBAction func editOptionsAction(_ sender: UIBarButtonItem) {
        displayOptionsForSelectedItem()
    }
    
    @IBAction func resetZoomAction(_ sender: UIBarButtonItem) {
        SREG.actionManager.resetZoomAction().perform()
    }
    
    @IBAction func deleteAllAction(_ sender: UIBarButtonItem) {
        SREG.actionManager.deleteAllAction().perform()
    }
}

// MARK: - Observers and private func
extension MainViewController {
    private func addObservers() {
        let nc = NotificationCenter.default
        nc.addObserver(self, selector: #selector(refreshView), name: .notificationSymbolsAdded, object: nil)
        nc.addObserver(self, selector: #selector(refreshView), name: .notificationSelectionChanged, object: nil)
        nc.addObserver(self, selector: #selector(refreshView), name: .notificationSymbolsRemoved, object: nil)
        nc.addObserver(self, selector: #selector(refreshView), name: .notificationModelUpdated, object: nil)
    }
    
    @objc private func refreshView() {
        deleteButton.isEnabled = SREG.selection.selection.count != 0
        undoButton.isEnabled = !SREG.commandManager.currentCommandIsFirst()
        redoButton.isEnabled = !SREG.commandManager.currentCommandIsLast()
        deleteAllButton.isEnabled = SREG.model.symbols.count != 0
        editOptionsButton.isEnabled = deleteButton.isEnabled
    }
    
    private func displayOptionsForSelectedItem() {
        let storyboard = UIStoryboard(name: "Main", bundle: nil)
        let optionsVC = storyboard.instantiateViewController(withIdentifier: "EditViewController") as! EditViewController
        optionsVC.modalPresentationStyle = .popover
        optionsVC.popoverPresentationController?.barButtonItem = editOptionsButton
        optionsVC.tableView.rowHeight = UITableView.automaticDimension
        optionsVC.tableView.estimatedRowHeight = 44
        let contentHeight = optionsVC.tableView.estimatedRowHeight * CGFloat(optionsVC.editOptions.count)
        optionsVC.preferredContentSize = CGSize(width: 200, height: contentHeight)
        self.present(optionsVC, animated: true, completion: nil)
    }
    
    private func setup() {
        navigationItem.leftBarButtonItem = splitViewController?.displayModeButtonItem
        title = "Project Name"
        symbolSelectionControl.addTarget(self, action: #selector(handleSymbolSelection(_:)), for: .valueChanged)
    }
    
    @objc private func handleSymbolSelection(_ sender: UISegmentedControl) {
        if sender.selectedSegmentIndex == 0 {
            SREG.context.selectedSymbol = .rectangle
        } else {
            SREG.context.selectedSymbol = .ellipse
        }
    }
}
