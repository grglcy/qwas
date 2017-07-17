package ooo.george.qwas.keyboard;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Combination {
    int m_key;
    final List<Combination> m_keys;
    private String m_name;
    private final int m_priority;

    Combination(int p_priority) {
        m_keys = new ArrayList<>();
        m_priority = p_priority;
    }

    public Combination(int p_key, int p_priority) {
        this(p_priority);
        m_key = p_key;
    }
    
    Combination(Combination p_combination, int p_priority) {
        this(p_priority);
        add(p_combination);
    }

    public Combination(int p_key, String p_name, int p_priority) {
        this(p_priority);
        m_key = p_key;
        m_name = p_name;
    }

    Combination(Combination p_combination, String p_name, int p_priority) {
        this(p_priority);
        add(p_combination);
        m_name = p_name;
    }

    public List getKeys() {
        return m_keys;
    }

    public int getPriority() {
        return m_priority;
    }

    public void add(Combination p_key){
        if (!m_keys.contains(p_key)) {
            m_keys.add(p_key);
        }
    }

    public boolean evaluate(Hashtable<Integer, KeyPress> p_keys) {
        if (p_keys.get(m_key) == null || !p_keys.get(m_key).isPressed()) {
            return false;
        }

        for (Combination o: m_keys) {
            if (!o.evaluate(p_keys)) {
                return false;
            }
        }

        return true;
    }
}
