/*
 * VCellId_v1App.java
 */

package vcellid_v1;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class VCellId_v1App extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new VCellId_v1View(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of VCellId_v1App
     */
    public static VCellId_v1App getApplication() {
        return Application.getInstance(VCellId_v1App.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(VCellId_v1App.class, args);
    }
}
