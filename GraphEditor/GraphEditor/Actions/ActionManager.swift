//
//  ActionManager.swift
//  GraphEditor
//
//  Created by Marko Cicak on 11/7/18.
//  Copyright © 2018 Marko Cicak. All rights reserved.
//

import Foundation

class ActionManager {
    func undoAction() -> ActionEditUndo {
        return ActionEditUndo()
    }
    
    func redoAction() -> ActionEditRedo {
        return ActionEditRedo()
    }
    
    func deleteSelectionAction() -> ActionDeleteSelection {
        return ActionDeleteSelection()
    }
    
    func selectAllAction() -> ActionEditSelectAll {
        return ActionEditSelectAll()
    }
    
    func cutAction() -> ActionEditCut {
        return ActionEditCut()
    }
    
    func pasteAction() -> ActionEditPaste {
        return ActionEditPaste()
    }
    
    func copyAction() -> ActionEditCopy {
        return ActionEditCopy()
    }
    
    func resetZoomAction() -> ActionResetZoom {
        return ActionResetZoom()
    }
    
    func deleteAllAction() -> ActionDeleteAll {
        return ActionDeleteAll()
    }
}
