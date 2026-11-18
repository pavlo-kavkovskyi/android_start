
# android-start
To jest pierwsza aplikacja zrobiona przez Android Studio. Jest dla mnie, dla polaków, dla pana Beyniarskiego.
## Technologie
- Java 11, Android SDK (compileSdk 36, minSdk 26)
- AndroidX (AppCompat, Activity, ConstraintLayout), Material Components 3
- Gradle 8.13 (Kotlin DSL)
- XML (układy widoków i zasoby)
## Uruchomienie
```text
Otwórz folder android_start-main w Android Studio i poczekaj na synchronizację projektu Gradle.

Uruchom aplikację na emulatorze (AVD) lub fizycznym urządzeniu z systemem Android (Shift + F10) albo zbuduj i zainstaluj ją z terminala:

.\gradlew.bat assembleDebug
.\gradlew.bat installDebug


Aplikacja uruchamia się na urządzeniu/emulatorze jako HelloApp (com.kavkovskyi.helloapp).
Aplikacja: http://localhost:5000
```
## Struktura
```text
app/src/main/java/com/kavkovskyi/helloapp/MainActivity.java – główna aktywność aplikacji
app/src/main/res/layout/activity_main.xml – układ graficzny głównego ekranu (XML)
app/src/main/res/values/ – zasoby tekstowe, kolory oraz motywy (strings.xml, colors.xml, themes.xml)
app/src/main/res/values-night/ – motyw aplikacji dla trybu ciemnego
app/src/main/AndroidManifest.xml – konfiguracja i manifest aplikacji Android
app/src/test/ – lokalne testy jednostkowe (ExampleUnitTest.java)
app/src/androidTest/ – testy instrumentalne na urządzeniu (ExampleInstrumentedTest.java)
build.gradle.kts, settings.gradle.kts – konfiguracja budowania Gradle
gradle/libs.versions.toml – zarządzanie wersjami bibliotek i wtyczek
```
## Trasy
| Metoda | Adres | Opis |
|----------|-----------------------|-----------|
| INTENT (MAIN) | .MainActivity | ekran główny z powitaniem |
| ONCLICK | @+id/button_click | zwiększenie licznika i komunikat Toast |
| INTENT (MAIN) | .CatalogActivity | katalog widgetów (zadanie 2) |
| INTENT (MAIN) | .BmiActivity | kalkulator BMI (zadania 4, 5, 7) |
| INTENT (MAIN) | .OrderActivity | formularz zamówienia (zadanie 6) |
## Status
- [x] układ ekranu głównego (`activity_main.xml`)
- [x] obsługa kliknięć przycisku i licznik w `MainActivity`
- [x] wyświetlanie komunikatu `Toast`
- [x] przycisk resetowania licznika
- [x] zachowanie stanu licznika po obrocie ekranu (`onSaveInstanceState`)

## Zadanie 2
<img width="387" height="853" alt="zad2AS" src="https://github.com/user-attachments/assets/d359ba26-7f37-468c-851b-62dc93d92b94" />

## Zadanie 4
<img width="315" height="700" alt="englishAS" src="https://github.com/user-attachments/assets/fbe4a704-3250-4e63-84ce-3dd0e2c2677f" />

## Mapa plików

| Plik | Za co odpowiada | Odpowiednik w Django |
|---|---|---|
| `MainActivity.java` | klasa ekranu: wczytuje layout, obsługuje kliknięcia | `views.py` |
| `res/layout/activity_main.xml` | wygląd ekranu: co, gdzie i jakiego rozmiaru | `templates/index.html` |
| `AndroidManifest.xml` | nazwa, ikona, lista ekranów, uprawnienia, ekran startowy | `settings.py` + `urls.py` |
| `res/values/strings.xml` | wszystkie teksty aplikacji (tłumaczenia w `values-en/`) | brak (najbliżej: pliki tłumaczeń `locale/`) |
| `app/build.gradle.kts` | zależności, `minSdk`, `targetSdk`, wersja aplikacji | `requirements.txt` |
| `res/` | zasoby: obrazki, kolory, layouty (każdy dostaje id w klasie `R`) | `static/` |
| `res/mipmap-*/ic_launcher` | ikona aplikacji w różnych rozmiarach | `static/` (favicon) |
| `.gitignore` | pliki pomijane przez Git (`build/`, `local.properties`) | `.gitignore` |

## Logcat OnCreate
<img width="1918" height="992" alt="logcatAS" src="https://github.com/user-attachments/assets/3804b30b-d12f-4ec6-84db-adb57e7f8688" />
Celowo przestawiłem `findViewById` przed `setContentView`. Aplikacja zamknęła się przy starcie.

## Stacktrace 
<img width="1634" height="584" alt="stacktrace" src="https://github.com/user-attachments/assets/6d8bfd4b-63e3-4f4e-9e10-7647eb41afff" />

Zawiniła linia: _wpisz numer linii i kod, np. `MainActivity.java:42` – `buttonClick.setOnClickListener(...)`, bo `buttonClick` było `null`_.

