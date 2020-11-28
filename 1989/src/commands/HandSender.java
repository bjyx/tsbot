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
	
	private static Message lastDemHand;
	private static Message lastComHand;
	
	@Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        if (e.getAuthor().isBot() && !respondToBots())
            return;
        if (GameData.hasGameStarted() && !GameData.hasGameEnded() && e.getMessage().getContentRaw().startsWith("DF.")) {
        	if (lastDemHand!=null) lastDemHand.delete().complete();
        	lastDemHand = GameData.txtdem.sendMessage(HandManager.getUSAHand()).complete();

        	if (lastComHand!=null) lastComHand.delete().complete();
        	lastComHand = GameData.txtcom.sendMessage(HandManager.getSUNHand()).complete();
        }
    }
	
	protected boolean respondToBots()
    {
        return false;
    }
}
