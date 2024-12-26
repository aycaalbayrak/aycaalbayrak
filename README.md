# **Apartman Yönetim Sistemi**

## **Proje Tanımı**
Apartman Yönetim Sistemi, apartman sakinleri ve yöneticiler için tasarlanmış bir uygulamadır. Bu sistem, aidat yönetimi, kullanıcı takibi ve bildirim gönderimi gibi işlemleri etkili bir şekilde yönetmek amacıyla geliştirilmiştir. Kod, **Singleton**, **Factory**, **State**, **Observer**, **Decorator**, ve **Composite** tasarım desenleri ile yapılandırılmıştır ve bu da sistemin genişletilebilirliğini ve bakımını kolaylaştırır.

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

### 1. **Singleton**
- **Kullanım Alanı**: Veritabanı bağlantısını yöneten `VeritabaniBaglantisi` sınıfında kullanılmıştır.
- **Amaç**: Veritabanına tek bir bağlantı nesnesi üzerinden erişim sağlamak ve kaynak tüketimini azaltmak.

### 2. **Factory**
- **Kullanım Alanı**:`EntityFactory` sınıfı, farklı tipte kullanıcıları (`DaireSakini`, `YoneticiKullanici`) ve apartman birimlerini (`Daire`, `Dukkan`) oluşturmak için kullanılmıştır.
- **Amaç**: Nesne oluşturmayı soyutlayarak, esnek ve yeniden kullanılabilir bir yapı sunmak.

### 3. **State**
- **Kullanım Alanı**: Kullanıcıların durumlarını (`AktifDurum`, `PasifDurum`, `BorcluDurum`) yönetmek için kullanılmıştır.
- **Amaç**: Kullanıcı durumlarına göre sistem davranışını dinamik olarak değiştirmek.

### 4. **Observer**
- **Kullanım Alanı**:`Subject` ve `Observer` arayüzleri, bildirim mekanizmasında uygulanmıştır.`AidatHatirlatici` sınıfı, kullanıcıların durum değişikliklerini gözlemler ve bildirim gönderir.
- **Amaç**: Nesneler arasında gevşek bağlam sağlamak ve durum değişikliklerini ilgili nesnelere otomatik olarak iletmek.

### 5. **Decorator**
- **Kullanım Alanı**: Aidat hesaplamalarını genişletmek için kullanılmıştır (`GuvenlikHizmetiDecorator`, `TemizlikHizmetiDecorator`).
- **Amaç**: Ekstra özellikler (güvenlik, temizlik ücretleri) eklenerek aidat hesaplamasının dinamik olarak değiştirilmesi.

### 6. **Composite**
- **Kullanım Alanı**:
  - `Apartman` ve `ApartmanBirim` sınıfları, apartman ve birimlerinin (daire, dükkan) hiyerarşik bir yapı içinde yönetilmesini sağlar.
- **Amaç**: Karmaşık bir nesne yapısını (apartman birimleri) ağaç benzeri bir yapıda temsil etmek.

---

   ## Ekran Görüntüleri

- **Üye Ol Ekranı:** Kullanıcıların sisteme üye olmasını sağlar.
  
  ![image](https://github.com/user-attachments/assets/51446147-a840-440c-ae70-47e92d69e85d)

- **Giriş Ekranı:**  Kullanıcıların sisteme giriş yapmasını sağlar.
  
  ![image](https://github.com/user-attachments/assets/f1cb1eb7-f1bb-45ad-8d71-6018cd5e78b8)

- **Admin Paneli:**  Kullanıcı ve aidat yönetimi yapılır.
  
  ![image](https://github.com/user-attachments/assets/def40355-eac4-418d-b68a-1ff4fac67e37)
  ![image](https://github.com/user-attachments/assets/cbeb62ee-0fbc-40a4-8a3a-581d6d7df510)
  ![image](https://github.com/user-attachments/assets/5f915a8b-0194-4be4-8ee6-d53c5001b178)
  ![image](https://github.com/user-attachments/assets/529ccf21-08f3-4ec5-8584-3af44e677377)
  ![image](https://github.com/user-attachments/assets/51352888-8dd7-4ae7-83e6-989c0ae304a8)
  
- **Resident Paneli:**  Daire sakinlerinin aidat ödeme ve bildirim görüntüleme işlemleri yapılır.
  
  ![image](https://github.com/user-attachments/assets/1063365f-4c8a-4b4d-a73d-124855ccba8e)
  ![image](https://github.com/user-attachments/assets/e3ec8a3e-8624-457e-8150-e2c99e401c22)
  ![image](https://github.com/user-attachments/assets/16efd731-1b7f-4822-a447-601694829568)
  ![image](https://github.com/user-attachments/assets/99f32e2e-7572-4e95-b67d-5a5a3ae6a804)



  


