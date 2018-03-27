package co.com.empresa.certification.proyectname.features.maintain_my_todo_list;

import co.com.empresa.certification.proyectname.questions.ClearCompletedItems;
import co.com.empresa.certification.proyectname.questions.TheItems;
import co.com.empresa.certification.proyectname.tasks.Clear;
import co.com.empresa.certification.proyectname.tasks.CompleteItem;
import co.com.empresa.certification.proyectname.tasks.Start;
import co.com.empresa.certification.proyectname.questions.ElementAvailability;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@RunWith(SerenityRunner.class)
@WithTags({
        @WithTag("Screenplay pattern"),
        @WithTag("version:RELEASE-2"),
})
public class ClearCompletedTodos {

    @Managed private WebDriver hisBrowser;
    private Actor james = Actor.named("James");
    @Before public void jamesCanBrowseTheWeb() {
        james.can(BrowseTheWeb.with(hisBrowser));
    }

    @Test
    public void should_be_able_to_clear_completed_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
                CompleteItem.called("Walk the dog"),
                Clear.completedItems()
        );

        then(james).should(seeThat(TheItems.displayed(), contains("Put out the garbage")));
    }

    @Test
    public void should_not_be_able_to_clear_completed_todos_if_none_are_complete() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        then(james).should(seeThat(ClearCompletedItems.option(), Matchers.is(ElementAvailability.Unavailable)));
    }
}