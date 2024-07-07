package io.todo.enums;

public enum Difficult {
    ALL, HARD, AVERAGE, EASY, NONE;

    public static boolean allDifficult(String difficult) {
        return (HARD.toString()
                    .equals(difficult) || AVERAGE.toString()
                                                 .equals(difficult)) || (EASY.toString()
                                                                             .equals(difficult)) || (NONE.toString()
                                                                                                         .equals(difficult));
    }
}
