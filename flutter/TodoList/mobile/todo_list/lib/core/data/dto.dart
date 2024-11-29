class LoginResponse {
  final String token;

  LoginResponse({required this.token});

  factory LoginResponse.fromJson(jsonDecode) {
    return LoginResponse(token: jsonDecode['token']);
  }
}

class NoteDto {
  final String id;
  final String title;
  final String content;

  NoteDto({required this.id, required this.title, required this.content});

  factory NoteDto.fromJson(jsonDecode) {
    return NoteDto(
      id: jsonDecode['_id'],
      title: jsonDecode['title'],
      content: jsonDecode['content'],
    );
  }
}

class CreateNoteRequest {
  final String title;
  final String content;

  CreateNoteRequest({required this.title, required this.content});

  Map<String, dynamic> toJson() => {'title': title, 'content': content};
}

class EditNoteRequest {
  final String title;
  final String content;

  EditNoteRequest({required this.title, required this.content});

  Map<String, dynamic> toJson() => {'title': title, 'content': content};
}

class CreateNoteResponse {
  final String id;

  CreateNoteResponse({required this.id});

  factory CreateNoteResponse.fromJson(jsonDecode) {
    return CreateNoteResponse(id: jsonDecode['id']);
  }
}
