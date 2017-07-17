package ooo.george.qwas.exercise.content;

public class StringExerciseContent implements ExerciseContent{
    private final String m_content;
    private int m_position;

    public StringExerciseContent(String p_text) {
        m_content = p_text.replace('\n', ' ');
        m_position = 0;
    }

    private boolean initialised() {
        return !(m_content == null || m_content.equals(" "));
    }

    public boolean start() {
        m_position = 0;
        return initialised();
    }

    public boolean attempt(char p_attempt) {
        if (remainingCharacters() < 1)
            return false;
        if (p_attempt != m_content.charAt(m_position)) {
            return false;
        } else {
            m_position++;
            return true;
        }
    }

    public boolean completed() {
        return remainingCharacters() < 1;
    }

    public char getNextCharacter() {
        if (m_position >= m_content.length()) {
            return '\0';
        } else {
            return m_content.charAt(m_position);
        }
    }

    public int length() {
        return m_content.length();
    }

    public String getRemainingText() {
        if (m_position >= m_content.length()) {
            return "";
        } else {
            return m_content.substring(m_position);
        }
    }

    public int remainingCharacters() {
        return length() - m_position;
    }
}
