import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import '../../utils/app_colors.dart';
import '../../utils/app_routes.dart';

class StudentHome extends StatefulWidget {
  const StudentHome({super.key});

  @override
  State<StudentHome> createState() => _StudentHomeState();
}

class _StudentHomeState extends State<StudentHome> {
  int _currentIndex = 0;

  final List<Widget> _tabs = const [
    _HomeTab(),
    _AttendanceHistoryTab(),
    _StudyTab(),
    _ProfileTab(),
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
            icon: Icon(Icons.home_outlined),
            selectedIcon: Icon(Icons.home, color: AppColors.darkNavy),
            label: 'Home',
          ),
          NavigationDestination(
            icon: Icon(Icons.calendar_today_outlined),
            selectedIcon: Icon(Icons.calendar_today, color: AppColors.darkNavy),
            label: 'Attendance',
          ),
          NavigationDestination(
            icon: Icon(Icons.menu_book_outlined),
            selectedIcon: Icon(Icons.menu_book, color: AppColors.darkNavy),
            label: 'Study',
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

// ─── Home Tab ───────────────────────────────────────────────
class _HomeTab extends StatelessWidget {
  const _HomeTab();

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    final classes = [
      {
        'name': 'Mathematics',
        'time': '08:00 - 09:30',
        'room': 'Room 201',
        'status': 'present'
      },
      {
        'name': 'Physics',
        'time': '10:00 - 11:30',
        'room': 'Room 105',
        'status': 'upcoming'
      },
      {
        'name': 'Computer Science',
        'time': '13:00 - 14:30',
        'room': 'Lab 3',
        'status': 'upcoming'
      },
    ];

    return SafeArea(
      child: Padding(
        padding: EdgeInsets.all(size.width * 0.05),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Hello, Student!',
              style: TextStyle(
                fontSize: size.width * 0.065,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
            SizedBox(height: size.height * 0.005),
            Text(
              'Today\'s Classes',
              style: TextStyle(
                fontSize: size.width * 0.04,
                color: AppColors.darkNavy.withOpacity(0.6),
              ),
            ),
            SizedBox(height: size.height * 0.025),
            Expanded(
              child: ListView.separated(
                itemCount: classes.length,
                separatorBuilder: (_, __) => const SizedBox(height: 12),
                itemBuilder: (context, index) {
                  final cls = classes[index];
                  final status = cls['status']!;
                  Color statusColor;
                  String statusLabel;

                  switch (status) {
                    case 'present':
                      statusColor = AppColors.successGreen;
                      statusLabel = 'Present';
                      break;
                    case 'absent':
                      statusColor = AppColors.alertRed;
                      statusLabel = 'Absent';
                      break;
                    default:
                      statusColor = AppColors.warningYellow;
                      statusLabel = 'Upcoming';
                  }

                  return Container(
                    padding: EdgeInsets.all(size.width * 0.04),
                    decoration: BoxDecoration(
                      color: AppColors.lightBlueTint,
                      borderRadius: BorderRadius.circular(16),
                    ),
                    child: Row(
                      children: [
                        Container(
                          width: 4,
                          height: size.height * 0.07,
                          decoration: BoxDecoration(
                            color: statusColor,
                            borderRadius: BorderRadius.circular(2),
                          ),
                        ),
                        SizedBox(width: size.width * 0.04),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                cls['name']!,
                                style: TextStyle(
                                  fontSize: size.width * 0.045,
                                  fontWeight: FontWeight.w600,
                                  color: AppColors.darkNavy,
                                ),
                              ),
                              const SizedBox(height: 4),
                              Text(
                                '${cls['time']}  •  ${cls['room']}',
                                style: TextStyle(
                                  fontSize: size.width * 0.033,
                                  color: AppColors.darkNavy.withOpacity(0.6),
                                ),
                              ),
                            ],
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 12, vertical: 6),
                          decoration: BoxDecoration(
                            color: statusColor.withOpacity(0.15),
                            borderRadius: BorderRadius.circular(20),
                          ),
                          child: Text(
                            statusLabel,
                            style: TextStyle(
                              color: statusColor,
                              fontWeight: FontWeight.w600,
                              fontSize: size.width * 0.032,
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

// ─── Attendance History Tab ─────────────────────────────────
class _AttendanceHistoryTab extends StatelessWidget {
  const _AttendanceHistoryTab();

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    final history = [
      {'date': 'Mon, Mar 9', 'subject': 'Mathematics', 'status': 'present'},
      {'date': 'Mon, Mar 9', 'subject': 'Physics', 'status': 'present'},
      {'date': 'Fri, Mar 6', 'subject': 'CS', 'status': 'absent'},
      {'date': 'Fri, Mar 6', 'subject': 'Mathematics', 'status': 'present'},
      {'date': 'Thu, Mar 5', 'subject': 'Physics', 'status': 'late'},
    ];

    return SafeArea(
      child: Padding(
        padding: EdgeInsets.all(size.width * 0.05),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Attendance History',
              style: TextStyle(
                fontSize: size.width * 0.065,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
            SizedBox(height: size.height * 0.01),
            // Summary row
            Row(
              children: [
                _buildSummaryChip(
                    context, 'Present', '85%', AppColors.successGreen),
                const SizedBox(width: 8),
                _buildSummaryChip(context, 'Absent', '10%', AppColors.alertRed),
                const SizedBox(width: 8),
                _buildSummaryChip(
                    context, 'Late', '5%', AppColors.warningYellow),
              ],
            ),
            SizedBox(height: size.height * 0.02),
            Expanded(
              child: ListView.separated(
                itemCount: history.length,
                separatorBuilder: (_, __) => const SizedBox(height: 8),
                itemBuilder: (context, index) {
                  final item = history[index];
                  final status = item['status']!;
                  Color statusColor;
                  switch (status) {
                    case 'present':
                      statusColor = AppColors.successGreen;
                      break;
                    case 'absent':
                      statusColor = AppColors.alertRed;
                      break;
                    default:
                      statusColor = AppColors.warningYellow;
                  }

                  return Container(
                    padding: EdgeInsets.all(size.width * 0.04),
                    decoration: BoxDecoration(
                      color: AppColors.lightGrey,
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Row(
                      children: [
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                item['subject']!,
                                style: TextStyle(
                                  fontWeight: FontWeight.w600,
                                  color: AppColors.darkNavy,
                                  fontSize: size.width * 0.04,
                                ),
                              ),
                              Text(
                                item['date']!,
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
                              horizontal: 12, vertical: 4),
                          decoration: BoxDecoration(
                            color: statusColor.withOpacity(0.15),
                            borderRadius: BorderRadius.circular(20),
                          ),
                          child: Text(
                            status[0].toUpperCase() + status.substring(1),
                            style: TextStyle(
                              color: statusColor,
                              fontWeight: FontWeight.w600,
                              fontSize: size.width * 0.032,
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

  static Widget _buildSummaryChip(
      BuildContext context, String label, String value, Color color) {
    return Expanded(
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 10),
        decoration: BoxDecoration(
          color: color.withOpacity(0.1),
          borderRadius: BorderRadius.circular(10),
        ),
        child: Column(
          children: [
            Text(value,
                style: TextStyle(
                    fontWeight: FontWeight.bold, color: color, fontSize: 16)),
            Text(label, style: TextStyle(color: color, fontSize: 12)),
          ],
        ),
      ),
    );
  }
}

// ─── Study Tab ──────────────────────────────────────────────
class _StudyTab extends StatelessWidget {
  const _StudyTab();

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;

    final quizzes = [
      {'subject': 'Mathematics', 'topic': 'Algebra Basics', 'questions': '10'},
      {'subject': 'Physics', 'topic': 'Newton\'s Laws', 'questions': '8'},
      {'subject': 'CS', 'topic': 'Data Structures', 'questions': '12'},
    ];

    return SafeArea(
      child: Padding(
        padding: EdgeInsets.all(size.width * 0.05),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Study Assistant',
              style: TextStyle(
                fontSize: size.width * 0.065,
                fontWeight: FontWeight.bold,
                color: AppColors.darkNavy,
              ),
            ),
            SizedBox(height: size.height * 0.005),
            Text(
              'Revision quizzes for your classes',
              style: TextStyle(
                fontSize: size.width * 0.04,
                color: AppColors.darkNavy.withOpacity(0.6),
              ),
            ),
            SizedBox(height: size.height * 0.025),
            Expanded(
              child: ListView.separated(
                itemCount: quizzes.length,
                separatorBuilder: (_, __) => const SizedBox(height: 12),
                itemBuilder: (context, index) {
                  final quiz = quizzes[index];
                  return Container(
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
                            color: AppColors.skyBlue.withOpacity(0.15),
                            borderRadius: BorderRadius.circular(12),
                          ),
                          child: Icon(
                            Icons.menu_book,
                            color: AppColors.skyBlue,
                            size: size.width * 0.07,
                          ),
                        ),
                        SizedBox(width: size.width * 0.04),
                        Expanded(
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                quiz['subject']!,
                                style: TextStyle(
                                  fontSize: size.width * 0.042,
                                  fontWeight: FontWeight.w600,
                                  color: AppColors.darkNavy,
                                ),
                              ),
                              Text(
                                '${quiz['topic']}  •  ${quiz['questions']} questions',
                                style: TextStyle(
                                  fontSize: size.width * 0.032,
                                  color: AppColors.darkNavy.withOpacity(0.6),
                                ),
                              ),
                            ],
                          ),
                        ),
                        Icon(
                          Icons.arrow_forward_ios,
                          size: size.width * 0.04,
                          color: AppColors.skyBlue,
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

// ─── Profile Tab ────────────────────────────────────────────
class _ProfileTab extends StatelessWidget {
  const _ProfileTab();

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
                Icons.person,
                size: size.width * 0.12,
                color: AppColors.white,
              ),
            ),
            SizedBox(height: size.height * 0.02),
            Text(
              user?.displayName ?? 'Student',
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
                color: AppColors.skyBlue.withOpacity(0.15),
                borderRadius: BorderRadius.circular(20),
              ),
              child: Text(
                'Student',
                style: TextStyle(
                  color: AppColors.skyBlue,
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
