package pro.skyjava.course2.examinerservice.service;

import pro.skyjava.course2.examinerservice.domain.Question;
import pro.skyjava.course2.examinerservice.exception.NotEnoughQuestionsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    void getQuestions_ShouldReturnCorrectAmountOfQuestions() {
        // Arrange
        int amount = 3;
        Set<Question> mockQuestions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3"),
                new Question("Q4", "A4"),
                new Question("Q5", "A5")
        );

        when(javaQuestionService.getAll()).thenReturn(mockQuestions);
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(new Question("Q1", "A1"))
                .thenReturn(new Question("Q2", "A2"))
                .thenReturn(new Question("Q3", "A3"));

        // Act
        Collection<Question> result = examinerService.getQuestions(amount);

        // Assert
        assertNotNull(result);
        assertEquals(amount, result.size());
        verify(javaQuestionService, times(amount)).getRandomQuestion();
    }

    @Test
    void getQuestions_AllQuestionsShouldBeUnique() {
        // Arrange
        int amount = 3;
        Set<Question> mockQuestions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3"),
                new Question("Q4", "A4")
        );

        when(javaQuestionService.getAll()).thenReturn(mockQuestions);
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(new Question("Q1", "A1"))
                .thenReturn(new Question("Q2", "A2"))
                .thenReturn(new Question("Q3", "A3"));

        // Act
        Collection<Question> result = examinerService.getQuestions(amount);

        // Assert
        assertEquals(amount, new HashSet<>(result).size()); // Все вопросы уникальны
    }

    @Test
    void getQuestions_WhenAmountExceedsAvailable_ShouldThrowNotEnoughQuestionsException() {
        // Arrange
        int requestedAmount = 5;
        Set<Question> availableQuestions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3")
        );

        when(javaQuestionService.getAll()).thenReturn(availableQuestions);

        // Act & Assert
        NotEnoughQuestionsException exception = assertThrows(
                NotEnoughQuestionsException.class,
                () -> examinerService.getQuestions(requestedAmount)
        );

        assertEquals("Запрошено 5 вопросов, но доступно только 3", exception.getMessage());
        verify(javaQuestionService, never()).getRandomQuestion();
    }

    @Test
    void getQuestions_ShouldUseJavaQuestionServiceForRandomQuestions() {
        // Arrange
        int amount = 2;
        Set<Question> mockQuestions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3")
        );

        Question question1 = new Question("Q1", "A1");
        Question question2 = new Question("Q2", "A2");

        when(javaQuestionService.getAll()).thenReturn(mockQuestions);
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(question1)
                .thenReturn(question2);

        // Act
        Collection<Question> result = examinerService.getQuestions(amount);

        // Assert
        verify(javaQuestionService, times(amount)).getRandomQuestion();
        assertTrue(result.contains(question1));
        assertTrue(result.contains(question2));
    }

    @Test
    void getQuestions_WhenDuplicateRandomQuestion_ShouldContinueUntilEnoughUnique() {
        // Arrange
        int amount = 2;
        Set<Question> mockQuestions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3")
        );

        Question question1 = new Question("Q1", "A1");
        Question question2 = new Question("Q2", "A2");

        when(javaQuestionService.getAll()).thenReturn(mockQuestions);
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(question1)  // первый вызов
                .thenReturn(question1)  // дубликат - второй вызов
                .thenReturn(question2); // уникальный - третий вызов

        // Act
        Collection<Question> result = examinerService.getQuestions(amount);

        // Assert
        assertEquals(amount, result.size());
        verify(javaQuestionService, times(3)).getRandomQuestion(); // 3 вызова из-за дубликата
    }

    @Test
    void getQuestions_WithZeroAmount_ShouldReturnEmptyCollection() {
        // Arrange
        int amount = 0;

        // Act
        Collection<Question> result = examinerService.getQuestions(amount);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(javaQuestionService, never()).getRandomQuestion();
    }
}