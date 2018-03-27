package co.com.empresa.certification.proyectname.features.completing_todos;

import co.com.empresa.certification.proyectname.model.TodoStatus;
import co.com.empresa.certification.proyectname.questions.TheItemStatus;
import co.com.empresa.certification.proyectname.questions.TheItems;
import co.com.empresa.certification.proyectname.tasks.CompleteItem;
import co.com.empresa.certification.proyectname.tasks.Start;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Manual;
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
        @WithTag("version:RELEASE-1"),
})
public class CompleteATodo {

    private Actor james = Actor.named("James");

    @Managed(driver = "chrome", options = "--headless")
    private WebDriver hisBrowser;

    @Before public void jamesCanBrowseTheWeb() {
        james.can(BrowseTheWeb.with(hisBrowser));
    }

    @Test
    @Manual
    public void should_be_able_to_complete_a_todo() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog")
        );

        then(james).should(
            GivenWhenThen.seeThat(TheItemStatus.forTheItemCalled("Walk the dog"), CoreMatchers.is(TodoStatus.Completed)),
            GivenWhenThen.seeThat(TheItems.leftCount(), is(1))
        );
    }

    @Test
    public void should_see_the_number_of_todos_decrease_when_an_item_is_completed() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog")
        );

        then(james).should(seeThat(TheItems.leftCount(), is(1)));
    }
}