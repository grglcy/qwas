package ooo.george.qwas.keyboard.key;

import ooo.george.qwas.keyboard.Combination;

import java.util.HashMap;
import java.util.Map;

public class HashKey implements Key{
    private final char m_default;
    private final HashMap<Combination, Character> m_values;

    public HashKey(char p_value) {
        m_values = new HashMap<>();
        m_default = p_value;
    }

    public void addKey(Combination p_key, char p_value) {
        if (!m_values.containsKey(p_key)) {
            m_values.put(p_key, p_value);
        }
    }

    public char getKey() {
        return m_default;
    }

    public char getKey(Combination p_modifier) {
        return m_values.get(p_modifier);
    }

    public boolean contains(char p_value) {
        if (m_default == p_value) {
            return true;
        }

        for (Map.Entry<Combination, Character> entry: m_values.entrySet()) {
            if (p_value == entry.getValue()) {
                return true;
            }
        }

        return false;
    }
}
