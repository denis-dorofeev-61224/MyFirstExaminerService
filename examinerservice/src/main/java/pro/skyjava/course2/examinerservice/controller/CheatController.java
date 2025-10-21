package pro.skyjava.course2.examinerservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheatController {

    @GetMapping("/cheat")
    public String getCheat() {
        return """
               <pre>
               Мапа не коллекция,
               а List-интерфейс.
               Dependency Injection
               ожидают тебя здесь.
               
               Если выбрал Джава -
               ты выбрал путь страданий.
               Но ты не сомневайся -
               окупятся старанья.
               
               Ты выучил инты, даблы 
               и даже boolean.
               Твоя башка гудит - как растревоженный улей.
               
               Уверенность добавилась, ведь ты "типа программер"
               Ты написал свой цикл как вонючий ламер!
               
               Запомни-ка Stream API и больше не дуркуй.
               Освоишь когда лямбды - то будешь как буржуй!
               </pre>
               """;
    }
}