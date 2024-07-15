package com.adaptionsoft.games.newtrivia;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Questions {
    private final Map<Category, Deque<Question>> questions = new HashMap<>();

    public Questions() {
        for (Category category : Category.values()) {
            questions.put(category, new ArrayDeque<>());
        }
    }

    public void addQuestion(Question question) {
        questions.get(question.getCategory()).addLast(question);
    }

    public Question getNextQuestion(Category category) {
        return questions.get(category).pollFirst();
    }
}
