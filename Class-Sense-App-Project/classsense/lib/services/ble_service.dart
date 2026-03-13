// BLE service disabled - flutter_blue_plus dependency removed due to Gradle build issues.
// This service was not being used in the app.
// Can be re-enabled with a newer BLE library after fixing the build pipeline.

/*
import 'package:flutter_blue_plus/flutter_blue_plus.dart';

class BleService {
  Stream<List<ScanResult>> get scanResults => FlutterBluePlus.scanResults;

  Future<void> startScan(
      {Duration timeout = const Duration(seconds: 10)}) async {
    await FlutterBluePlus.startScan(timeout: timeout);
  }

  Future<void> stopScan() async {
    await FlutterBluePlus.stopScan();
  }

  Stream<bool> get isScanning => FlutterBluePlus.isScanning;
}
*/
