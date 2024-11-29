// main.dart
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:get_it/get_it.dart';
import 'package:http/http.dart' as http;
import 'package:todo_list/core/data/dto.dart';
import 'dart:convert';

import '../core/data/service_api.dart';
import 'bloc/auth_bloc.dart';
import 'package:http/http.dart' as http;
import 'dart:io';

// import '../auth/presentation/pages/login_page.dart';
// import '../auth/data/repositories/auth_repository_impl.dart';
// import '../auth/domain/repositories/auth_repository.dart';

void main() {
  setupDependencies();
  runApp(const MyApp());
}

void setupDependencies() {
  final getIt = GetIt.instance;

  // Register ServiceApi as a singleton
  getIt.registerLazySingleton<ServiceApi>(() => ServiceApi(NetworkConfiguration.local()));

  // Register AuthRepository and inject the ServiceApi instance
  getIt.registerLazySingleton<AuthRepository>(
    () => AuthRepositoryImpl(getIt<ServiceApi>()),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Notes App',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        useMaterial3: true,
      ),
      home: const LoginPage(),
    );
  }
}

abstract class AuthRepository {
  Future<void> login(String username, String password);

  Future<void> register(String firstname, String lastname, String username, String password);
}

class UserDTO {
  final String firstname;
  final String lastname;
  final String username;

  UserDTO({required this.firstname, required this.lastname, required this.username});

  factory UserDTO.fromJson(Map<String, dynamic> json) {
    return UserDTO(
      firstname: json['firstname'],
      lastname: json['lastname'],
      username: json['username'],
    );
  }
}

class AuthRepositoryImpl implements AuthRepository {
  final ServiceApi serviceApi;

  AuthRepositoryImpl(this.serviceApi);

  @override
  Future<void> login(String username, String password) async {
    final response = await serviceApi.login(username, password);
    serviceApi.setAuthToken(response.token);
  }

  @override
  Future<void> register(String firstname, String lastname, String username, String password) async {
    await serviceApi.register(firstname, lastname, username, password);
  }
}

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final authRepository = GetIt.instance<AuthRepository>();

    return BlocProvider(
      create: (context) => AuthBloc(authRepository: authRepository),
      child: Scaffold(
        appBar: AppBar(title: const Text("Login")),
        body: BlocConsumer<AuthBloc, AuthState>(
          listener: (context, state) {
            if (state is AuthSuccess) {
              Navigator.pushNamed(context, '/notes');
            } else if (state is AuthFailure) {
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text(state.message)),
              );
            }
          },
          builder: (context, state) {
            if (state is AuthLoading) {
              return const Center(child: CircularProgressIndicator());
            }

            return Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: [
                  TextField(
                    controller: usernameController,
                    decoration: const InputDecoration(labelText: "Username"),
                  ),
                  TextField(
                    controller: passwordController,
                    decoration: const InputDecoration(labelText: "Password"),
                    obscureText: true,
                  ),
                  const SizedBox(height: 20),
                  ElevatedButton(
                    onPressed: () {
                      final username = usernameController.text.trim();
                      final password = passwordController.text.trim();

                      if (username.isEmpty || password.isEmpty) {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(content: Text("Please fill in all fields")),
                        );
                        return;
                      }

                      context.read<AuthBloc>().add(LoginEvent(
                        username: username,
                        password: password,
                      ));
                    },
                    child: const Text("Login"),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => const RegisterPage()),
                      );
                    },
                    child: const Text("Register"),
                  ),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {
  final TextEditingController firstnameController = TextEditingController();
  final TextEditingController lastnameController = TextEditingController();
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final authRepository = GetIt.instance<AuthRepository>();

    return BlocProvider(
      create: (context) => AuthBloc(authRepository: authRepository),
      child: Scaffold(
        appBar: AppBar(title: const Text("Register")),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Builder(
            // Builder provides a new context inside BlocProvider
            builder: (context) => Column(
              children: [
                TextField(
                  controller: firstnameController,
                  decoration: const InputDecoration(labelText: "Firstname"),
                ),
                TextField(
                  controller: lastnameController,
                  decoration: const InputDecoration(labelText: "Lastname"),
                ),
                TextField(
                  controller: usernameController,
                  decoration: const InputDecoration(labelText: "Username"),
                ),
                TextField(
                  controller: passwordController,
                  decoration: const InputDecoration(labelText: "Password"),
                  obscureText: true,
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Use the Builder's context here
                    final authBloc = context.read<AuthBloc>();
                    authBloc.add(RegisterEvent(
                      firstname: firstnameController.text,
                      lastname: lastnameController.text,
                      username: usernameController.text,
                      password: passwordController.text,
                    ));
                  },
                  child: const Text("Register"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
