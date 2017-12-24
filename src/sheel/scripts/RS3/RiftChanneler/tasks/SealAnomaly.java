package sheel.scripts.RS3.RiftChanneler.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.MobileIdNameQuery;
import org.powerbot.script.rt6.Npc;
import sheel.scripts.RS3.RiftChanneler.Constants;
import sheel.scripts.RS3.RiftChanneler.Task;

import java.util.ArrayList;
import java.util.List;

public class SealAnomaly extends Task<ClientContext> {
    public SealAnomaly(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean validate() {
        return ctx.players.local().animation() == Constants.CHANNELING_ANIMATION;
    }

    @Override
    public void execute()
    {
        MobileIdNameQuery<Npc> anomaliesQuery = ctx.npcs.select().id(Constants.ANOMALY_ID);
        List<Npc> anomalies = new ArrayList<>();
        anomaliesQuery.addTo(anomalies);

        for(Npc anomaly : anomalies)
        {
            if(anomaly.hover())
            {
                if(anomaly.interact("Seal"))
                {
                    Condition.sleep(700);
                }
            }
        }
    }
}
