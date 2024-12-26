# **Apartman Yönetim Sistemi**

## **Proje Tanımı**
Apartman Yönetim Sistemi, apartman sakinleri ve yöneticiler için tasarlanmış bir uygulamadır. Bu sistem, aidat yönetimi, kullanıcı takibi ve bildirim gönderimi gibi işlemleri etkili bir şekilde yönetmek amacıyla geliştirilmiştir. Kod, **Singleton**, **Factory**, **State**, **Observer**, **Decorator** ve **Composite** tasarım desenleri ile yapılandırılmıştır ve bu da sistemin genişletilebilirliğini ve bakımını kolaylaştırır.

---

## **Özellikler**
- **Kullanıcı Yönetimi**:
  - Yönetici ve daire sakini rolleriyle özelleştirilmiş yetkilendirme.
  - Kullanıcıların eklenmesi, güncellenmesi ve silinmesi.
- **Aidat Yönetimi**:
  - Aidatların eklenmesi, düzenlenmesi ve durum takibi.
  - Ek hizmet ücretlerinin (güvenlik, temizlik vb.) dahil edilmesi.
- **Bildirim Sistemi**:
  - Kullanıcılara aidat hatırlatmaları ve genel bildirimler gönderme.
- **Grafik Kullanıcı Arayüzü**:
  - Java Swing ile geliştirilmiş kullanıcı dostu paneller.
- **Ölçeklenebilirlik ve Modülerlik**:
  - Tasarım desenleri kullanılarak kodun genişletilebilirliği artırılmıştır.

---

## **Teknoloji Yığını**
- **Programlama Dili**: Java  
- **Veritabanı**: Microsoft SQL Server  
- **Bağlantı Teknolojisi**: JDBC  
- **Arayüz**: Java Swing  

---

## **Tasarım Desenleri**

### 1. **Singleton Design Pattern**
- **Kullanım Alanı**: Veritabanı bağlantısını yöneten `VeritabaniBaglantisi` sınıfında kullanılmıştır.
- **Amaç**: Veritabanına tek bir bağlantı nesnesi üzerinden erişim sağlamak ve kaynak tüketimini azaltmak.

### 2. **Factory Design Pattern**
- **Kullanım Alanı**:`EntityFactory` sınıfı, farklı tipte kullanıcıları (`DaireSakini`, `YoneticiKullanici`) ve apartman birimlerini (`Daire`, `Dukkan`) oluşturmak için kullanılmıştır.
- **Amaç**: Nesne oluşturmayı soyutlayarak, esnek ve yeniden kullanılabilir bir yapı sunmak.

### 3. **State Design Pattern**
- **Kullanım Alanı**: Kullanıcıların durumlarını (`AktifDurum`, `PasifDurum`, `BorcluDurum`) yönetmek için kullanılmıştır.
- **Amaç**: Kullanıcı durumlarına göre sistem davranışını dinamik olarak değiştirmek.

### 4. **Observer Design Pattern**
- **Kullanım Alanı**:`Subject` ve `Observer` arayüzleri, bildirim mekanizmasında uygulanmıştır.`AidatHatirlatici` sınıfı, kullanıcıların durum değişikliklerini gözlemler ve bildirim gönderir.
- **Amaç**: Nesneler arasında gevşek bağlam sağlamak ve durum değişikliklerini ilgili nesnelere otomatik olarak iletmek.

### 5. **Decorator Design Pattern**
- **Kullanım Alanı**: Aidat hesaplamalarını genişletmek için kullanılmıştır (`GuvenlikHizmetiDecorator`, `TemizlikHizmetiDecorator`).
- **Amaç**: Ekstra özellikler (güvenlik, temizlik ücretleri) eklenerek aidat hesaplamasının dinamik olarak değiştirilmesi.

### 6. **Composite Design Pattern**
- **Kullanım Alanı**:`Apartman` ve `ApartmanBirim` sınıfları, apartman ve birimlerinin (daire, dükkan) hiyerarşik bir yapı içinde yönetilmesini sağlar.
- **Amaç**: Karmaşık bir nesne yapısını (apartman birimleri) ağaç benzeri bir yapıda temsil etmek.

---

   ## Ekran Görüntüleri

- **Üye Ol Ekranı:** Kullanıcıların sisteme üye olmasını sağlar.
  
  ![image](https://github.com/user-attachments/assets/51446147-a840-440c-ae70-47e92d69e85d)
  

- **Giriş Ekranı:**  Kullanıcıların sisteme giriş yapmasını sağlar.
  
  ![image](https://github.com/user-attachments/assets/f1cb1eb7-f1bb-45ad-8d71-6018cd5e78b8)
  

- **Admin Paneli:**  Kullanıcı ve aidat yönetimi yapılır.
  ![image](https://github.com/user-attachments/assets/bb1e57f3-e754-4757-8b49-f524af981e40)
  ![image](https://github.com/user-attachments/assets/031acf3d-7f4b-4b27-8a77-383064913b33)
  ![image](https://github.com/user-attachments/assets/42d7d093-fe28-4df3-adf8-5cd320ff764b)
  ![image](https://github.com/user-attachments/assets/624f7904-c26b-4105-9108-b9ffb1f12939)
  ![image](https://github.com/user-attachments/assets/9a44f373-d93a-458c-9cb4-760ded48d9f5)


- **Resident Paneli:**  Daire sakinlerinin aidat ödeme ve bildirim görüntüleme işlemleri yapılır.
  
  ![image](https://github.com/user-attachments/assets/1063365f-4c8a-4b4d-a73d-124855ccba8e)
  ![image](https://github.com/user-attachments/assets/e3ec8a3e-8624-457e-8150-e2c99e401c22)
  ![image](https://github.com/user-attachments/assets/16efd731-1b7f-4822-a447-601694829568)
  ![image](https://github.com/user-attachments/assets/99f32e2e-7572-4e95-b67d-5a5a3ae6a804)


## Yazarlar

- [AYÇA ALBAYRAK](https://github.com/aycaalbayrak)


## Teşekkürler

Projemi incelediğiniz için teşekkür ederim!



  


