package game;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TimeManager extends ListenerAdapter {
	@Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        if (!e.getAuthor().getId().equals("519675083644469279"))
            return;
        if (e.getMessage().getContentRaw().startsWith("`ADVANCE`")) {
        	GameData.advanceTime();
        }
        
    }
}
