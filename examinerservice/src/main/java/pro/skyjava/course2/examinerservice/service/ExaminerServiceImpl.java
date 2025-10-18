package pro.skyjava.course2.examinerservice.service;

import pro.skyjava.course2.examinerservice.domain.Question;
import pro.skyjava.course2.examinerservice.exception.NotEnoughQuestionsException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final JavaQuestionService javaQuestionService;

    public ExaminerServiceImpl(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        if (amount > javaQuestionService.getAll().size()) {
            throw new NotEnoughQuestionsException("Запрошено " + amount + " вопросов, но доступно только " + javaQuestionService.getAll().size());
        }

        Set<Question> questions = new HashSet<>();

        while (questions.size() < amount) {
            Question randomQuestion = javaQuestionService.getRandomQuestion();
            if (randomQuestion != null) {
                questions.add(randomQuestion);
            }
        }

        return questions;
    }
}