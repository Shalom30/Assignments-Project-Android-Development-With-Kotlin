/// A generic sealed Result type for safe async error handling.
/// Eliminates try-catch boilerplate by wrapping success/failure in a single type.
///
/// Usage:
/// ```dart
/// final Result<UserModel> result = await fetchUser();
/// result.when(
///   success: (user) => print(user.name),
///   failure: (error) => print(error),
/// );
/// ```
sealed class Result<T> {
  const Result();

  /// Wraps an async operation and returns [Success] or [Failure].
  static Future<Result<T>> guard<T>(Future<T> Function() action) async {
    try {
      final value = await action();
      return Success(value);
    } catch (e) {
      return Failure(e.toString());
    }
  }

  /// Pattern-matching style fold over Success / Failure.
  R when<R>({
    required R Function(T data) success,
    required R Function(String error) failure,
  });

  /// Returns the value if Success, or null if Failure.
  T? get valueOrNull;

  /// Returns the error message if Failure, or null if Success.
  String? get errorOrNull;

  /// Whether this result is a success.
  bool get isSuccess => this is Success<T>;

  /// Maps the success value to a new type using [transform].
  Result<R> map<R>(R Function(T data) transform) {
    return when(
      success: (data) => Success(transform(data)),
      failure: (error) => Failure(error),
    );
  }
}

/// Represents a successful result holding [data].
class Success<T> extends Result<T> {
  final T data;
  const Success(this.data);

  @override
  R when<R>({
    required R Function(T data) success,
    required R Function(String error) failure,
  }) =>
      success(data);

  @override
  T? get valueOrNull => data;

  @override
  String? get errorOrNull => null;
}

/// Represents a failed result holding an [error] message.
class Failure<T> extends Result<T> {
  final String error;
  const Failure(this.error);

  @override
  R when<R>({
    required R Function(T data) success,
    required R Function(String error) failure,
  }) =>
      failure(error);

  @override
  T? get valueOrNull => null;

  @override
  String? get errorOrNull => error;
}
