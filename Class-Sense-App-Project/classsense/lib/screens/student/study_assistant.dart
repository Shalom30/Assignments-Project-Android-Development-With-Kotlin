import 'package:flutter/material.dart';
import '../../utils/app_colors.dart';

class StudyAssistant extends StatelessWidget {
  const StudyAssistant({super.key});

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return Scaffold(
      backgroundColor: AppColors.white,
      appBar: AppBar(
        title: const Text('Study Assistant'),
        backgroundColor: AppColors.darkNavy,
        foregroundColor: AppColors.white,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.menu_book,
                size: size.width * 0.2, color: AppColors.skyBlue),
            SizedBox(height: size.height * 0.02),
            Text(
              'Study Assistant',
              style: TextStyle(
                fontSize: size.width * 0.05,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
