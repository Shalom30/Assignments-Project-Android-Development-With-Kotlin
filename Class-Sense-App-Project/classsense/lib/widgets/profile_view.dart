import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import '../utils/app_colors.dart';
import '../utils/app_routes.dart';

/// Reusable profile view widget extracted from the 3 duplicated profile tabs
/// (teacher, student, admin). Demonstrates OOP code reuse through composition.
///
/// Accepts [roleName] and [roleColor] to customise per-role appearance,
/// and an optional [roleIcon] override.
class ProfileView extends StatelessWidget {
  final String roleName;
  final Color roleColor;
  final IconData roleIcon;

  const ProfileView({
    super.key,
    required this.roleName,
    this.roleColor = AppColors.skyBlue,
    this.roleIcon = Icons.person,
  });

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    final user = FirebaseAuth.instance.currentUser;

    return SafeArea(
      child: Padding(
        padding: EdgeInsets.all(size.width * 0.05),
        child: Column(
          children: [
            SizedBox(height: size.height * 0.03),
            CircleAvatar(
              radius: size.width * 0.12,
              backgroundColor: AppColors.darkNavy,
              child: Icon(
                roleIcon,
                size: size.width * 0.12,
                color: AppColors.white,
              ),
            ),
            SizedBox(height: size.height * 0.02),
            Text(
              user?.displayName ?? roleName,
              style: TextStyle(
                fontSize: size.width * 0.06,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
            SizedBox(height: size.height * 0.005),
            Text(
              user?.email ?? '',
              style: TextStyle(
                fontSize: size.width * 0.038,
                color: AppColors.darkNavy.withOpacity(0.6),
              ),
            ),
            SizedBox(height: size.height * 0.01),
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 6),
              decoration: BoxDecoration(
                color: roleColor.withOpacity(0.15),
                borderRadius: BorderRadius.circular(20),
              ),
              child: Text(
                roleName,
                style: TextStyle(
                  color: roleColor,
                  fontWeight: FontWeight.w600,
                  fontSize: size.width * 0.035,
                ),
              ),
            ),
            const Spacer(),
            SizedBox(
              width: double.infinity,
              height: size.height * 0.065,
              child: ElevatedButton.icon(
                onPressed: () => _handleLogout(context),
                icon: const Icon(Icons.logout),
                label: const Text('Logout'),
                style: ElevatedButton.styleFrom(
                  backgroundColor: AppColors.alertRed,
                  foregroundColor: AppColors.white,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  elevation: 0,
                ),
              ),
            ),
            SizedBox(height: size.height * 0.03),
          ],
        ),
      ),
    );
  }

  Future<void> _handleLogout(BuildContext context) async {
    await FirebaseAuth.instance.signOut();
    if (context.mounted) {
      Navigator.pushReplacementNamed(context, AppRoutes.login);
    }
  }
}