## Obrót ekranu

Po obrocie telefonu licznik wraca do zera, bo system niszczy Activity i tworzy je od nowa (`onCreate` wykonuje się drugi raz, więc `clickCount = 0`); pełne wyjaśnienie w M02.

## M02 – widgety, zdarzenia, cykl życia

Każdy nowy ekran ma w manifeście własny `intent-filter` MAIN/LAUNCHER, więc na liście aplikacji w telefonie pojawiają się ikony **HelloApp**, **Katalog**, **BMI** i **Zamówienie**. W Android Studio uruchamiasz wybrany ekran przez *Run → Edit Configurations → Launch Options → Specified Activity*. (Przechodzenie między ekranami z kodu nie jest jeszcze w M02.)

### Zadanie 1 – LinearLayout
`activity_main.xml` jest teraz pionowym `LinearLayout`. Przyciski „Kliknij mnie” i „Reset” są w poziomym `LinearLayout` z `layout_width="0dp"` i `layout_weight="1"` – każdy zajmuje połowę szerokości. Po zmianie zewnętrznego `orientation` na `horizontal` wszystkie elementy układają się w jednym wierszu i wylatują poza ekran (zachowanie opisane w M02, rozdz. 9).

### Zadanie 2 – katalog widgetów (`CatalogActivity`)
`TextView`, `EditText`, `Button`, `CheckBox`, `Switch`, `RadioGroup` z trzema `RadioButton`, `SeekBar` (etykieta głośności zmienia się na żywo) i `ImageView` z własnym wektorem `drawable/ic_catalog.xml`. Przycisk „Zbierz stan” wypisuje stan wszystkich widgetów w `TextView`, np. `Imię: Ania, zgoda: tak, powiadomienia: nie, rozmiar: M, głośność: 70`.

### Zadanie 3 – cykl życia
`MainActivity` nadpisuje `onCreate`, `onStart`, `onRestart`, `onResume`, `onPause`, `onStop`, `onDestroy` (oraz `onSaveInstanceState`) z logiem `Log.d("MainActivity", ...)`. Tabela wg schematu z M02 – **sprawdź ją w Logcat** (filtr `tag:MainActivity`) i popraw, jeśli Twój telefon pokaże coś innego:

| Akcja | Kolejność wywołań |
|---|---|
| Uruchomienie aplikacji | `onCreate` → `onStart` → `onResume` |
| Home | `onPause` → `onSaveInstanceState` → `onStop` |
| Powrót z Home | `onRestart` → `onStart` → `onResume` |
| Przełączenie na inną aplikację | `onPause` → `onSaveInstanceState` → `onStop` |
| Powrót do aplikacji | `onRestart` → `onStart` → `onResume` |
| Zablokowanie ekranu | `onPause` → `onSaveInstanceState` → `onStop` |
| Odblokowanie ekranu | `onRestart` → `onStart` → `onResume` |
| Obrót ekranu | `onPause` → `onSaveInstanceState` → `onStop` → `onDestroy` → `onCreate` → `onStart` → `onResume` |
| Coś przysłoniło ekran (dialog, telefon) | `onPause` → (po powrocie) `onResume` |

### Zadania 4, 5, 7 – kalkulator BMI (`BmiActivity`)
- walidacja wszystkich pól naraz (`setError`), zakresy: 20–300 kg / 50–250 cm albo 45–660 lb / 20–98 in,
- obliczenie BMI i kategoria (`strings.xml`, `getString` z formatem),
- wynik przetrwa obrót (`onSaveInstanceState`), `EditText` i przełącznik jednostek zapisują się same dzięki `android:id`,
- zadanie 5: `TextWatcher` na polu wagi – błąd pojawia się od razu po wyjściu poza zakres i znika po powrocie do zakresu,
- zadanie 7: `RadioGroup` „kg / cm” vs „lb / in” zamiast `CheckBox`; zmiana wyboru zmienia `hint` obu pól (`setHint`).

_(wstaw tu zrzut ekranu z błędem walidacji – zrobisz go na emulatorze)_

### Zadanie 6 – formularz zamówienia (`OrderActivity`, domena: 4A Sklep)
Sześć pól różnych typów: imię i nazwisko (`text`), e-mail (`textEmailAddress`), ilość (`number`, 1–20), produkt (`Spinner`, pozycja 0 to podpowiedź), dostawa (`RadioGroup`), regulamin (`CheckBox`). Każde jest walidowane, „Zapisz” pokazuje `Toast` z podsumowaniem zamówienia, „Wyczyść” resetuje formularz. Stan po obrocie wraca sam (wszystkie widgety mają `id`). Teksty (także tablica produktów) są w `strings.xml` i `values-en/strings.xml`.

### Zadanie 8 – historia commitów
Commity z kolejnych zadań: `feat: add BMI calculator layout`, `feat: add validation and calculation`, `feat: preserve result on rotation` oraz pozostałe (`git log --oneline`).

## Autor
Pavlo Kavkovskyi, 4A, 2026/2027
