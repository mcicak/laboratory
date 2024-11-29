import 'dart:convert';
import 'package:http/http.dart' as http;

import 'dto.dart';

class NetworkConfiguration {
  final String baseUrl;

  NetworkConfiguration._(this.baseUrl);

  // Factory for Local Environment
  factory NetworkConfiguration.local() {
    return NetworkConfiguration._('https://9dfc-79-101-17-181.ngrok-free.app');
  }

  // Factory for ManTest Environment
  factory NetworkConfiguration.mantest() {
    return NetworkConfiguration._('https://mantest.example.com');
  }

  // Factory for Production Environment
  factory NetworkConfiguration.production() {
    return NetworkConfiguration._('https://api.example.com');
  }
}

class ServiceApi {
  final NetworkConfiguration config;
  String? _authHeader;

  ServiceApi(this.config);

  // Set Basic Authentication header
  void setAuthToken(String token) {
    _authHeader = 'Basic $token';
  }

  // Clear Authentication (e.g., logout)
  void clearAuthCredentials() {
    _authHeader = null;
  }

  Future<LoginResponse> login(String username, String password) async {
    final response = await _post(
      '/auth/login',
      body: {'username': username, 'password': password},
      useAuthHeader: false,
    );
    return LoginResponse.fromJson(jsonDecode(response));
  }

  Future<void> register(String firstname, String lastname, String username, String password) async {
    await _post(
      '/auth/register',
      body: {
        'firstname': firstname,
        'lastname': lastname,
        'username': username,
        'password': password,
      },
      useAuthHeader: false,
    );
  }

  Future<List<NoteDto>> getNotes() async {
    final response = await _get('/api/notes');
    return (jsonDecode(response) as List).map((e) => NoteDto.fromJson(e)).toList();
  }

  Future<CreateNoteResponse> createNote(CreateNoteRequest note) async {
    final response = await _post('/api/notes', body: note.toJson());
    return CreateNoteResponse.fromJson(jsonDecode(response));
  }

  Future<void> editNote(String noteId, EditNoteRequest note) async {
    await _put('/api/note/$noteId', body: note.toJson());
  }

  Future<NoteDto> getNote(String noteId) async {
    final response = await _get('/api/note/$noteId');
    return NoteDto.fromJson(jsonDecode(response));
  }

  Future<void> deleteNote(String noteId) async {
    await _delete('/api/note/$noteId');
  }

  // Private HTTP helper methods
  Future<String> _get(String endpoint, {bool useAuthHeader = true}) async {
    final response = await http.get(
      Uri.parse('${config.baseUrl}$endpoint'),
      headers: _buildHeaders(useAuthHeader),
    );
    _handleResponse(response);
    return response.body;
  }

  Future<String> _post(String endpoint, {Map<String, dynamic>? body, bool useAuthHeader = true}) async {
    final response = await http.post(
      Uri.parse('${config.baseUrl}$endpoint'),
      headers: _buildHeaders(useAuthHeader),
      body: body != null ? jsonEncode(body) : null,
    );
    _handleResponse(response);
    return response.body;
  }

  Future<String> _put(String endpoint, {Map<String, dynamic>? body, bool useAuthHeader = true}) async {
    final response = await http.put(
      Uri.parse('${config.baseUrl}$endpoint'),
      headers: _buildHeaders(useAuthHeader),
      body: body != null ? jsonEncode(body) : null,
    );
    _handleResponse(response);
    return response.body;
  }

  Future<void> _delete(String endpoint, {bool useAuthHeader = true}) async {
    final response = await http.delete(
      Uri.parse('${config.baseUrl}$endpoint'),
      headers: _buildHeaders(useAuthHeader),
    );
    _handleResponse(response);
  }

  // Build headers for requests
  Map<String, String> _buildHeaders(bool useAuthHeader) {
    final headers = <String, String>{
      'Content-Type': 'application/json',
    };
    if (useAuthHeader && _authHeader != null) {
      headers['Authorization'] = _authHeader!;
    }
    return headers;
  }

  // Handle HTTP response
  void _handleResponse(http.Response response) {
    if (response.statusCode >= 400) {
      throw HttpException(
        'Request failed with status: ${response.statusCode}. Body: ${response.body}',
      );
    }
  }
}

class HttpException implements Exception {
  final String message;

  HttpException(this.message);

  @override
  String toString() => 'HttpException: $message';
}
