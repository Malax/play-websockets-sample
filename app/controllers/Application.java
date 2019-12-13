package controllers;

import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import javax.inject.Inject;
import actors.TickWebSocketActor;

public class Application extends Controller {
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public Application(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public Result index(Http.Request request) {
        return ok(views.html.index.render(request));
    }

    public WebSocket pingWs() {
        return WebSocket.Text.accept(request -> ActorFlow.actorRef(TickWebSocketActor::props, actorSystem, materializer));
    }
}
