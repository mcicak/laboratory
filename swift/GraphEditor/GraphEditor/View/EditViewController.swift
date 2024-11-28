//
//  EditViewController.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/15/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import UIKit

class EditViewController: UITableViewController {
    var editOptions = [EditOption]()
    
    enum EditOption: String {
        case optionCut = "Cut"
        case optionCopy = "Copy"
        case optionPaste = "Paste"
        case optionUndo = "Undo"
        case optionRedo = "Redo"
        case optionSelectAll = "Select All"
        case optionPreferences = "Preferences"
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupEditOptions()
    }
    
    private func setupTableView() {
        tableView.alwaysBounceVertical = false
        view.layer.backgroundColor = UIColor.clear.cgColor
        view.backgroundColor = .clear
    }
    
    private func setupEditOptions() {
        editOptions.append(.optionUndo)
        editOptions.append(.optionRedo)
        editOptions.append(.optionCut)
        editOptions.append(.optionCopy)
        editOptions.append(.optionPaste)
        editOptions.append(.optionSelectAll)
        editOptions.append(.optionPreferences)

        setupTableView()
    }
}

// MARK: - Data source
extension EditViewController {
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return editOptions.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "EditOptionCell", for: indexPath)
        cell.textLabel?.text = editOptions[indexPath.row].rawValue
        cell.layer.backgroundColor = UIColor.clear.cgColor
        cell.backgroundColor = .clear
        return cell
    }
}

// MARK: - Delegate
extension EditViewController {
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let selectedOption = editOptions[indexPath.row]
        switch selectedOption {
        case .optionUndo:
            SREG.actionManager.undoAction().perform()
            break
        case .optionRedo:
            SREG.actionManager.redoAction().perform()
            break
        case .optionCut:
            SREG.actionManager.cutAction().perform()
            break
        case .optionCopy:
            SREG.actionManager.copyAction().perform()
            break
        case .optionPaste:
            SREG.actionManager.pasteAction().perform()
            break
        case .optionSelectAll:
            SREG.actionManager.selectAllAction().perform()
            break
        case .optionPreferences:
            print("optionPreferences tapped")
            break
        }
        
        dismiss(animated: true, completion: nil)
    }
}
