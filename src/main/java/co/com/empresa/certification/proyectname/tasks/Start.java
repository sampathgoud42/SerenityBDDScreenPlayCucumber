package co.com.empresa.certification.proyectname.tasks;

import co.com.empresa.certification.proyectname.user_interface.ApplicationHomePage;
import com.google.common.base.Joiner;
import co.com.empresa.certification.proyectname.actions.Refresh;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.conditions.Check;
import net.thucydides.core.annotations.Step;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Start implements Task {

    private final Collection<String> items;
    private ApplicationHomePage applicationHomePage;
    private final String todoListDescription;

    @Step("{0} starts with **#todoListDescription**")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.browserOn().the(applicationHomePage),
                Refresh.theBrowserSession(),
                Check.whether(items.isEmpty()).otherwise(AddTodoItems.called(items)));
    }

    public static Start withAnEmptyTodoList() {
        return instrumented(Start.class, Collections.EMPTY_LIST, "no items");
    }
    public static Start withATodoListContaining(String... items) {
        return withATodoListContaining(Arrays.asList(items));
    }

    public static Start withATodoListContaining(List<String> items) {
        return instrumented(Start.class, items, "a todo list containing " +  Joiner.on(", ").join(items));
    }

    public Start(Collection<String> items, String todoListDescription) {
        this.items = items;
        this.todoListDescription = todoListDescription;
    }
}