import 'package:flutter/material.dart';
import '../../utils/app_colors.dart';

class GeofenceSetup extends StatelessWidget {
  const GeofenceSetup({super.key});

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return Scaffold(
      backgroundColor: AppColors.white,
      appBar: AppBar(
        title: const Text('Geofence Setup'),
        backgroundColor: AppColors.darkNavy,
        foregroundColor: AppColors.white,
      ),
      body: Center(
        child: Padding(
          padding: EdgeInsets.all(size.width * 0.08),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Icon(Icons.location_on,
                  size: size.width * 0.2, color: AppColors.skyBlue),
              SizedBox(height: size.height * 0.02),
              Text(
                'Geofence Configuration',
                style: TextStyle(
                  fontSize: size.width * 0.055,
                  fontWeight: FontWeight.bold,
                  color: AppColors.darkNavy,
                ),
              ),
              SizedBox(height: size.height * 0.01),
              Text(
                'Set up school boundary zones for student safety alerts.',
                textAlign: TextAlign.center,
                style: TextStyle(
                  fontSize: size.width * 0.038,
                  color: AppColors.darkNavy.withOpacity(0.6),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
