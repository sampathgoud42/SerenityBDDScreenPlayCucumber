package co.com.empresa.certification.proyectname.tasks;

import co.com.empresa.certification.proyectname.user_interface.TodoList;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;

import static org.openqa.selenium.Keys.RETURN;

public class AddATodoItem {

    public static Task called(String thingToDo) {
        return Task.where("{0} adds a todo item called: #thingToDo",
                Enter.theValue(thingToDo)
                        .into(TodoList.WHAT_NEEDS_TO_BE_DONE)
                        .thenHit(RETURN)
                ).with("thingsToDo").of(thingToDo);
    }
}