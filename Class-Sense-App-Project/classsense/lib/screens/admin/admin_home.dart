import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import '../../utils/app_colors.dart';
import '../../utils/app_routes.dart';

class AdminHome extends StatefulWidget {
  const AdminHome({super.key});

  @override
  State<AdminHome> createState() => _AdminHomeState();
}

class _AdminHomeState extends State<AdminHome> {
  int _currentIndex = 0;

  final List<Widget> _tabs = const [
    _AdminDashboard(),
    _ManageUsersTab(),
    _AdminProfileTab(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.white,
      body: _tabs[_currentIndex],
      bottomNavigationBar: NavigationBar(
        selectedIndex: _currentIndex,
        onDestinationSelected: (index) => setState(() => _currentIndex = index),
        backgroundColor: AppColors.white,
        indicatorColor: AppColors.skyBlue.withOpacity(0.15),
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.dashboard_outlined),
            selectedIcon: Icon(Icons.dashboard, color: AppColors.darkNavy),
            label: 'Dashboard',
          ),
          NavigationDestination(
            icon: Icon(Icons.people_outline),
            selectedIcon: Icon(Icons.people, color: AppColors.darkNavy),
            label: 'Users',
          ),
          NavigationDestination(
            icon: Icon(Icons.person_outline),
            selectedIcon: Icon(Icons.person, color: AppColors.darkNavy),
            label: 'Profile',
          ),
        ],
      ),
    );
  }
}

// ─── Admin Dashboard ────────────────────────────────────────
class _AdminDashboard extends StatelessWidget {
  const _AdminDashboard();

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    return SafeArea(
      child: Padding(
        padding: EdgeInsets.all(size.width * 0.05),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Admin Dashboard',
              style: TextStyle(
                fontSize: size.width * 0.065,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
            SizedBox(height: size.height * 0.01),
            Text(
              'System overview',
              style: TextStyle(
                fontSize: size.width * 0.04,
                color: AppColors.darkNavy.withOpacity(0.6),
              ),
            ),
            SizedBox(height: size.height * 0.03),
            _buildStatCard(
                context, Icons.people, 'Total Users', '128', AppColors.skyBlue),
            SizedBox(height: size.height * 0.015),
            _buildStatCard(
                context, Icons.school, 'Teachers', '12', AppColors.darkNavy),
            SizedBox(height: size.height * 0.015),
            _buildStatCard(context, Icons.person, 'Students', '115',
                AppColors.successGreen),
            SizedBox(height: size.height * 0.015),
            _buildStatCard(context, Icons.location_on, 'Geofences Active', '3',
                AppColors.warningYellow),
          ],
        ),
      ),
    );
  }

  static Widget _buildStatCard(BuildContext context, IconData icon,
      String title, String value, Color color) {
    final size = MediaQuery.of(context).size;
    return Container(
      width: double.infinity,
      padding: EdgeInsets.all(size.width * 0.045),
      decoration: BoxDecoration(
        color: AppColors.lightBlueTint,
        borderRadius: BorderRadius.circular(16),
      ),
      child: Row(
        children: [
          Container(
            padding: EdgeInsets.all(size.width * 0.03),
            decoration: BoxDecoration(
              color: color.withOpacity(0.15),
              borderRadius: BorderRadius.circular(12),
            ),
            child: Icon(icon, color: color, size: size.width * 0.07),
          ),
          SizedBox(width: size.width * 0.04),
          Expanded(
            child: Text(
              title,
              style: TextStyle(
                fontSize: size.width * 0.04,
                color: AppColors.darkNavy,
                fontWeight: FontWeight.w500,
              ),
            ),
          ),
          Text(
            value,
            style: TextStyle(
              fontSize: size.width * 0.06,
              fontWeight: FontWeight.bold,
              color: AppColors.darkNavy,
            ),
          ),
        ],
      ),
    );
  }
}

// ─── Manage Users Tab ───────────────────────────────────────
class _ManageUsersTab extends StatelessWidget {
  const _ManageUsersTab();

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    final users = [
      {'name': 'John Doe', 'email': 'john@mail.com', 'role': 'Teacher'},
      {'name': 'Jane Smith', 'email': 'jane@mail.com', 'role': 'Student'},
      {'name': 'Mike Johnson', 'email': 'mike@mail.com', 'role': 'Student'},
      {'name': 'Sarah Connor', 'email': 'sarah@mail.com', 'role': 'Teacher'},
    ];

    return SafeArea(
      child: Padding(
        padding: EdgeInsets.all(size.width * 0.05),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Manage Users',
              style: TextStyle(
                fontSize: size.width * 0.065,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
            SizedBox(height: size.height * 0.02),
            Expanded(
              child: ListView.separated(
                itemCount: users.length,
                separatorBuilder: (_, __) => const SizedBox(height: 8),
                itemBuilder: (context, index) {
                  final u = users[index];
                  return Container(
                    padding: EdgeInsets.all(size.width * 0.04),
                    decoration: BoxDecoration(
                      color: AppColors.lightGrey,
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Row(
                      children: [
                        CircleAvatar(
                          backgroundColor: AppColors.darkNavy,
                          foregroundColor: AppColors.white,
                          child: Text(u['name']![0]),
                        ),
                        SizedBox(width: size.width * 0.04),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                u['name']!,
                                style: TextStyle(
                                  fontWeight: FontWeight.w600,
                                  color: AppColors.darkNavy,
                                  fontSize: size.width * 0.04,
                                ),
                              ),
                              Text(
                                u['email']!,
                                style: TextStyle(
                                  color: AppColors.darkNavy.withOpacity(0.5),
                                  fontSize: size.width * 0.032,
                                ),
                              ),
                            ],
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 10, vertical: 4),
                          decoration: BoxDecoration(
                            color: u['role'] == 'Teacher'
                                ? AppColors.skyBlue.withOpacity(0.15)
                                : AppColors.successGreen.withOpacity(0.15),
                            borderRadius: BorderRadius.circular(20),
                          ),
                          child: Text(
                            u['role']!,
                            style: TextStyle(
                              color: u['role'] == 'Teacher'
                                  ? AppColors.skyBlue
                                  : AppColors.successGreen,
                              fontWeight: FontWeight.w600,
                              fontSize: size.width * 0.03,
                            ),
                          ),
                        ),
                      ],
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

// ─── Admin Profile Tab ──────────────────────────────────────
class _AdminProfileTab extends StatelessWidget {
  const _AdminProfileTab();

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
                Icons.admin_panel_settings,
                size: size.width * 0.12,
                color: AppColors.white,
              ),
            ),
            SizedBox(height: size.height * 0.02),
            Text(
              user?.displayName ?? 'Admin',
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
                color: AppColors.alertRed.withOpacity(0.15),
                borderRadius: BorderRadius.circular(20),
              ),
              child: Text(
                'Admin',
                style: TextStyle(
                  color: AppColors.alertRed,
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
                onPressed: () async {
                  await FirebaseAuth.instance.signOut();
                  if (context.mounted) {
                    Navigator.pushReplacementNamed(context, AppRoutes.login);
                  }
                },
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
}
