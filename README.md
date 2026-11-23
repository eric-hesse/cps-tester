# 🖱️ CPS Tester

A small **clicks-per-second tester** written in Java (Swing) – my computer science project in grade 12 (June 2024).

*Ein kleiner Klicks-pro-Sekunde-Tester in Java (Swing) – mein Informatik-Projekt in der 12. Klasse (Juni 2024).*

## What it does

1. Asks how long the test should run (in seconds).
2. Counts every click on the mouse image while a countdown runs.
3. When the time is up, it shows your clicks and your **CPS** (clicks per second) and offers a restart with a new duration.

## Run it

```bash
javac CPSTester.java
java CPSTester
```

> **Note:** the mouse image is loaded from an absolute path. Put the full path of `Maus.png` into the code (around line 110), otherwise the window opens without the picture. See [`ReadMe.txt`](ReadMe.txt).

## Files

| File | |
|---|---|
| `CPSTester.java` | the whole program: GUI, click counter, timer, result dialog |
| `Maus.png` | background image of the click area |
| `docs/Arbeitsprotokoll.pdf` | my handwritten work log (German), 23 May – 20 June 2024 |

## How it was built

According to the work log: started by analysing and commenting example code, set up the Java SDK, then built my own version step by step – click counter and timer first, later a countdown, a background image and the restart button.
