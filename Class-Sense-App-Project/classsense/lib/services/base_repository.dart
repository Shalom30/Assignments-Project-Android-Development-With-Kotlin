import 'package:cloud_firestore/cloud_firestore.dart';
import '../models/base_model.dart';

/// Generic Firestore repository providing type-safe CRUD operations.
/// Works with any model that extends [BaseModel] and has a [ModelFactory].
class BaseRepository<T extends BaseModel<T>> {
  final FirebaseFirestore _firestore;
  final String collectionPath;
  final ModelFactory<T> fromMap;

  BaseRepository({
    required this.collectionPath,
    required this.fromMap,
    FirebaseFirestore? firestore,
  }) : _firestore = firestore ?? FirebaseFirestore.instance;

  CollectionReference<Map<String, dynamic>> get _collection =>
      _firestore.collection(collectionPath);

  /// Adds a new document (auto-generated ID).
  Future<DocumentReference<Map<String, dynamic>>> add(T model) {
    return _collection.add(model.toMap());
  }

  /// Sets a document with a specific ID (creates or overwrites).
  Future<void> set(T model) {
    return _collection.doc(model.id).set(model.toMap());
  }

  /// Updates specific fields of a document.
  Future<void> update(String id, Map<String, dynamic> data) {
    return _collection.doc(id).update(data);
  }

  /// Deletes a document by ID.
  Future<void> delete(String id) {
    return _collection.doc(id).delete();
  }

  /// Gets a single document by ID.
  Future<T?> getById(String id) async {
    final doc = await _collection.doc(id).get();
    if (doc.exists && doc.data() != null) {
      return fromMap(doc.data()!, doc.id);
    }
    return null;
  }

  /// Returns a real-time stream of a single document.
  Stream<T?> streamById(String id) {
    return _collection.doc(id).snapshots().map((doc) {
      if (doc.exists && doc.data() != null) {
        return fromMap(doc.data()!, doc.id);
      }
      return null;
    });
  }

  /// Returns a real-time stream of all documents in the collection.
  Stream<List<T>> streamAll() {
    return _collection.snapshots().map((snapshot) =>
        snapshot.docs.map((doc) => fromMap(doc.data(), doc.id)).toList());
  }

  /// Returns a filtered & ordered stream (generic query builder).
  Stream<List<T>> streamWhere({
    required String field,
    required dynamic isEqualTo,
    String? orderByField,
    bool descending = false,
  }) {
    Query<Map<String, dynamic>> query =
        _collection.where(field, isEqualTo: isEqualTo);
    if (orderByField != null) {
      query = query.orderBy(orderByField, descending: descending);
    }
    return query.snapshots().map((snapshot) =>
        snapshot.docs.map((doc) => fromMap(doc.data(), doc.id)).toList());
  }
}
