package com.studentregist.app

object AppConstants {

    const val ADMIN_EMAIL = "admin@simahasiswa.ac.id"
    const val ADMIN_PASSWORD = "admin123"

    val FACULTIES = listOf(
        "FSAD – Fakultas Sains dan Analitika Data",
        "FTIRS – Fakultas Teknologi Industri dan Rekayasa Sistem",
        "FTK – Fakultas Teknologi Kelautan",
        "FTSPK – Fakultas Teknik Sipil, Perencanaan, dan Kebumian",
        "FTEIC – Fakultas Teknologi Elektro dan Informatika Cerdas",
        "FDKBD – Fakultas Desain Kreatif dan Bisnis Digital",
        "Fakultas Vokasi",
        "FKK – Fakultas Kedokteran dan Kesehatan",
        "SIMT – Sekolah Interdisiplin Manajemen dan Teknologi"
    )

    val MAJORS = mapOf(
        "FSAD – Fakultas Sains dan Analitika Data" to listOf(
            "Fisika",
            "Matematika",
            "Statistika",
            "Kimia",
            "Biologi",
            "Aktuaria"
        ),
        "FTIRS – Fakultas Teknologi Industri dan Rekayasa Sistem" to listOf(
            "Teknik Mesin",
            "Teknik Kimia",
            "Teknik Fisika",
            "Teknik Sistem dan Industri",
            "Teknik Material dan Metalurgi",
            "Teknik Pangan",
            "Rekayasa Keselamatan Proses"
        ),
        "FTK – Fakultas Teknologi Kelautan" to listOf(
            "Teknik Perkapalan",
            "Teknik Sistem Perkapalan",
            "Teknik Kelautan",
            "Teknik Transportasi Laut",
            "Teknik Lepas Pantai"
        ),
        "FTSPK – Fakultas Teknik Sipil, Perencanaan, dan Kebumian" to listOf(
            "Teknik Sipil",
            "Arsitektur",
            "Teknik Lingkungan",
            "Teknik Geomatika",
            "Perencanaan Wilayah dan Kota",
            "Teknik Geofisika",
            "Teknik Pertambangan"
        ),
        "FTEIC – Fakultas Teknologi Elektro dan Informatika Cerdas" to listOf(
            "Teknik Elektro",
            "Teknik Informatika",
            "Sistem Informasi",
            "Teknik Komputer",
            "Teknik Biomedik",
            "Teknologi Informasi",
            "Teknik Telekomunikasi",
            "Inovasi Digital",
            "Rekayasa Perangkat Lunak",
            "Rekayasa Kecerdasan Artifisial"
        ),
        "FDKBD – Fakultas Desain Kreatif dan Bisnis Digital" to listOf(
            "Desain Produk",
            "Desain Interior",
            "Desain Komunikasi Visual",
            "Manajemen Bisnis",
            "Studi Pembangunan",
            "Bisnis Digital",
            "Sains Komunikasi"
        ),
        "Fakultas Vokasi" to listOf(
            "Teknik Infrastruktur Sipil",
            "Teknik Mesin Industri",
            "Teknik Elektro Otomasi",
            "Teknik Kimia Industri",
            "Teknik Instrumentasi",
            "Statistika Bisnis"
        ),
        "FKK – Fakultas Kedokteran dan Kesehatan" to listOf(
            "Kedokteran",
            "Teknologi Kedokteran"
        ),
        "SIMT – Sekolah Interdisiplin Manajemen dan Teknologi" to listOf(
            "S2 Manajemen Teknologi",
            "S3 Manajemen Teknologi",
            "S2 Inovasi Sistem dan Teknologi",
            "Program Profesi Insinyur"
        )
    )

    val YEARS = listOf("2020", "2021", "2022", "2023", "2024", "2025")
}