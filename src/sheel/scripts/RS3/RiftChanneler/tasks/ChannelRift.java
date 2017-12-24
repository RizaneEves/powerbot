package sheel.scripts.RS3.RiftChanneler.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import sheel.scripts.RS3.RiftChanneler.Constants;
import sheel.scripts.RS3.RiftChanneler.Task;

public class ChannelRift extends Task<ClientContext>
{

    public ChannelRift(ClientContext ctx)
    {
        super(ctx);
    }

    @Override
    public boolean validate() {
        return ctx.players.local().animation() != Constants.CHANNELING_ANIMATION;
    }

    @Override
    public void execute()
    {
        GameObject rift = ctx.objects.select().id(Constants.RIFT_ID).poll();
        if(rift.interact("Channel"))
        {
            Condition.wait(() -> ctx.players.local().animation() == Constants.CHANNELING_ANIMATION, 250, 10);
        }
    }
}
