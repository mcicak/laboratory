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

enum SendMessageError: Error {
    case failedToSend
}

protocol ApiService {
    
    func login(username: String, password: String) async throws -> LoginResult
    
    func sendMessage(text: String, chatId: String) async throws -> SendMessageResult
}

class ApiServiceMock: ApiService {
    
    func login(username: String, password: String) async throws -> LoginResult {
        try await withCheckedThrowingContinuation { continuation in
            Thread.sleep(forTimeInterval: 1)
            continuation.resume(returning: LoginResult(success: true))
        }
    }
    
    func sendMessage(text: String, chatId: String) async throws -> SendMessageResult {
        try await withCheckedThrowingContinuation { continuation in
            Thread.sleep(forTimeInterval: 1)
            let shouldFail = Int.random(in: 1...100) <= 25
            
            if shouldFail {
                continuation.resume(throwing: SendMessageError.failedToSend)
            } else {
                continuation.resume(returning: SendMessageResult(id: UUID().uuidString))
            }
        }
    }
}
