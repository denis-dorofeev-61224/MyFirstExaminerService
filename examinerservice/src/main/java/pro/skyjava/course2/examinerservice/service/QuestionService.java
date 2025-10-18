package pro.skyjava.course2.examinerservice.service;

import pro.skyjava.course2.examinerservice.domain.Question;
import java.util.Collection;

public interface QuestionService {

    // Добавить вопрос
    Question add(String question, String answer);

    // Удалить вопрос
    Question remove(String question, String answer);

    // Получить все вопросы
    Collection<Question> getAll();

    // Получить случайный вопрос
    Question getRandomQuestion();

}
