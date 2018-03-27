package co.com.bancolombia.certification.proyectname.features.completing_todos;

import co.com.bancolombia.certification.proyectname.model.TodoStatus;
import co.com.bancolombia.certification.proyectname.questions.TheItemStatus;
import co.com.bancolombia.certification.proyectname.questions.TheItems;
import co.com.bancolombia.certification.proyectname.tasks.Start;
import co.com.bancolombia.certification.proyectname.tasks.ToggleStatus;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SerenityRunner.class)
@WithTags({
        @WithTag("Screenplay pattern"),
        @WithTag("version:RELEASE-2"),
})
public class ToggleAllTodos {

    @Managed(driver = "chrome", options = "--headless")
    private WebDriver hisBrowser;

    private Actor james = Actor.named("James");

    @Before
    public void jamesCanBrowseTheWeb() {
        james.can(BrowseTheWeb.with(hisBrowser));
    }

    @Test
    public void should_be_able_to_quickly_complete_all_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
                ToggleStatus.ofAllItems()
        );

        then(james).should(
                GivenWhenThen.seeThat(TheItemStatus.forTheItemCalled("Walk the dog"), CoreMatchers.is(TodoStatus.Completed)),
                GivenWhenThen.seeThat(TheItemStatus.forTheItemCalled("Put out the garbage"), CoreMatchers.is(TodoStatus.Completed))
        );
    }

    @Test
    public void should_be_able_to_toggle_status_of_all_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
                ToggleStatus.ofAllItems(),
                ToggleStatus.ofAllItems()
        );

        then(james).should(
                GivenWhenThen.seeThat(TheItemStatus.forTheItemCalled("Walk the dog"), CoreMatchers.is(TodoStatus.Active)),
                GivenWhenThen.seeThat(TheItemStatus.forTheItemCalled("Put out the garbage"), CoreMatchers.is(TodoStatus.Active))
        );
    }


    @Test
    public void should_see_that_there_are_zero_items_todo_when_all_are_toggled_complete() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
                ToggleStatus.ofAllItems()
        );

        then(james).should(
                GivenWhenThen.seeThat(TheItems.leftCount(), is(0))
        );
    }

    @Test
    public void should_see_how_many_items_todo_when_all_are_toggled_to_incomplete() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
                ToggleStatus.ofAllItems(),
                ToggleStatus.ofAllItems()
        );

        then(james).should(
                seeThat(TheItems.leftCount(), is(2))
        );
    }
}