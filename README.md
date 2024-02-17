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


# Tutorial 3

### 1. Explain what principles you apply to your project!
Prinsip yang saya terapkan pada tutorial 3:

- Menghapus extend `ProductController` dalam class `CarController` dan memisahkan ke dalam file java masing masing untuk mengikuti SRP, karena `CarController` saat di extend hanya pengendalian untuk Car saja bukan untuk produk mengikuti SRP.
- Menghapus extend `ProductController` dalam class `CarController` jadi tidak ada inheritance dari superclass ke subclass, sehingga mengikuti LSP.
- Mengapus setter UUID dalam konstruktor model `Product` dan memindahkan ke dalam method `create` pada `ProductRepository`
- Karena kedua interface terkait hanya dengan satu objek masing-masing (`ProductService` terkait dengan `Product` dan `CarService` terkait dengan `Car`) program mengikuti ISP.
- Mengubah `private CarServiceImpl carService` menjadi `private CarService carService` untuk mengikuti OCP, lalu keduanya bergantuk pada abstract yang mengikuti DIP.

### 2. Explain the advantages of applying SOLID principles to your project with examples.
Keuntungan menerapkan prinsip `SOLID`
- OCP dan DIP: Memudahkan penambahan atau penggantian komponen tanpa perlu mengubah kode yang sudah ada. Ini mengurangi risiko bug dan mempermudah pengujian.
- SRP: Memperjelas struktur kode dengan membagi tanggung jawab, membuat kode lebih mudah dipahami dan diuji. Perubahan pada satu bagian tidak mengganggu bagian lain.
- LSP dan ISP: Memungkinkan fleksibilitas dan penggantian komponen dengan mudah karena setiap bagian kode memiliki tanggung jawab yang jelas dan terbatas.

### 3. Explain the disadvantages of not applying SOLID principles to your project with examples.
Kerugian tidak menerapkan prinsip `SOLID`
- Tanpa OCP dan DIP: Kode menjadi kaku dan sulit diperbarui atau diuji. Perubahan kecil bisa memerlukan banyak penyesuaian di tempat lain.
- Tanpa SRP: Kode menjadi rumit dan sulit dipelihara. Satu perubahan bisa berdampak luas, meningkatkan risiko bug.
- Tanpa LSP dan ISP: Kurangnya fleksibilitas dan ketergantungan yang tinggi antar komponen, membuat sulit untuk menambah atau mengubah fitur tanpa mengubah banyak bagian kode.