package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USER_GUIDE_URL = "https://ay2526s2-cs2103t-t12-4.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "For more details, refer to the ";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private static final String USER_GUIDE_LOCAL_PATH = "/docs/UserGuide.md";
    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Label helpMessage;

    @FXML
    private TextArea commandList;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Returns the Markdown string of the command summary from the user guide.
     */
    private String getCommandSummary() {
        String markdown;
        markdown = loadUserGuide();

        String start = "## Command summary";
        int startIndex = markdown.indexOf(start);

        if (startIndex == -1) {
            logger.info("Could not find command summary in user guide.");
            return "Command summary section not found in User Guide.";
        }

        return markdown.substring(startIndex);
    }

    /**
     * Loads the user guide and returns its contents.
     * During development, it reads from the external user guide file.
     * During production (in JAR file), it loads the file from resources.
     */
    private String loadUserGuide() {
        Path external = Path.of(USER_GUIDE_LOCAL_PATH);
        if (Files.exists(external)) {
            try {
                return Files.readString(Path.of(USER_GUIDE_LOCAL_PATH));
            } catch (IOException e) {
                logger.severe("Something went wrong while loading and reading the User Guide.\n" + e);
            }
        }

        try (InputStream is = getClass().getResourceAsStream(USER_GUIDE_LOCAL_PATH)) {
            if (is == null) {
                throw new IOException("Resource not found: " + USER_GUIDE_LOCAL_PATH);
            }
            return new String(is.readAllBytes());
        } catch (IOException e) {
            logger.severe("Something went wrong while loading and reading the User Guide from resources.\n" + e);
        }

        return "";
    }

    /**
     * Open the link to the user guide.
     */
    @FXML
    private void openUserGuide() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(java.net.URI.create(USER_GUIDE_URL));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
