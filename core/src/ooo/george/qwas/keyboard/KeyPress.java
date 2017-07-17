package ooo.george.qwas.keyboard;

public class KeyPress {
    private boolean m_pressed;
    private double m_time;

    public KeyPress() {
        m_pressed = true;
        m_time = 0;
    }

    public void reset() {
        m_pressed = false;
        m_time = 0;
    }

    public boolean isPressed() {
        return m_pressed;
    }

    public double getTime() {
        return m_time;
    }

    public void increment(double p_delta) {
        m_time += p_delta;
    }

    public void press() {
        m_pressed = true;
    }
}
