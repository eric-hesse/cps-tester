
// Importe für GUI-Komponenten und Dateioperationen
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Hauptklasse für den CPS-Tester, implementiert Maus-Ereignisse
public class CPSTester implements MouseListener {

    int clickCount; // Zählt die Klicks
    Timer timer; // Zeitgeber für Klicks pro Sekunde
    JLabel clickLabel; // Anzeige für Klicks
    JLabel timerLabel; // Anzeige für den Timer
    int remainingTime; // Verbleibende Zeit in Sekunden
    boolean timerExpired; // Gibt an, ob der Timer abgelaufen ist
    boolean timerStarted; // Gibt an, ob der Timer gestartet wurde
    int totalTime; // Gesamtdauer des Timers in Sekunden

    // Konstruktor für den CPS-Tester
    public CPSTester(JLabel clickLabel, JLabel timerLabel, int timerInterval) {
        this.clickCount = 0; // Initialisiert Klickzähler
        this.clickLabel = clickLabel; // Setzt Klick-Anzeige
        this.timerLabel = timerLabel; // Setzt Timer-Anzeige
        this.remainingTime = timerInterval / 1000; // Setzt verbleibende Zeit
        this.totalTime = this.remainingTime; // Setzt Gesamtzeit

        // Timer, der jede Sekunde tickt
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingTime > 0) {
                    remainingTime--; // Reduziert verbleibende Zeit
                    timerLabel.setText("Timer: " + remainingTime + " Sekunden verbleiben"); // Aktualisiert
                                                                                            // Timer-Anzeige
                } else {
                    timer.stop(); // Stoppt Timer bei Ablauf
                    timerExpired = true; // Setzt Timer-Status auf abgelaufen
                    showResultDialog(); // Zeigt Ergebnis-Dialog an
                }
            }
        });
    }

    // Zeigt Ergebnis-Dialog
    void showResultDialog() {
        double cps = (double) clickCount / totalTime; // Berechnet Klicks pro Sekunde
        int option = JOptionPane.showConfirmDialog(null,
                "Zeit abgelaufen! Du hast " + clickCount + " Klick(s) in " + totalTime
                        + " Sekunde(n) gemacht, dies entspricht einer CPS von " + String.format("%.2f", cps)
                        + ". Möchtest du neu starten?",
                "Ergebnis",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) { // Benutzer will neu starten
            int newTimerInterval = getTimerInterval(); // Eingabe neuer Timer-Dauer
            if (newTimerInterval > 0) { // Überprüft auf gültige Eingabe
                resetData(newTimerInterval); // Setzt Daten zurück
            }
        } else {
            System.exit(0); // Beendet Programm
        }
    }

    // Eingabe der Timer-Dauer vom Benutzer
    private int getTimerInterval() {
        while (true) {
            String input = JOptionPane.showInputDialog("Gib die Timer-Dauer in Sekunden ein:");
            if (input == null) { // Benutzer hat abgebrochen
                System.exit(0); // Beendet Programm
            }
            try {
                return Integer.parseInt(input) * 1000; // Konvertiert Eingabe in Millisekunden
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ungültige Eingabe! Verwende eine gültige Zahl."); // Fehlermeldung
            }
        }
    }

    // Wenn Maus geklickt wird
    public void mouseClicked(MouseEvent e) {
        if (timerExpired) { // Wenn Timer abgelaufen ist, nichts tun
            return;
        }
        if (!timerStarted) { // Wenn Timer noch nicht gestartet wurde
            timer.start(); // Startet Timer
            timerStarted = true; // Setzt Timer-Startstatus
        }
        clickCount++; // Zählt Klicks
        clickLabel.setText("Klicks: " + clickCount); // Aktualisiert Klick-Anzeige
    }

    // Hauptmethode zum Starten des Programms
    public static void main(String[] args) {
        int timerInterval = new CPSTester(null, null, 0).getTimerInterval(); // Eingabe Timer-Dauer

        JFrame frame = new JFrame("CPS Tester"); // Erstellt JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beendet Programm beim Schließen
        frame.setSize(280, 280); // Setzt Größe
        frame.setLayout(new BorderLayout()); // Setzt Layout

        JLabel clickLabel = new JLabel("Klicks: 0"); // Klick-Anzeige
        JLabel timerLabel = new JLabel("Timer: " + (timerInterval / 1000) + " Sekunden verbleiben"); // Timer-Anzeige

        JPanel imagePanel = new JPanel() { // Panel für Hintergrundbild
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("path\\to\\the\\Maus.png")); // Lädt Bild
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) { // Zeichnet Bild
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 15, getWidth(), getHeight(), this);
                }
            }
        };
        imagePanel.setLayout(new FlowLayout()); // Setzt Layout für Bildpanel

        imagePanel.add(clickLabel); // Fügt Klick-Anzeige hinzu
        imagePanel.add(timerLabel); // Fügt Timer-Anzeige hinzu

        frame.add(imagePanel, BorderLayout.CENTER); // Fügt Bildpanel zum Frame hinzu

        CPSTester cpsTester = new CPSTester(clickLabel, timerLabel, timerInterval); // Erstellt CPS-Tester Instanz
        frame.addMouseListener(cpsTester); // Fügt MouseListener hinzu

        frame.setVisible(true); // Macht Fenster sichtbar
    }

    // Setzt alle Daten zurück
    private void resetData(int newTimerInterval) {
        this.clickCount = 0; // Setzt Klickzähler zurück
        this.remainingTime = newTimerInterval / 1000; // Setzt verbleibende Zeit zurück
        this.totalTime = this.remainingTime; // Setzt Gesamtzeit zurück
        this.clickLabel.setText("Klicks: 0"); // Aktualisiert Klick-Anzeige
        this.timerLabel.setText("Timer: " + this.remainingTime + " Sekunden verbleiben"); // Aktualisiert Timer-Anzeige
        this.timerExpired = false; // Setzt Timer-Status zurück
        this.timerStarted = false; // Setzt Timer-Startstatus zurück
    }

    // Leere Methoden des MouseListener-Interfaces
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
