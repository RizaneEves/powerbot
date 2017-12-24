package sheel.scripts.RS3.RiftChanneler;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class Task<C extends ClientContext> extends ClientAccessor<C>
{
    public Task(C ctx) {
        super(ctx);
    }

    public abstract boolean validate();

    public abstract void execute();
}
