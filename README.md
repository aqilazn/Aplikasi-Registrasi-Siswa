# SiMahasiswa - Aplikasi Registrasi Mahasiswa

Aplikasi Android untuk manajemen registrasi mahasiswa menggunakan **Jetpack Compose**, **Room Database**, dan arsitektur **MVVM**.

---

## 🚀 Cara Membuka di Android Studio

1. Ekstrak file ZIP ini
2. Buka **Android Studio** (versi Hedgehog atau lebih baru)
3. Pilih **File → Open** → arahkan ke folder `StudentRegistApp`
4. Tunggu Gradle sync selesai (butuh koneksi internet untuk download dependencies)
5. Jalankan di emulator atau device Android (min SDK 26 / Android 8.0)

---

## 🔑 Kredensial Login

| Field    | Value                        |
|----------|------------------------------|
| Email    | `admin@simahasiswa.ac.id`    |
| Password | `admin123`                   |

---

## ✨ Fitur Aplikasi

- **Login Admin** — autentikasi hardcoded dengan validasi
- **CRUD Mahasiswa** — Tambah, Lihat, Edit, Hapus data mahasiswa
- **Real-time Search** — cari berdasarkan nama atau NIM
- **Validasi Form** — semua field wajib diisi, validasi format email & IPK
- **Dropdown Dinamis** — fakultas → program studi (saling terkait)
- **Toggle Gender** — pilihan Laki-laki / Perempuan dengan chip button
- **Konfirmasi Hapus** — dialog konfirmasi sebelum hapus permanen
- **Statistik Cepat** — counter total, laki-laki, perempuan di header
- **Card Expandable** — tap kartu mahasiswa untuk lihat detail lengkap
- **Room Database** — data tersimpan lokal di SQLite secara persisten

---

## 🏗️ Struktur Project

```
app/src/main/java/com/studentregist/app/
├── AppConstants.kt           # Data statis (fakultas, prodi, dll)
├── MainActivity.kt
├── data/
│   ├── entity/Student.kt     # Model data / tabel Room
│   ├── dao/StudentDao.kt     # Query database
│   └── database/AppDatabase.kt
├── repository/
│   └── StudentRepository.kt  # Layer abstraksi data
├── viewmodel/
│   └── StudentViewModel.kt   # Business logic + state
└── ui/
    ├── AppTheme.kt           # Warna & tema Material3
    ├── AppNavigation.kt      # State navigasi login/main
    ├── login/LoginScreen.kt
    ├── students/StudentsScreen.kt
    └── components/
        ├── StudentCard.kt
        ├── StudentFormDialog.kt
        └── DeleteConfirmDialog.kt
```

---

## 📦 Tech Stack

| Library | Versi | Kegunaan |
|---------|-------|----------|
| Jetpack Compose | BOM 2024.09 | UI deklaratif |
| Room Database | 2.6.1 | Penyimpanan lokal SQLite |
| ViewModel | 2.8.4 | MVVM architecture |
| Kotlin Coroutines | 1.8.1 | Async operations |
| Material3 | — | Design system |
| Navigation Compose | 2.7.7 | Navigasi antar layar |

---

## 🎨 Desain

- **Warna utama**: Indigo (#4F46E5) + Teal (#14B8A6)
- **UI framework**: Material Design 3
- **Tema**: Light mode dengan gradient header
- **Komponen**: Card, ExtendedFAB, FilterChip, ExposedDropdownMenu
