package commands;

import cards.HandManager;
import game.GameData;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class HandSender extends ListenerAdapter {
	
	private static Message lastUSAHand;
	private static Message lastSSRHand;
	
	@Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        if (e.getAuthor().isBot() && !respondToBots())
            return;
        if (GameData.hasGameStarted() && !GameData.hasGameEnded() && e.getMessage().getContentRaw().startsWith("TS.")) {
        	if (lastUSAHand!=null) lastUSAHand.delete().complete();
        	if (lastSSRHand!=null) lastSSRHand.delete().complete();
        	lastUSAHand = GameData.txtusa.sendMessage(HandManager.getUSAHand()).complete();
        	lastSSRHand = GameData.txtssr.sendMessage(HandManager.getSUNHand()).complete();
        }
    }
	
	protected boolean respondToBots()
    {
        return false;
    }
}
