# Panduan Kontribusi

Terima kasih atas minat Anda untuk berkontribusi pada proyek **Keva - Aplikasi Persistensi Data Sederhana**. Kami menghargai setiap kontribusi, baik itu dalam bentuk laporan *bug*, perbaikan kode, atau usulan fitur.

Dokumen ini menguraikan prosedur dan pedoman yang harus diikuti saat Anda berpartisipasi dalam proyek.

---

## 🐞 Melaporkan Issue

Sebelum mengajukan Pull Request (PR), jika Anda menemukan *bug* atau memiliki ide untuk fitur baru, silakan laporkan Issue terlebih dahulu.

1.  **Cek Duplikasi:** Pastikan tidak ada Issue yang sudah dibuka yang membahas masalah atau fitur yang sama.
2.  **Gunakan Judul Deskriptif:** Buat judul Issue yang ringkas dan jelas.
3.  **Jelaskan Masalah (*Bug*):**
    * Sediakan **langkah-langkah yang jelas** untuk mereproduksi *bug*.
    * Jelaskan perilaku yang diharapkan dan perilaku yang sebenarnya terjadi.
    * Sertakan informasi lingkungan: Versi Aplikasi, Versi Android Studio, dan API Level Android yang digunakan.
4.  **Saran Fitur:**
    * Jelaskan tujuan dan manfaat dari fitur yang Anda usulkan.

---

## ⚙️ Mengajukan Perubahan (Pull Request)

Kontribusi kode harus mengikuti alur kerja *Git* dan standar teknis proyek.

### 1. Alur Kerja Kontribusi

1.  **Fork** repositori ini ke akun GitHub pribadi Anda.
2.  **Clone** repositori yang sudah Anda *fork* ke lingkungan lokal Anda.
3.  Buat **Branch Baru** dari `main`. Gunakan konvensi penamaan yang jelas: `fix/nama-bug` atau `feat/nama-fitur-baru`.

    ```bash
    git checkout -b nama-branch-anda
    ```

4.  Lakukan perubahan kode. Pastikan semua *build* berjalan bersih dan aplikasi tidak *crash* pada perangkat target.
5.  **Commit** perubahan Anda. Gunakan pesan *commit* yang **imperatif** dan **singkat** (contoh: `feat: Implementasi validasi input NIM`).
6.  **Push** *branch* Anda ke repositori *fork* Anda.
7.  Buka **Pull Request (PR)** dari *branch* Anda ke *branch* `main` di repositori ini.

### 2. Pedoman Teknis dan Kode

* **Arsitektur:** Patuhi **Repository Pattern** yang sudah diterapkan. Operasi data (SharedPreferences dan File I/O) harus melalui layer Repository.
* **Kotlin:** Ikuti [Konvensi Kode Kotlin](https://kotlinlang.org/docs/coding-conventions.html).
* **UI/Compose:** Gunakan komponen **Jetpack Compose dan Material 3** secara eksklusif. Hindari penggunaan View lama (XML) atau *legacy* API.
* **Style Commit:** Pesan *commit* harus jelas. Awali dengan tipe: `fix:`, `feat:`, `docs:`, `refactor:`, dsb.

### 3. Review Pull Request

* Setelah PR diajukan, tim akan melakukan *review* kode.
* Kami mungkin meminta perubahan (revisi) atau klarifikasi.
* Setelah disetujui, PR Anda akan di-*merge* ke *branch* `main`.

---

## 📜 Lisensi

Dengan berkontribusi pada proyek ini, Anda menyetujui bahwa kontribusi Anda akan dilisensikan di bawah **GPL-3.0 License**.
