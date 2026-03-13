import 'package:flutter/material.dart';
import '../utils/app_colors.dart';

class EngagementChart extends StatelessWidget {
  final List<double> scores;

  const EngagementChart({super.key, required this.scores});

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return Container(
      width: double.infinity,
      padding: EdgeInsets.all(size.width * 0.04),
      decoration: BoxDecoration(
        color: AppColors.lightBlueTint,
        borderRadius: BorderRadius.circular(16),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Engagement Overview',
            style: TextStyle(
              fontSize: size.width * 0.045,
              fontWeight: FontWeight.bold,
              color: AppColors.darkNavy,
            ),
          ),
          SizedBox(height: size.height * 0.02),
          SizedBox(
            height: size.height * 0.15,
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: scores.map((score) {
                return Expanded(
                  child: Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 4),
                    child: FractionallySizedBox(
                      heightFactor: score / 100,
                      child: Container(
                        decoration: BoxDecoration(
                          color: score > 70
                              ? AppColors.successGreen
                              : score > 40
                                  ? AppColors.warningYellow
                                  : AppColors.alertRed,
                          borderRadius: BorderRadius.circular(4),
                        ),
                      ),
                    ),
                  ),
                );
              }).toList(),
            ),
          ),
        ],
      ),
    );
  }
}
