package co.com.empresa.certification.proyectname.questions;

import co.com.empresa.certification.proyectname.user_interface.TodoList;
import co.com.empresa.certification.proyectname.model.TodoStatusFilter;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.questions.Text;

@Subject("the displayed todo items")
public class CurrentFilter implements Question<TodoStatusFilter> {

    @Override
    public TodoStatusFilter answeredBy(Actor actor) {
        return Text.of(TodoList.SELECTED_FILTER)
                .viewedBy(actor)
                .asEnum(TodoStatusFilter.class);
    }

    public static CurrentFilter selected() {
        return new CurrentFilter();
    }
}