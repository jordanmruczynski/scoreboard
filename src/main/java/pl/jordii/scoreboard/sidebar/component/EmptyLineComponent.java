package pl.jordii.scoreboard.sidebar.component;

import com.google.common.collect.Maps;

import java.util.Map;

public class EmptyLineComponent extends SidebarComponent {

    // the line to place the component in the sidebar
    private int line;

    public static EmptyLineComponent create() {
        return new EmptyLineComponent();
    }

    /**
     * Sets the line number of the component to be placed in the sidebar.
     * Please note that this line id represents an absolute position and
     * should therefore be unique. No components should have the same line
     * number.
     *
     * @param line The line of the sidebar, where the component should
     *             be visible.
     * @return The current component instance for fluent builder design.
     */
    public EmptyLineComponent line(int line) {
        this.line = line;
        return this;
    }

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
    @Override
    public Map<Integer, String> render() {
        Map<Integer, String> output = Maps.newHashMap();
        output.put(line, " ");
        return output;
    }

}
