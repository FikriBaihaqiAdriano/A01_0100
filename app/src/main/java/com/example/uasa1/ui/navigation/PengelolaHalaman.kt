package com.example.uasa1.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyekmanajemen.ui.view.DestinasiHomePage
import com.example.proyekmanajemen.ui.view.HomePage
import com.example.uasa1.ui.view.Anggota.AnggotaTimInsertScreen
import com.example.uasa1.ui.view.Anggota.DestinasiDetailAnggotaTim
import com.example.uasa1.ui.view.Anggota.DestinasiHomeAnggotaTim
import com.example.uasa1.ui.view.Anggota.DestinasiInsertAnggotaTim
import com.example.uasa1.ui.view.Anggota.DestinasiUpdateAnggotaTim
import com.example.uasa1.ui.view.Anggota.DetailAnggotaTimScreen
import com.example.uasa1.ui.view.Anggota.HomeAnggotaTimScreen
import com.example.uasa1.ui.view.Anggota.UpdateAnggotaScreen
import com.example.uasa1.ui.view.Proyek.DestinasiDetailProyek
import com.example.uasa1.ui.view.Proyek.DestinasiHomeProyek
import com.example.uasa1.ui.view.Proyek.DestinasiInsertProyek
import com.example.uasa1.ui.view.Proyek.DestinasiUpdateProyek
import com.example.uasa1.ui.view.Proyek.DetailProyekScreen
import com.example.uasa1.ui.view.Proyek.HomeProyekScreen
import com.example.uasa1.ui.view.Proyek.ProyekInsertScreen
import com.example.uasa1.ui.view.Proyek.UpdateProyekScreen
import com.example.uasa1.ui.view.Tim.DestinasiDetailTim
import com.example.uasa1.ui.view.Tim.DestinasiHomeTim
import com.example.uasa1.ui.view.Tim.DestinasiInsertTim
import com.example.uasa1.ui.view.Tim.DestinasiUpdateTim
import com.example.uasa1.ui.view.Tim.DetailTimScreen
import com.example.uasa1.ui.view.Tim.HomeTimScreen
import com.example.uasa1.ui.view.Tim.TimInsertScreen
import com.example.uasa1.ui.view.Tim.UpdateTimScreen
import com.example.uasa1.ui.view.Tugas.DestinasiDetailTugas
import com.example.uasa1.ui.view.Tugas.DestinasiHomeTugas
import com.example.uasa1.ui.view.Tugas.DestinasiInsertTugas
import com.example.uasa1.ui.view.Tugas.DestinasiUpdateTugas
import com.example.uasa1.ui.view.Tugas.DetailTugasScreen
import com.example.uasa1.ui.view.Tugas.HomeTugasScreen
import com.example.uasa1.ui.view.Tugas.TugasInsertScreen
import com.example.uasa1.ui.view.Tugas.UpdateTugasScreen

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeProyek.route,
        modifier = Modifier
    ) {
        // Home Page
        composable(route = DestinasiHomePage.route) {
            HomePage(
                onAddProject = { navController.navigate(DestinasiHomeProyek.route) },
                onManageTeams = { navController.navigate(DestinasiHomeTim.route) },
                onManageMembers = { navController.navigate(DestinasiHomeAnggotaTim.route) }
            )
        }

        // Tugas
        composable(DestinasiHomeTugas.route) {
            HomeTugasScreen(
                onBackClick = { navController.popBackStack() },
                navigateToTugasEntry = { navController.navigate(DestinasiInsertTugas.route) },
                navigateToTugasUpdate = { id -> navController.navigate("${DestinasiUpdateTugas.route}/$id") },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailTugas.route}/$id")
                }
            )
        }

        composable(DestinasiInsertTugas.route) {
            TugasInsertScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTugas.route) {
                        popUpTo(DestinasiHomeTugas.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailTugas.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailTugas.ID_TUGAS) {
                type = NavType.IntType
            })
        ) {
            val id_tugas = it.arguments?.getInt(DestinasiDetailTugas.ID_TUGAS) ?: -1
            DetailTugasScreen(
                id_tugas = id_tugas,
                onBackClick = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateTugas.route}/$id")
                }
            )
        }

        composable(
            route = DestinasiUpdateTugas.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateTugas.ID_TUGAS) {
                type = NavType.IntType
            })
        ) {
            val id_tugas = it.arguments?.getInt(DestinasiUpdateTugas.ID_TUGAS) ?: -1
            id_tugas?.let { id_tugas ->
                UpdateTugasScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeTugas.route) {
                            popUpTo(DestinasiHomeTugas.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Tim
        composable(DestinasiHomeTim.route) {
            HomeTimScreen(
                onBackClick = { navController.popBackStack() },
                navigateToTimEntry = { navController.navigate(DestinasiInsertTim.route) },
                navigateToTimUpdate = { id -> navController.navigate("${DestinasiUpdateTim.route}/$id") },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailTim.route}/$id")
                }
            )
        }

        composable(DestinasiInsertTim.route) {
            TimInsertScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTim.route) {
                        popUpTo(DestinasiHomeTim.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailTim.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailTim.ID_TIM) {
                type = NavType.IntType
            })
        ) {
            val id_tim = it.arguments?.getInt(DestinasiDetailTim.ID_TIM) ?: -1
            DetailTimScreen(
                id_tim = id_tim,
                onBackClick = { navController.popBackStack() },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateTim.route}/$id")
                }
            )
        }

        composable(
            route = DestinasiUpdateTim.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateTim.ID_TIM) {
                type = NavType.IntType
            })
        ) {
            val id_tim = it.arguments?.getInt(DestinasiUpdateTim.ID_TIM) ?: -1
            id_tim?.let { id_tim ->
                UpdateTimScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeTim.route) {
                            popUpTo(DestinasiHomeTim.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Proyek
        composable(DestinasiHomeProyek.route) {
            HomeProyekScreen(
                onBackClick = { navController.popBackStack() },
                navigateToProyekEntry = { navController.navigate(DestinasiInsertProyek.route) },
                navigateToProyekUpdate = { id -> navController.navigate("${DestinasiUpdateProyek.route}/$id") },
                onDetailClick = { id -> navController.navigate("${DestinasiDetailProyek.route}/$id") },
                navigateToHome = { navController.navigate(DestinasiHomePage.route) }
            )
        }

        composable(
            route = DestinasiInsertProyek.route
        ) {
            ProyekInsertScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeProyek.route) {
                        popUpTo(DestinasiHomeProyek.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailProyek.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailProyek.ID_PROYEK) {
                type = NavType.IntType
            })
        ) {
            val id_proyek = it.arguments?.getInt(DestinasiDetailProyek.ID_PROYEK) ?: -1
            DetailProyekScreen(
                id_proyek = id_proyek,
                onBackClick = { navController.popBackStack() },
                onNavigateToHomeTugas = { navController.navigate(DestinasiHomeTugas.route) },
                onEditClick = { id ->
                    navController.navigate("${DestinasiUpdateProyek.route}/$id")

                }
            )
        }

        composable(
            route = DestinasiUpdateProyek.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateProyek.ID_PROYEK) {
                type = NavType.IntType
            })
        ) {
            val id_proyek = it.arguments?.getInt(DestinasiUpdateProyek.ID_PROYEK) ?: -1
            id_proyek?.let { id_proyek ->
                UpdateProyekScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeProyek.route) {
                            popUpTo(DestinasiHomeProyek.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Anggota Tim
        composable(DestinasiHomeAnggotaTim.route) {
            HomeAnggotaTimScreen(
                onBackClick = { navController.popBackStack() },
                navigateToAnggotaTimEntry = { navController.navigate(DestinasiInsertAnggotaTim.route) },
                onDetailClick = { id -> navController.navigate("${DestinasiDetailAnggotaTim.route}/$id") },
                navigateToAnggotaUpdate = { id -> navController.navigate("${DestinasiUpdateAnggotaTim.route}/$id") }
            )
        }

        composable(DestinasiInsertAnggotaTim.route) {
            AnggotaTimInsertScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeAnggotaTim.route) {
                        popUpTo(DestinasiHomeAnggotaTim.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DestinasiDetailAnggotaTim.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailAnggotaTim.ID_ANGGOTA) {
                type = NavType.IntType
            })
        ) {
            val id_anggota = it.arguments?.getInt(DestinasiDetailAnggotaTim.ID_ANGGOTA) ?: -1
            DetailAnggotaTimScreen(
                id_anggota = id_anggota,
                onBackClick = { navController.popBackStack() },
                onEditClick = { id -> navController.navigate("${DestinasiUpdateAnggotaTim.route}/$id") }
            )
        }

        composable(
            route = DestinasiUpdateAnggotaTim.routeWithArgs,
            arguments = listOf(navArgument(DestinasiUpdateAnggotaTim.ID_ANGGOTA) {
                type = NavType.IntType
            })
        ) {
            val id_anggota = it.arguments?.getInt(DestinasiUpdateAnggotaTim.ID_ANGGOTA) ?: -1
            id_anggota?.let { id_anggota ->
                UpdateAnggotaScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeAnggotaTim.route) {
                            popUpTo(DestinasiHomeAnggotaTim.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
