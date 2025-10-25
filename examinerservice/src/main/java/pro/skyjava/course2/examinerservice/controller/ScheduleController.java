package pro.skyjava.course2.examinerservice.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.skyjava.course2.examinerservice.domain.ExamSchedule;

@RestController

public class ScheduleController {
    @GetMapping("/schedule")
    public ExamSchedule getSchedule() {
        return new ExamSchedule("Здесь будет расписание экзамена");
    }
}
