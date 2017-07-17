package ooo.george.qwas.keyboard.layout;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Log;
import ooo.george.qwas.keyboard.Combination;
import ooo.george.qwas.keyboard.KeyPress;
import ooo.george.qwas.keyboard.key.Key;
import ooo.george.qwas.keyboard.layout.json.layout;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class JSONLayout implements Layout{
    private Key m_keys[][];
    private final List<Combination> m_combinations;
    private final String m_name;

    public JSONLayout(String p_fileContents, String p_name) {
        m_combinations = new ArrayList<>();
        m_name = p_name;
        boolean m_valid = buildLayout(p_fileContents);
    }

    private boolean buildLayout(String p_fileContents) {
        if (p_fileContents == null || p_fileContents.equals("")) {
            return false;
        }

        Json json = new Json();

        layout layout = json.fromJson(ooo.george.qwas.keyboard.layout.json.layout.class, p_fileContents);

        if (!layout.valid()) {
            Log.warning("Invalid layout: " + m_name);
            return false;
        }

        this.m_keys = new Key[layout.lineCount()][layout.maxCharacters()];

        constructArray(layout);

        String[] combinationNames = layout.getCombinations();

        for (String s: combinationNames) {
            if (Assets.combinations.containsKey(s))
                addCombination(Assets.combinations.get(s));
        }

        return true;
    }

    private void constructArray(layout p_layout) {
        for (int iii = 0; iii < p_layout.lineCount(); iii++) {
            for (int jjj = 0; p_layout.getKey(jjj, iii) != null; jjj++) {
                m_keys[iii][jjj] = p_layout.getKey(jjj, iii);
            }
        }
    }

    public boolean addCombination(Combination p_combination) {
        return !m_combinations.contains(p_combination) && m_combinations.add(p_combination);
    }

    private char stringToChar(String p_value) throws Exception {
        if (p_value.length() == 1) {
            return p_value.toCharArray()[0];
        } else if (p_value.toLowerCase().equals("comma")) {
            return ',';
        } else {
            throw new Exception("Unrecognised string in layout file.");
        }
    }

    public char getKey(int x, int y, Hashtable<Integer, KeyPress> p_keys) {
        //KeyPress pp = new KeyPress();
        //pp.press();
        //p_keys.put(Input.Keys.SHIFT_LEFT, pp);
        if (outOfBounds(x, y)) {
            return '\0';
        } else {
            Key key = m_keys[x][y];

            Combination currentCombination = checkCombinations(p_keys);

            if (currentCombination != null) {
                return key.getKey(currentCombination);
            } else {
                return key.getKey();
            }
        }
    }

    private Combination checkCombinations(Hashtable<Integer, KeyPress> p_keys) {
        int currentPriority = Integer.MAX_VALUE;
        Combination currentCombination = null;
        for (Combination c: m_combinations) {
            if (c.evaluate(p_keys) && c.getPriority() < currentPriority) {
                currentPriority = c.getPriority();
                currentCombination = c;
            }
        }
        return currentCombination;
    }

    private boolean outOfBounds(int x, int y) {
        return x > m_keys.length - 1 || y > m_keys[x].length - 1 || m_keys[x][y] == null;
    }

    public Vector2 getPosition(char p_key) {
        for (int iii = 0; iii < m_keys.length; iii++) {
            for (int jjj = 0; jjj < m_keys[iii].length; jjj++) {
                if (m_keys[iii][jjj] == null)
                    break;
                if (m_keys[iii][jjj].contains(p_key))
                    return new Vector2(iii, jjj);
            }
        }
        return null;
    }

    public int Rows() {
        return m_keys.length;
    }

    public int RowLength(int p_row) {
        if (p_row >= Rows() || p_row < 0) {
            return -1;
        } else {
            int count = 0;

            for (Key key: m_keys[p_row]) {
                if (key == null) {
                    return count;
                } else {
                    count++;
                }
            }
            return count;
        }
    }

    public String getName() {
        return m_name;
    }
}
