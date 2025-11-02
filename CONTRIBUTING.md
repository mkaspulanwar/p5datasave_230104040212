# 🤝 Panduan Kontribusi

Terima kasih telah meluangkan waktu untuk berkontribusi pada proyek **Keva - Aplikasi Persistensi Data Sederhana**! Kontribusi Anda sangat dihargai.

Panduan ini menjelaskan proses dan standar yang perlu Anda ikuti untuk mengajukan perubahan (Pull Request) atau melaporkan masalah (Issue).

---

## 🐞 Melaporkan Issue

Jika Anda menemukan *bug* atau memiliki saran fitur, silakan buat Issue baru.

1.  **Cek Duplikasi:** Pastikan belum ada Issue yang dibuka yang membahas masalah atau saran yang sama.
2.  **Jelaskan Masalah:**
    * Gunakan judul yang jelas dan deskriptif.
    * Jelaskan langkah-langkah untuk mereproduksi *bug* (jika ada).
    * Sertakan informasi tentang lingkungan Anda (Perangkat, OS Android, Versi Aplikasi, Versi Android Studio).
    * Jika ini adalah saran fitur, jelaskan kegunaan dan alasannya.

---

## ⚙️ Mengajukan Perubahan (Pull Request)

Kami menyambut kontribusi dalam bentuk perbaikan *bug*, peningkatan fitur, atau perbaikan dokumentasi.

### 1. Proses Pengembangan

1.  **Fork** repositori ini ke akun GitHub Anda.
2.  **Clone** repositori yang sudah Anda *fork* ke lokal Anda.
    ```bash
    git clone [https://github.com/YourUsername/keva-persistence-app.git](https://github.com/YourUsername/keva-persistence-app.git)
    ```
3.  Buat **Branch Baru** untuk perubahan Anda. Gunakan nama yang deskriptif (misalnya: `feature/tambah-validasi-nim` atau `fix/issue-darkmode-persistence`).
    ```bash
    git checkout -b nama-branch-anda
    ```
4.  Lakukan perubahan dan pastikan kode Anda:
    * Berjalan tanpa *crash* di emulator/perangkat target (**minSdk 24, targetSdk 35**).
    * Mengikuti **Konvensi Kotlin** dan **prinsip Clean Code/Repository Pattern** yang sudah ada.
    * Menggunakan **Jetpack Compose dan Material 3** secara konsisten.
5.  **Commit** perubahan Anda. Tulis pesan *commit* yang ringkas dan informatif.
    ```bash
    git commit -m "feat: Menambahkan validasi input NIM agar numerik dan tidak kosong"
    ```
6.  **Push** *branch* Anda ke GitHub.
    ```bash
    git push origin nama-branch-anda
    ```

### 2. Pedoman Kode & Style

* **Kotlin:** Patuhi [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html).
* **Compose:** Hindari menggunakan fungsi Composable di luar file `ui/screens` atau `ui/components` kecuali diperlukan (misalnya di `MainActivity`).
* **Data:** Semua operasi I/O dan persisten data harus melalui **Repository** yang relevan (`FilesRepository.kt` atau `PreferencesRepository.kt`).
* **Navigasi:** Gunakan definisi rute yang ada di `NavGraph.kt`.

### 3. Mengajukan Pull Request (PR)

1.  Buka **Pull Request** dari *branch* Anda ke *branch* `main` di repositori asli.
2.  **Deskripsikan PR:**
    * Jelaskan secara singkat apa yang diubah.
    * Sertakan referensi ke Issue yang relevan (misalnya: `Fixes #12`).
    * Sertakan *screenshot* atau GIF jika perubahan melibatkan UI.
3.  Setelah diajukan, tim pengembang akan melakukan *review* kode. Bersiaplah untuk diskusi dan penyesuaian.

---

## 📝 Lisensi

Dengan berkontribusi, Anda setuju bahwa kontribusi Anda akan dilisensikan di bawah [Lisensi MIT](LICENSE) proyek ini.

Terima kasih atas kontribusi Anda!
