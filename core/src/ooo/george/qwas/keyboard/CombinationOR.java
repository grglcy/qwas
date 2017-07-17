package ooo.george.qwas.keyboard;

import java.util.Hashtable;

public class CombinationOR extends Combination {
    public CombinationOR(int p_priority) {
        super(p_priority);
    }

    public CombinationOR(int p_int, int p_priority) {
        super(p_int);
    }

    public CombinationOR(Combination p_combination, int p_priority) {
        super(p_combination, p_priority);
    }

    public CombinationOR(int p_int, String p_name, int p_priority) {
        super(p_int, p_name, p_priority);
    }

    public CombinationOR(Combination p_combination, String p_name, int p_priority) {
        super(p_combination,p_name, p_priority);
    }

    @Override
    public boolean evaluate(Hashtable<Integer, KeyPress> p_keys) {
        if (p_keys.get(m_key) != null && p_keys.get(m_key).isPressed()) {
            return true;
        }

        for (Combination o: m_keys) {
            if (o.evaluate(p_keys)) {
                return true;
            }
        }

        return false;
    }
}
