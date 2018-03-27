package co.com.empresa.certification.proyectname.questions;

import co.com.empresa.certification.proyectname.model.ApplicationInformation;
import co.com.empresa.certification.proyectname.user_interface.TodoListApp;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.Text;

import static net.serenitybdd.screenplay.questions.ValueOf.the;

public class Application implements Question<ApplicationInformation> {

    @Override
    public ApplicationInformation answeredBy(Actor actor) {
        String title = BrowseTheWeb.as(actor).getTitle();
        String heading = the(Text.of(TodoListApp.MAIN_HEADING).viewedBy(actor));
        String aboutInformation = the(Text.of(TodoListApp.FOOTER).viewedBy(actor));

        return new ApplicationInformation(title, heading, aboutInformation);
    }

    public static Application information() {
        return new Application();
    }
}