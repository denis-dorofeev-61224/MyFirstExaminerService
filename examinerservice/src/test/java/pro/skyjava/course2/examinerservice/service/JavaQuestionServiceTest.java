package pro.skyjava.course2.examinerservice.service;

import pro.skyjava.course2.examinerservice.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    private JavaQuestionService javaQuestionService;

    @BeforeEach
    void setUp() {
        javaQuestionService = new JavaQuestionService();
    }

    @Test
    void add_ShouldReturnCorrectQuestion() {
        // Arrange
        String questionText = "What is Java?";
        String answerText = "Programming language";

        // Act
        Question result = javaQuestionService.add(questionText, answerText);

        // Assert
        assertNotNull(result);
        assertEquals(questionText, result.question());
        assertEquals(answerText, result.answer());
        assertTrue(javaQuestionService.getAll().contains(result));
    }

    @Test
    void remove_ExistingQuestion_ShouldRemoveAndReturnQuestion() {
        // Arrange
        Question question = javaQuestionService.add("What is OOP?", "Object-Oriented Programming");

        // Act
        Question result = javaQuestionService.remove(question.question(), question.answer());

        // Assert
        assertNotNull(result);
        assertEquals(question, result);
        assertFalse(javaQuestionService.getAll().contains(question));
    }

    @Test
    void remove_NonExistingQuestion_ShouldReturnNull() {
        // Act
        Question result = javaQuestionService.remove("Non-existing question", "Answer");

        // Assert
        assertNull(result);
    }

    @Test
    void getAll_ShouldReturnUnmodifiableCollection() {
        // Arrange
        javaQuestionService.add("Question 1", "Answer 1");
        javaQuestionService.add("Question 2", "Answer 2");

        // Act
        Collection<Question> result = javaQuestionService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверяем, что коллекция неизменяемая
        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(new Question("New Question", "New Answer"));
        });
    }

    @Test
    void getRandomQuestion_WhenNotEmpty_ShouldReturnNotNull() {
        // Arrange
        javaQuestionService.add("What is Spring?", "Framework");
        javaQuestionService.add("What is Maven?", "Build tool");

        // Act & Assert - запускаем несколько раз, так как метод случайный
        for (int i = 0; i < 10; i++) {
            Question result = javaQuestionService.getRandomQuestion();
            assertNotNull(result);
        }
    }

    @Test
    void getRandomQuestion_WhenEmpty_ShouldReturnNull() {
        // Act
        Question result = javaQuestionService.getRandomQuestion();

        // Assert
        assertNull(result);
    }

    @Test
    void getRandomQuestion_ShouldReturnQuestionFromCollection() {
        // Arrange
        Question question1 = javaQuestionService.add("Question 1", "Answer 1");
        Question question2 = javaQuestionService.add("Question 2", "Answer 2");

        // Act
        Question result = javaQuestionService.getRandomQuestion();

        // Assert
        assertTrue(result.equals(question1) || result.equals(question2));
    }
}