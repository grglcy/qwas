package ooo.george.qwas;

import com.badlogic.gdx.Gdx;

public class Log {
    public static void debug(String p_message) {
        Gdx.app.debug("", p_message);
    }
    public static void error(String p_message) {
        Gdx.app.error("", p_message);
    }
    public static void warning(String p_message) {
        Gdx.app.debug("Warning", p_message);
    }
}
