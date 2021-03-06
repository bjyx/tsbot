package commands;

import cards.HandManager;
import game.GameData;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
/**
 * A different listener that updates each player's hand after every command. 
 * @author adalbert
 *
 */
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
        	lastUSAHand = GameData.txtusa.sendMessage(HandManager.getUSAHand()).complete();

        	if (lastSSRHand!=null) lastSSRHand.delete().complete();
        	lastSSRHand = GameData.txtssr.sendMessage(HandManager.getSUNHand()).complete();
        }
    }
	
	protected boolean respondToBots()
    {
        return false;
    }
}
