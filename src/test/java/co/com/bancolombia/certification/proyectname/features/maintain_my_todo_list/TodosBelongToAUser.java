package co.com.bancolombia.certification.proyectname.features.maintain_my_todo_list;

import co.com.bancolombia.certification.proyectname.questions.TheItems;
import co.com.bancolombia.certification.proyectname.tasks.Clear;
import co.com.bancolombia.certification.proyectname.tasks.CompleteItem;
import co.com.bancolombia.certification.proyectname.tasks.Start;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.contains;

@RunWith(SerenityRunner.class)
@WithTag("Screenplay pattern")
@WithTags({
        @WithTag("Screenplay pattern"),
        @WithTag("version:RELEASE-3"),
})
public class TodosBelongToAUser {

    private Actor james = Actor.named("James");
    private Actor jane = Actor.named("Jane");

    @Managed private WebDriver hisBrowser;
    @Managed private WebDriver herBrowser;

    @Before
    public void jamesCanBrowseTheWeb() {
        james.can(BrowseTheWeb.with(hisBrowser));
        jane.can(BrowseTheWeb.with(herBrowser));
    }

    @Test
    public void should_not_affect_todos_belonging_to_another_user() {
        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));
        andThat(jane).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Feed the cat"));

        when(james).attemptsTo(
                CompleteItem.called("Walk the dog"),
                Clear.completedItems()
        );

        then(jane).should(GivenWhenThen.seeThat(TheItems.displayed(), contains("Walk the dog", "Feed the cat")));
    }
}