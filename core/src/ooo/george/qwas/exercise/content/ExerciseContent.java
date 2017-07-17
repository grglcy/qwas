package ooo.george.qwas.exercise.content;

public interface ExerciseContent {
    String getRemainingText();

    int length();

    char getNextCharacter();

    int remainingCharacters();

    boolean attempt(char p_attempt);

    boolean completed();

    boolean start();
}
