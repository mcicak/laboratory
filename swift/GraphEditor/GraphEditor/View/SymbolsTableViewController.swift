//
//  SymbolsTableViewController.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/22/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class SymbolsTableViewController: UITableViewController, UISplitViewControllerDelegate {
    var symbols: [Symbol] {
        return (SREG.model.symbols as? [Symbol])!
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setup()
        addObservers()
    }
}

// MARK: - Private func
extension SymbolsTableViewController {
    private func addObservers() {
        let nc = NotificationCenter.default
        nc.addObserver(self, selector: #selector(handleSymbolsChanged), name: Notification.Name.notificationSymbolsAdded , object: nil)
        nc.addObserver(self, selector: #selector(handleSymbolsChanged), name: Notification.Name.notificationSymbolsRemoved , object: nil)
        nc.addObserver(self, selector: #selector(handleSelectionChanged), name: Notification.Name.notificationSelectionChanged , object: nil)
    }
    
    @objc private func handleSymbolsChanged() {
        reloadData()
    }
    
    @objc private func handleSelectionChanged() {
        reloadData()
    }
    
    private func reloadData() {
        tableView.reloadData()
    }
    
    private func setup() {
        splitViewController?.delegate = self
        title = "Symbols"
    }
    
    private func handleSelection(_ cell: UITableViewCell, _ symbol: Symbol) {
        if symbol.isSelected {
            SREG.selection.removeFromSelection(symbol: symbol)
            cell.accessoryType = .none
        } else {
            SREG.selection.addToSelection(symbol: symbol)
            cell.accessoryType = .checkmark
        }
    }
}

// MARK: - Data source
extension SymbolsTableViewController {
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return SREG.model.symbols.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "SymbolCell", for: indexPath)
        let symbol = symbols[indexPath.row]
        cell.textLabel?.text = symbol.text
        if symbol.isSelected {
            cell.accessoryType = .checkmark
        } else {
            cell.accessoryType = .none
        }
        cell.layer.backgroundColor = UIColor.clear.cgColor
        cell.backgroundColor = .clear
        return cell
    }
}

// MARK: - Delegate
extension SymbolsTableViewController {
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let cell = tableView.cellForRow(at: indexPath) {
            let symbol = symbols[indexPath.row]
            handleSelection(cell, symbol)
        }
    }
    
    override func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        print("didDeselectRowAt indexPath:")
    }
}
