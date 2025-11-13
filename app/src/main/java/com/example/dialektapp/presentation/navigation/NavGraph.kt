package com.example.dialektapp.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.dialektapp.presentation.screens.achievements.AchievementsScreen
import com.example.dialektapp.presentation.screens.activity.ActivityScreen
import com.example.dialektapp.presentation.screens.course.CourseDetailScreen
import com.example.dialektapp.presentation.screens.lessons.ModuleLessonsScreen
import com.example.dialektapp.presentation.screens.home.HomeScreen
import com.example.dialektapp.presentation.screens.leaderboard.LeaderboardScreen
import com.example.dialektapp.presentation.navigation.MainScreenWrapper

@Composable
fun NavGraph(
    navController: NavHostController,
    loginScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    forgotPasswordScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit = {},
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(400),
                initialOffsetX = { it / 3 }
            ) + fadeIn(
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                animationSpec = tween(400),
                targetOffsetX = { -it / 3 }
            ) + fadeOut(
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                animationSpec = tween(400),
                initialOffsetX = { -it / 3 }
            ) + fadeIn(
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = tween(400),
                targetOffsetX = { it / 3 }
            ) + fadeOut(
                animationSpec = tween(400)
            )
        }
    ) {
        authNavGraph(
            loginScreenContent = loginScreenContent,
            signUpScreenContent = signUpScreenContent,
            forgotPasswordScreenContent = forgotPasswordScreenContent
        )

        composable<Routes.Home> {
            MainScreenWrapper(
                currentRoute = currentRoute,
                navController = navController
            ) {
                HomeScreen(
                    onProfileClick = {
                        navController.navigate(Routes.Profile)
                    },
                    onCourseClick = { courseId ->
                        val courseName = when (courseId) {
                            "transcarpathian" -> "Закарпатський діалект"
                            "galician" -> "Галицький діалект"
                            "kuban" -> "Кубанський діалект"
                            "lemko" -> "Лемківський діалект"
                            else -> "Курс діалекту"
                        }
                        navController.navigate(Routes.CourseDetail(courseId, courseName))
                    }
                )
            }
        }

        composable<Routes.CourseDetail> { backStackEntry ->
            val courseDetail = backStackEntry.toRoute<Routes.CourseDetail>()
            CourseDetailScreen(
                courseId = courseDetail.courseId,
                courseName = courseDetail.courseName,
                onBackClick = {
                    navController.popBackStack()
                },
                onModuleClick = { moduleId ->
                    navController.navigate(
                        Routes.ModuleLessons(
                            moduleId = moduleId,
                            moduleTitle = "Модуль $moduleId"
                        )
                    )
                }
            )
        }

        composable<Routes.ModuleLessons> { backStackEntry ->
            val moduleLessons = backStackEntry.toRoute<Routes.ModuleLessons>()
            ModuleLessonsScreen(
                moduleId = moduleLessons.moduleId,
                moduleTitle = moduleLessons.moduleTitle,
                onBackClick = {
                    navController.popBackStack()
                },
                onActivityClick = { activityId ->
                    navController.navigate(Routes.Activity(activityId = activityId))
                }
            )
        }

        composable<Routes.Leaderboard> {
            MainScreenWrapper(
                currentRoute = currentRoute,
                navController = navController
            ) {
                LeaderboardScreen()
            }
        }

        composable<Routes.Profile> {
            MainScreenWrapper(
                currentRoute = currentRoute,
                navController = navController
            ) {
                profileScreenContent()
            }
        }

        composable<Routes.Achievements> {
            MainScreenWrapper(
                currentRoute = currentRoute,
                navController = navController
            ) {
                AchievementsScreen(
                    onNavigateBack = {
                        if (!navController.popBackStack()) {
                            navController.navigate(Routes.Home)
                        }
                    }
                )
            }
        }

        composable<Routes.Activity> { backStackEntry ->
            val activity = backStackEntry.toRoute<Routes.Activity>()
            ActivityScreen(
                activityId = activity.activityId,
                onBackClick = {
                    navController.popBackStack()
                },
                onActivityComplete = {
                    navController.popBackStack()
                }
            )
        }
    }
}