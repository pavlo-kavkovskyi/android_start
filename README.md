
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
## Status
- [x] układ ekranu głównego (`activity_main.xml`)
- [x] obsługa kliknięć przycisku i licznik w `MainActivity`
- [x] wyświetlanie komunikatu `Toast`
- [ ] przycisk resetowania licznika
- [ ] zachowanie stanu licznika po obrocie ekranu

## Zadanie 2
<img width="387" height="853" alt="zad2AS" src="https://github.com/user-attachments/assets/d359ba26-7f37-468c-851b-62dc93d92b94" />

## Autor
Pavlo Kavkovskyi, 4A, 2026/2027
