package deneme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Fader {
    //  background color when component has focus
    private Color fadeColor;

    //  steps to fade from original background to fade background
    private int steps;

    //  apply transition colors at this time interval
    private int interval;

    //  store transition colors from orginal background to fade background
    private Hashtable backgroundColors = new Hashtable();

    /*
     *  Fade from a background color to the specified color using
     *  the default of 10 steps at a 50 millisecond interval.
     *
     *  @param fadeColor the temporary background color
     */
    public Fader(Color fadeColor) {
        this(fadeColor, 10, 50);
    }

    /*
     *  Fade from a background color to the specified color in the
     *  specified number of steps at the default 5 millisecond interval.
     *
     *  @param fadeColor the temporary background color
     *  @param steps     the number of steps to fade in the color
     */
    public Fader(Color fadeColor, int steps) {
        this(fadeColor, steps, 50);
    }

    /*
     *  Fade from a background color to the specified color in the
     *  specified number of steps at the specified time interval.
     *
     *  @param fadeColor the temporary background color
     *  @param steps     the number of steps to fade in the color
     *  @param intevral  the interval to apply color fading
     */
    public Fader(Color fadeColor, int steps, int interval) {
        this.fadeColor = fadeColor;
        this.steps = steps;
        this.interval = interval;
    }

    /*
     *  Add a component to this fader.
     *
     *  The fade color will be applied when the component gains focus.
     *  The background color will be restored when the component loses focus.
     *
     *  @param component apply fading to this component
     */
    public Fader add(JComponent component) {
        //  Get colors to be used for fading

        ArrayList colors = getColors(component.getBackground());

        //  FaderTimer will apply colors to the component

        new FaderTimer(colors, component, interval);

        return this;
    }

    /*
     **  Get the colors used to fade this background
     */
    private ArrayList getColors(Color background) {
        //  Check if the color ArrayList already exists

        Object o = backgroundColors.get(background);

        if (o != null) {
            return (ArrayList) o;
        }

        //  Doesn't exist, create fader colors for this background

        ArrayList colors = new ArrayList(steps + 1);
        colors.add(background);

        int rDelta = (background.getRed() - fadeColor.getRed()) / steps;
        int gDelta = (background.getGreen() - fadeColor.getGreen()) / steps;
        int bDelta = (background.getBlue() - fadeColor.getBlue()) / steps;

        for (int i = 1; i < steps; i++) {
            int rValue = background.getRed() - (i * rDelta);
            int gValue = background.getGreen() - (i * gDelta);
            int bValue = background.getBlue() - (i * bDelta);

            colors.add(new Color(rValue, gValue, bValue));
        }

        colors.add(fadeColor);
        backgroundColors.put(background, colors);

        return colors;
    }

    class FaderTimer implements FocusListener, ActionListener, MouseListener {
        private ArrayList colors;
        private JComponent component;
        private Timer timer;
        private int alpha;
        private int increment;

        FaderTimer(ArrayList colors, JComponent component, int interval) {
            this.colors = colors;
            this.component = component;
            component.addMouseListener(this);
            timer = new Timer(interval, this);
        }

        public void focusGained(FocusEvent e) {
            alpha = 0;
            increment = 1;
            timer.start();
        }

        public void focusLost(FocusEvent e) {
            alpha = steps;
            increment = -1;
            timer.start();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {
            alpha = 0;
            increment = 1;
            timer.start();
        }

        public void mouseExited(MouseEvent e) {
            alpha = steps;
            increment = -1;
            timer.start();
        }

        public void actionPerformed(ActionEvent e) {
            alpha += increment;

            component.setBackground((Color) colors.get(alpha));

            if (alpha == steps || alpha == 0)
                timer.stop();
        }
    }
}