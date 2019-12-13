package actors;

import akka.actor.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

public class TickWebSocketActor extends AbstractActor {
    private Cancellable cancellable;
    private ActorRef out;

    public TickWebSocketActor(ActorRef out) {
        this.out = out;
        ActorSystem system = getContext().getSystem();

        cancellable = system
            .scheduler()
            .schedule(Duration.ofSeconds(1), Duration.ofSeconds(1), self(), "Tick", system.dispatcher(), self());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> message.equals("Tick"), message -> out.tell(buildDateTimeString(), self()))
                .build();
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        cancellable.cancel();
    }

    private String buildDateTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public static Props props(ActorRef out) {
        return Props.create(TickWebSocketActor.class, out);
    }
}
