package sheel.scripts.RS3.RiftChanneler.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.MobileIdNameQuery;
import sheel.scripts.RS3.RiftChanneler.Constants;
import sheel.scripts.RS3.RiftChanneler.Task;

public class RotateCamera extends Task<ClientContext>
{
    public RotateCamera(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean validate() {
        return ctx.players.local().animation() == Constants.CHANNELING_ANIMATION;
    }

    @Override
    public void execute()
    {
        int pitch = Random.nextInt(30, 90);
        GameObject object = ctx.objects.select().shuffle().poll();

        Condition.wait(() -> ctx.camera.pitch(pitch), 250, 10);
        Condition.wait(() -> object.hover(), 250, 10);
    }
}
