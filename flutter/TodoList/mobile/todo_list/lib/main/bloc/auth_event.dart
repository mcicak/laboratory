part of 'auth_bloc.dart';

abstract class AuthEvent {}

class LoginEvent extends AuthEvent {
  final String username;
  final String password;

  LoginEvent({required this.username, required this.password});
}

class RegisterEvent extends AuthEvent {
  final String firstname;
  final String lastname;
  final String username;
  final String password;

  RegisterEvent({
    required this.firstname,
    required this.lastname,
    required this.username,
    required this.password,
  });
}