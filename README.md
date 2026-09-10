# PersonalChests

> Her oyuncuya sayfalı, kişisel sandıklar veren hafif bir Spigot/Paper eklentisi.

![Minecraft](https://img.shields.io/badge/Minecraft-1.8--1.21-brightgreen)
![Java](https://img.shields.io/badge/Java-8-orange)
![License](https://img.shields.io/badge/License-MIT-blue)

Oyuncular `/chest` ile kendi sandıklarını açar, ileri/geri butonlarıyla sayfalar arasında
gezinir. Yetkililer başka oyuncuların sandıklarını görüntüleyip düzenleyebilir. Tek jar
**1.8 – 1.21** arası tüm sürümlerde çalışır; sunucu sürümünü çalışma anında algılar.

## ✨ Özellikler

- 🎒 Oyuncu başına ayarlanabilir sandık sayısı (varsayılan **10**)
- 🔢 `/chest <sayfa>` ile istediğin sandığı doğrudan açma
- ⬅️➡️ GUI içinde ileri / geri / kapat butonları
- 🛡️ Yetkililer için `/chest admin <oyuncu>` — başkasının sandığını aç ve düzenle
- ⚙️ Başlıklar, itemler, sesler ve mesajlar tamamen configden ayarlanabilir
- 💾 Veriler oyuncu başına ayrı dosyada saklanır (`data/<uuid>.yml`)

## 📥 Kurulum

1. `PersonalChests.jar` dosyasını indir.
2. Sunucunun `plugins/` klasörüne at.
3. Sunucuyu yeniden başlat.
4. Oluşan `plugins/PersonalChests/config.yml` dosyasını dilediğin gibi düzenle.

## 🎮 Komutlar

| Komut | Açıklama | Yetki |
|-------|----------|-------|
| `/chest` | İlk sandığını açar | `chest.use` |
| `/chest <sayfa>` | Belirtilen sandığı açar | `chest.use` |
| `/chest admin <oyuncu> [sayfa]` | Başka oyuncunun sandığını açar | `chest.admin` |
| `/chest reload` | Configi yeniden yükler | `chest.admin` |

**Alias:** `/sandik` · `/pv` · `/kasa`

## 🔑 Yetkiler

| Yetki | Açıklama | Varsayılan |
|-------|----------|------------|
| `chest.use` | Komutu kullanabilir | herkes |
| `chest.admin` | Başkalarının sandıklarını yönetir, reload | op |

## ⚙️ Ayarlar (config.yml)

| Anahtar | Açıklama |
|---------|----------|
| `total-chests` | Oyuncu başına sandık sayısı |
| `rows` | GUI satır sayısı (2–6, son satır gezinme çubuğu) |
| `titles.player` / `titles.admin` | Başlık formatları (`%page%`, `%owner%`) |
| `sound.*` | Açılış ve sayfa geçiş sesleri |
| `items.*` | Buton materyalleri, isimleri, açıklamaları |
| `messages.*` | Tüm mesajlar |

## 🛠️ Derleme

```bash
mvn clean package
```

Çıktı: `target/PersonalChests.jar`

> 1.8 uyumluluğu için Java 8 hedefiyle derlenir. En sorunsuz derleme **JDK 8, 11 veya 17** iledir.

## 📄 Lisans

MIT — dilediğin gibi kullan, değiştir, dağıt.
