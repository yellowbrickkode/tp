package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    private static final String CONTENT_STYLE_PATH = "/view/HelpWindowContent.css";

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Label helpMessage;

    @FXML
    private WebView commandList;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        helpMessage.setText(HELP_MESSAGE);

        String commandSummaryMarkdown = getCommandSummary();
        String commandSummaryHtml = markdownToHtml(commandSummaryMarkdown);

        WebEngine webEngine = commandList.getEngine();
        webEngine.loadContent(commandSummaryHtml);
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
     * Turns the given Markdown string to HTML.
     */
    private String markdownToHtml(String markdown) {
        List<Extension> extensions = List.of(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
        Node document = parser.parse(markdown);
        String body = renderer.render(document);

        String cssUrl = Objects.requireNonNull(getClass()
                        .getResource(CONTENT_STYLE_PATH))
                        .toExternalForm();

        return """
        <html>
        <head>
            <link rel="stylesheet" href="
            """ + cssUrl + """
        ">
        </head>
        <body>
            """ + body + """
        </body>
        </html>
            """;
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
            return null;
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
