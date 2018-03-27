package co.com.empresa.certification.proyectname.features.maintain_my_todo_list;

import co.com.empresa.certification.proyectname.questions.TheItems;
import co.com.empresa.certification.proyectname.tasks.FilterItems;
import co.com.empresa.certification.proyectname.questions.CurrentFilter;
import co.com.empresa.certification.proyectname.tasks.CompleteItem;
import co.com.empresa.certification.proyectname.tasks.Start;
import co.com.empresa.certification.proyectname.model.TodoStatusFilter;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
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
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
@WithTags({
        @WithTag("Screenplay pattern"),
        @WithTag("version:RELEASE-2"),
})
public class FilteringTodos {

    private Actor james = Actor.named("James");
    @Managed private WebDriver hisBrowser;
    @Before public void jamesCanBrowseTheWeb() {
        james.can(BrowseTheWeb.with(hisBrowser));
    }

    @Test
    public void should_be_able_to_view_only_completed_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog"),
            FilterItems.toShow(TodoStatusFilter.Completed)
        );

        then(james).should(GivenWhenThen.seeThat(TheItems.displayed(), contains("Walk the dog")));
        and(james).should(seeThat(TheItems.displayed(), not(contains("Put out the garbage"))));
        and(james).should(seeThat(CurrentFilter.selected(), Matchers.is(TodoStatusFilter.Completed)));
    }

    @Test
    public void should_be_able_to_view_only_incomplete_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog"),
            FilterItems.toShow(TodoStatusFilter.Active)
        );

        then(james).should(seeThat(TheItems.displayed(), contains("Put out the garbage")));
        and(james).should(seeThat(TheItems.displayed(), not(contains("Walk the dog"))));
        and(james).should(seeThat(CurrentFilter.selected(), Matchers.is(TodoStatusFilter.Active)));
    }

    @Test
    public void should_be_able_to_view_both_complete_and_incomplete_todos() {

        givenThat(james).wasAbleTo(Start.withATodoListContaining("Walk the dog", "Put out the garbage"));

        when(james).attemptsTo(
            CompleteItem.called("Walk the dog"),
            FilterItems.toShow(TodoStatusFilter.Active),
            FilterItems.toShow(TodoStatusFilter.All)
        );

        then(james).should(seeThat(TheItems.displayed(), contains("Walk the dog", "Put out the garbage")));
        and(james).should(seeThat(CurrentFilter.selected(), Matchers.is(TodoStatusFilter.All)));
    }
}