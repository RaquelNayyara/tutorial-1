# Tutorial 1

## Refleksi 1

You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code. Please write your reflection inside the repository's README.md file.

Prinsip clean code yang di terapkan:
- Nama variabel dan fungsi singkat, jelas, dan to the point sehingga kode dapat dijelaskan dengan sendirinya (tanpa perlu komentar)
- Ukuran fungsi kecil dan simple

Praktek kode keamanan coding yang di terapkan:
- Menggunakan method ```POST``` untuk membuat product.

Kesalahan/kesusahan yang saya buat dalam tutorial ini:
- Membuat fungsi test di dalam fungsi utama, bukannya di direktori utama, menyebabkan JUnit tidak terdeteksi oleh editor code. Solusi nya memindahkan semua fungsi test ke direktori tes.
- Selenium tidak berfungsi. Solusi nya kurang ```$```

## Refleksi 2
1. After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?

Saat selesai mengerjakan ```unit-test``` kode saya terasa jauh lebih mudah. Untuk jumlah test pada class sepertinya tidak usah sampai 100% atau minamal 70% saja, karena hanya untuk memastikan uji kode cukup untuk melewati kasus yang akan ditangani. 100% cide coverage tidak selalu berarti kode kita bebas dari bug. Code coverage hanya mengukur sejauh mana kode telah dieksekusi selama test berlangsung.

2. Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner.
Menurut pendapat saya, kode akan menjadi kurang bersih. Alasannya adalah bahwa pengujian fungsional untuk memeriksa detail produk dan berapa banyak produk dalam daftar tidak begitu berbeda. Oleh karena itu, akan ada terlalu banyak kode yang diulang. Solusinya adalah menggabungkan dua kelas menjadi satu dan membuat metode untuk baris kode yang sama.


# Tutorial 2

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
    
- Memperbaiki dan menghindari error dari penggunaan nilai dalam annotation, dengan menghapus value= yang tidak perlu dari annotation, sehingga kode menjadi clean
- Masalah modifier ``public`` dalam interface, saya menghapus modifier `public` yang tidak perlu dalam method interface

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

- Continuous Integration dapat dicapai melalui execute tes dari pull, push, dan merge ke repository, yang diatur dalam alur kerja `ci.yml`
- Continuous Deployment dari Koyeb, mengotomatisasi proses dari pull, push, dan merge ke dalam repository