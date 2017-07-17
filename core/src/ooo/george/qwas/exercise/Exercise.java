package ooo.george.qwas.exercise;

import ooo.george.qwas.exercise.content.ExerciseContent;
import ooo.george.qwas.exercise.content.StringExerciseContent;

public class Exercise {
    private ExerciseContent m_content;
    private String m_name;
    private int m_totalTyped;
    private int m_correct;
    private long m_startTime;
    private long m_finishedTime;
    private boolean m_isComplete;

    private Exercise() {
        m_totalTyped = 0;
        m_correct = 0;
        m_startTime = System.currentTimeMillis();
        m_finishedTime = -1;
        m_isComplete = false;
    }

    public Exercise(String p_name, String p_content) {
        this();
        m_content = new StringExerciseContent(p_content);
        m_name = p_name;
    }

    public Exercise(String p_name, ExerciseContent p_exerciseContent) {
        this();
        m_content = p_exerciseContent;
        m_name = p_name;
    }

    public void start() {
        m_totalTyped = 0;
        m_correct = 0;
        m_startTime = System.currentTimeMillis();
        m_finishedTime = -1;
        m_isComplete = false;
        m_content.start();
    }

    public char getNextChar() {
        return m_content.getNextCharacter();
    }

    public void attempt(char p_typed) {
        m_totalTyped++;
        boolean success = m_content.attempt(p_typed);
        isComplete();

        if (success) {
            m_correct++;
        }
    }

    public String getName() {
        return m_name;
    }

    public String getShortenedName(int p_length) {
        if (m_name.length() <= p_length) {
            return m_name;
        } else {
            return m_name.substring(0, p_length) + "...";
        }
    }

    private int getTotalCharacters() {
        return m_content.length();
    }

    private int getRemainingCharacterCount() {
        return m_content.remainingCharacters();
    }

    public String getRemainingCharacter() {
        return m_content.getRemainingText();
    }

    public boolean isComplete() {
        if (m_isComplete) {
            return true;
        } else {
            if (m_content.completed()) {
                if (m_finishedTime < 0) {
                    m_finishedTime = System.currentTimeMillis();
                }
                m_isComplete = true;
                return true;
            } else {
                return false;
            }
        }
    }

    public float charactersPerSecond(long p_time) {
        long millisecondsElapsed = (p_time - m_startTime);
        float secondsElapsed = (float)millisecondsElapsed / 1000;
        if (m_totalTyped > 0) {
            return m_correct / secondsElapsed;
        } else {
            return 0;
        }
    }

    public float charactersPerSecond() {
        return charactersPerSecond(m_finishedTime);
    }

    private int correctTypeCount() {
        return getTotalCharacters() - getRemainingCharacterCount();
    }

    public float correctlyTypedPercent() {
        if (m_totalTyped < 1) {
            return 100;
        }

        return (float)correctTypeCount() / (float)m_totalTyped * 100f;
    }

    public float percentComplete() {
        float percentComplete = getTotalCharacters();
        percentComplete -= getRemainingCharacterCount();
        percentComplete /= getTotalCharacters();
        percentComplete *= 100;
        return percentComplete;
    }

    public long getFinishedTime() {
        return m_finishedTime;
    }
}
