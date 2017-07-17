/*package ooo.george.qwas.keyboard;

import com.badlogic.gdx.math.Vector2;
import ooo.george.qwas.Assets;
import ooo.george.qwas.Constants;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class FlatLayout implements Layout{
    private Key m_keys[][];
    private List<Combination> m_combinations;
    private boolean m_shift;
    private boolean m_space;
    private String m_name;
    public boolean m_valid;

    public FlatLayout(String p_fileContents, String p_name) {
        m_combinations = new ArrayList<>();
        m_valid = true;
        m_shift = false;
        m_space = false;
        m_name = p_name;
        m_valid = buildLayout(p_fileContents);
    }

    private boolean buildLayout(String p_fileContents) {
        if (p_fileContents == null || p_fileContents == "") {
            return false;
        }



        String[] lines = p_fileContents.split(Constants.LINE_DELIMITER);
        for (String string: lines) {
            if (string.toLowerCase().startsWith("shift")) {
                m_shift = true;
            } else if (string.toLowerCase().startsWith("mirror"))
                m_space = true;
        }

        return setKeys(lines);
    }

    private boolean setKeys(String[] p_lines) {
        if (!setArraySize(p_lines)) {
            Constants.debugMessage = "setArraySize failed";
            return false;
        }

        for (int iii = 0; iii < m_keys.length; iii++) {
            String[] lowerLine = p_lines[iii].split(Constants.KEY_DELIMITER);
            String[] upperLine = {""};
            String[] spaceLine = {""};

            if (m_shift) {
                addCombination(Assets.combinations.get("shift"));
                upperLine = p_lines[iii + 5].split(Constants.KEY_DELIMITER);
                if (lowerLine.length != upperLine.length) {
                    return false;
                }
            }
            if (m_space) {
                addCombination(Assets.combinations.get("space"));
                spaceLine = p_lines[iii + 10].split(Constants.KEY_DELIMITER);
                if (lowerLine.length != spaceLine.length) {
                    return false;
                }
            }

            for (int jjj = 0; jjj < lowerLine.length; jjj++)
                try {
                    Key newKey;
                    newKey = new HashKey(stringToChar(lowerLine[jjj]));

                    if (m_space) {
                        newKey.addKey(Assets.combinations.get("space"), stringToChar(spaceLine[jjj]));
                    }
                    if (m_shift) {
                        newKey.addKey(Assets.combinations.get("shift"), stringToChar(upperLine[jjj]));
                    }
                    m_keys[iii][jjj] = newKey;
                } catch (Exception e) {
                    return false;
                }
        }
        return true;
    }

    private boolean setArraySize(String[] p_lines) {
        int x = 0, y = 0;

        for (int iii = 0; iii < p_lines.length; iii++) {

            if (p_lines[iii].equals("") && x == 0) {
                x = iii;
            }

            int keyCount = p_lines[iii].split(Constants.KEY_DELIMITER).length;

            if (keyCount> y) {
                y = keyCount;
            }
        }

        this.m_keys = new Key[x][y];

        if (x > 0 && y > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addCombination(Combination p_combination) {
        if (m_combinations.contains(p_combination))
            return false;
        else
            return m_combinations.add(p_combination);
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

    public char getKey(int x, int y, Hashtable<Integer, Boolean> p_keys) {
        if (outOfBounds(x, y)) {
            return '\0';
        } else {
            Key key = m_keys[x][y];

            for (Combination c: m_combinations) {
                if (c.evaluate(p_keys)) {
                    return key.getKey(c);
                }
            }

            return key.getKey();
        }
    }

    private boolean outOfBounds(int x, int y) {
        if (x > m_keys.length - 1 || y > m_keys[x].length - 1) {
            return true;
        } else {
            if (m_keys[x][y] == null) {
                return true;
            } else {
                return false;
            }
        }
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
*/