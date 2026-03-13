import 'package:flutter/material.dart';
import '../utils/app_colors.dart';

class NotificationTile extends StatelessWidget {
  final String title;
  final String subtitle;
  final IconData icon;
  final Color? iconColor;

  const NotificationTile({
    super.key,
    required this.title,
    required this.subtitle,
    this.icon = Icons.notifications,
    this.iconColor,
  });

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return Container(
      padding: EdgeInsets.all(size.width * 0.04),
      decoration: BoxDecoration(
        color: AppColors.lightGrey,
        borderRadius: BorderRadius.circular(12),
      ),
      child: Row(
        children: [
          Icon(
            icon,
            color: iconColor ?? AppColors.skyBlue,
            size: size.width * 0.06,
          ),
          SizedBox(width: size.width * 0.04),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  title,
                  style: TextStyle(
                    fontWeight: FontWeight.w600,
                    color: AppColors.darkNavy,
                    fontSize: size.width * 0.038,
                  ),
                ),
                Text(
                  subtitle,
                  style: TextStyle(
                    color: AppColors.darkNavy.withOpacity(0.5),
                    fontSize: size.width * 0.032,
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
