package sheel.scripts.RS3.RiftChanneler;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import sheel.scripts.RS3.RiftChanneler.tasks.ChannelRift;
import org.powerbot.script.rt6.Constants;
import sheel.scripts.RS3.RiftChanneler.tasks.RotateCamera;
import sheel.scripts.RS3.RiftChanneler.tasks.SealAnomaly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Script.Manifest(name = "SRiftChanneler", description = "Channels the rift in Christmas event!" )
public class RiftChanneler extends PollingScript<ClientContext> implements PaintListener
{

    private ChannelRift channelRift = new ChannelRift(ctx);
    private SealAnomaly sealAnomaly = new SealAnomaly(ctx);
    //private RotateCamera rotateCamera = new RotateCamera(ctx);

    private List<Task> taskList;

    private int summoningStartExp = 0;
    private int summoningStartLevel = 0;

    @Override
    public void start()
    {
        super.start();
        taskList = new ArrayList<>();
        taskList.add(channelRift);
        taskList.add(sealAnomaly);
        //taskList.add(rotateCamera);

        summoningStartExp = ctx.skills.experience(Constants.SKILLS_SUMMONING);
        summoningStartLevel = ctx.skills.level(Constants.SKILLS_SUMMONING);
    }

    @Override
    public void poll()
    {
        for (Task task : taskList)
        {
            if(task.validate())
            {
                task.execute();
            }
        }

    }


    @Override
    public void repaint(Graphics graphics)
    {
        long milliseconds = this.getTotalRuntime();
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60) % 60);
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;

        int summoningExpRate = (ctx.skills.experience(Constants.SKILLS_SUMMONING) - summoningStartExp);
        int summoningLevelsGained = (ctx.skills.level(Constants.SKILLS_SUMMONING) - summoningStartLevel);

        int x = 5;
        int y = 400;

        Graphics2D g = (Graphics2D) graphics;

        g.setColor(new Color(0, 76, 153, 180));
        g.fillRect(x, y, 240, 130);

        g.setColor(new Color(255, 255, 255));
        g.drawRect(x, y, 240, 130);

        g.drawString("Sheel's RiftChanneler", x + 5, y + 26);
        g.drawString("Running for: " + String.format("%02d:%02d:%02d", hours, minutes, seconds), x + 5, y + 46);
        g.drawString("Summoning exp gained: " + summoningExpRate, x + 5, y + 66);
        g.drawString("Summoning levels gained: " + summoningLevelsGained, x + 5, y + 86);
    }

}
