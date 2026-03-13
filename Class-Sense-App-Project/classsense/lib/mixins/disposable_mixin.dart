import 'dart:async';

/// Mixin that provides lifecycle management for stream subscriptions
/// and other disposable resources. Classes mixing this in should call
/// [dispose] when they are no longer needed.
///
/// Demonstrates: mixin, OOP lifecycle pattern, higher-order resource tracking.
mixin DisposableMixin {
  final List<StreamSubscription> _subscriptions = [];

  /// Registers a [StreamSubscription] for automatic disposal.
  void addSubscription(StreamSubscription subscription) {
    _subscriptions.add(subscription);
  }

  /// Listens to a [Stream] and auto-registers the subscription.
  StreamSubscription<T> autoListen<T>(
    Stream<T> stream,
    void Function(T event) onData, {
    Function? onError,
    void Function()? onDone,
  }) {
    final sub = stream.listen(onData, onError: onError, onDone: onDone);
    _subscriptions.add(sub);
    return sub;
  }

  /// Cancels all tracked subscriptions and clears the list.
  void dispose() {
    for (final sub in _subscriptions) {
      sub.cancel();
    }
    _subscriptions.clear();
  }
}
