<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=soft&color=0:FCAF01,50:FE2C6B,100:FC05AC&height=160&section=header&text=PersonalChests&fontSize=64&fontColor=FFFFFF&fontAlignY=42&desc=Spigot%20%2F%20Paper%20%E2%80%A2%201.8%20%E2%86%92%201.21&descSize=16&descAlignY=72&animation=fadeIn" alt="PersonalChests" width="100%" />
</p>

<p align="center">
  <a href="https://github.com/coslant/PersonalChest/releases/latest"><img src="https://img.shields.io/github/v/release/coslant/PersonalChest?style=for-the-badge&label=%C4%B0ndir&color=FE2C6B&logo=github" alt="Download" /></a>
  <a href="https://discord.gg/forges"><img src="https://img.shields.io/badge/Discord-Forges%20Studio-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Discord: Forges Studio" /></a>
</p>

<p align="center">
  <a href="https://github.com/coslant/PersonalChest/actions/workflows/build.yml"><img src="https://img.shields.io/github/actions/workflow/status/coslant/PersonalChest/build.yml?branch=main&style=flat-square&label=build" alt="Build" /></a>
  <img src="https://img.shields.io/badge/Minecraft-1.8%20%E2%86%92%201.21-FCAF01?style=flat-square" alt="Minecraft 1.8 - 1.21" />
  <img src="https://img.shields.io/badge/Spigot%20%7C%20Paper-FE9808?style=flat-square" alt="Spigot | Paper" />
  <img src="https://img.shields.io/badge/Java-8-FD6C29?style=flat-square&logo=openjdk&logoColor=white" alt="Java 8" />
  <a href="LICENSE"><img src="https://img.shields.io/github/license/coslant/PersonalChest?style=flat-square&color=FB0C9E&label=lisans" alt="License" /></a>
</p>

<p align="center">
  <b>Her oyuncuya sayfalı, kişisel sandıklar veren hafif bir Spigot/Paper eklentisi.</b>
  <br>
  <sub><kbd>EN</kbd> Lightweight plugin that gives every player paged, personal chests.</sub>
</p>

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

1. `PersonalChests.jar` dosyasını [Releases](https://github.com/coslant/PersonalChest/releases/latest) sayfasından indir.
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

Her push'ta [GitHub Actions](https://github.com/coslant/PersonalChest/actions/workflows/build.yml) projeyi otomatik derler; etiketlenen sürümler (`v*`) jar'ıyla birlikte [Releases](https://github.com/coslant/PersonalChest/releases) sayfasında yayınlanır.

## 💬 Destek

- 🐛 Hata bildirimi ve öneriler için [Issues](https://github.com/coslant/PersonalChest/issues) sekmesini kullan.
- 💬 Sorular ve destek için [Forges Studio Discord](https://discord.gg/forges) sunucusuna katıl.

## 📄 Lisans

[MIT](LICENSE) — dilediğin gibi kullan, değiştir, dağıt.

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=rect&color=0:FCAF01,50:FE2C6B,100:FC05AC&height=4&section=footer" width="100%" alt="" />
  <br>
  <sub>🔥 <a href="https://github.com/coslant">coslant</a> tarafından geliştirildi · <a href="https://discord.gg/forges">Forges Studio</a></sub>
</p>
