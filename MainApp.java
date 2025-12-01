package studentinfosyscrudapp;

import javax.swing.UIManager;
/*
Java Swing has several built-in UI themes (called Look and Feel).
Examples:
Metal (default), Nimbus, Motif, Windows, GTK

UIManager.getInstalledLookAndFeels() - Returns all themes that Java supports on your computer.
The app uses a beautiful UI theme instead of the old gray Swing look.

Always create Swing UI components on the Event Dispatch Thread (EDT).
This is the thread where GUI events happen. If you don't, your UI could freeze or behave incorrectly.
*/
public class MainApp {

    public static void main(String[] args) {
        //use Nimbus look & feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // ignore, use default
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.setVisible(true);
        });
    }
}
