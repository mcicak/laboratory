//
//  DataProvider.swift
//  ChatApp
//
//  Created by Marko Cicak on 5.9.24..
//

import Foundation
import SwiftUI

struct ApiServiceKey: EnvironmentKey {
    static let defaultValue: ApiService = ApiServiceMock()
}

extension EnvironmentValues {
    var apiService: ApiService {
        get { self[ApiServiceKey.self] }
        set { self[ApiServiceKey.self] = newValue }
    }
}

protocol ApiService {
    
    func login(username: String, password: String) async throws -> LoginResult
}

class ApiServiceMock: ApiService {
    
    func login(username: String, password: String) async throws -> LoginResult {
        try await withCheckedThrowingContinuation { continuation in
            Thread.sleep(forTimeInterval: 1)
            continuation.resume(returning: LoginResult(success: true))
        }
    }
}
