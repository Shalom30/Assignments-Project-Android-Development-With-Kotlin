import 'package:flutter/material.dart';
import '../utils/app_colors.dart';

class AttendanceCard extends StatelessWidget {
  final String studentName;
  final String status;
  final String date;

  const AttendanceCard({
    super.key,
    required this.studentName,
    required this.status,
    required this.date,
  });

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    Color statusColor;
    IconData statusIcon;

    switch (status.toLowerCase()) {
      case 'present':
        statusColor = AppColors.successGreen;
        statusIcon = Icons.check_circle;
        break;
      case 'absent':
        statusColor = AppColors.alertRed;
        statusIcon = Icons.cancel;
        break;
      default:
        statusColor = AppColors.warningYellow;
        statusIcon = Icons.access_time;
    }

    return Container(
      padding: EdgeInsets.all(size.width * 0.04),
      decoration: BoxDecoration(
        color: AppColors.lightBlueTint,
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        children: [
          CircleAvatar(
            backgroundColor: AppColors.darkNavy,
            foregroundColor: AppColors.white,
            child: Text(studentName.isNotEmpty ? studentName[0] : '?'),
          ),
          SizedBox(width: size.width * 0.04),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  studentName,
                  style: TextStyle(
                    fontWeight: FontWeight.w600,
                    color: AppColors.darkNavy,
                    fontSize: size.width * 0.04,
                  ),
                ),
                Text(
                  date,
                  style: TextStyle(
                    color: AppColors.darkNavy.withOpacity(0.5),
                    fontSize: size.width * 0.032,
                  ),
                ),
              ],
            ),
          ),
          Icon(statusIcon, color: statusColor),
          SizedBox(width: size.width * 0.02),
          Text(
            status[0].toUpperCase() + status.substring(1),
            style: TextStyle(
              color: statusColor,
              fontWeight: FontWeight.w600,
              fontSize: size.width * 0.035,
            ),
          ),
        ],
      ),
    );
  }
}
