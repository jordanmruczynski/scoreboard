package pl.jordii.scoreboard.sidebar.component;

import java.util.Map;

public abstract class SidebarComponent {

    /**
     * Renders all the information provided in the component
     * to a map containing the final information to be rendered
     * to the sidebar (the lines where the text should be placed and
     * the text to write there).
     *
     * @return A map, where the key is the absolute line where
     *         the component should be placed in the sidebar and
     *         the value is the actual text for that line.
     */
    public abstract Map<Integer, String> render();

}
