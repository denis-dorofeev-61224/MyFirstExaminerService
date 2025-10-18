package pro.skyjava.course2.examinerservice.controller;
import pro.skyjava.course2.examinerservice.domain.Question;
import pro.skyjava.course2.examinerservice.service.JavaQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@RestController
@RequestMapping("/exam/java")
public class JavaQuestionController {
    private final JavaQuestionService javaQuestionService;

    public JavaQuestionController(JavaQuestionService javaQuestionService) {
        this.javaQuestionService = javaQuestionService;
    }

    @GetMapping
    public Collection<Question> getAllQuestions() {
        return javaQuestionService.getAll();
    }

    @GetMapping("/add")   //@PostMapping("/add")
    public Question addQuestion(@RequestParam String question,
                                @RequestParam String answer) {
        return javaQuestionService.add(question, answer);
    }

    @GetMapping("/remove") //@DeleteMapping("/remove")
    public Question removeQuestion(@RequestParam String question,
                                   @RequestParam String answer) {
        return javaQuestionService.remove(question, answer);
    }

}
