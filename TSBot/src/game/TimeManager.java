package game;

import cards.CardList;
import cards.HandManager;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class TimeManager extends ListenerAdapter {
	@Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        if (!e.getAuthor().getId().equals("519675083644469279")) //TSBot#3550
            return;
        if (e.getMessage().getContentRaw().startsWith("`Setup Complete`")) {
        	e.getMessage().delete().complete();
        	GameData.advanceTurn();
        	GameData.txtchnl.sendMessage("**`Turn "+GameData.getTurn()+" "+(GameData.getAR()<=2?("Headline " + GameData.getAR()):("Action Round " + (GameData.getAR()-1)/2 + " - " + (GameData.getAR()%2==0?"USA":"USSR"))) + "`**");
        }
        if (e.getMessage().getContentRaw().startsWith("`Update`")) {
        	GameData.advanceTime();
        	e.getMessage().delete().complete();
        	GameData.txtchnl.sendMessage("**`Turn "+GameData.getTurn()+" "+(GameData.getAR()<=2?("Headline " + GameData.getAR()):("Action Round " + (GameData.getAR()-1)/2 + " - " + (GameData.getAR()%2==0?"USA":"USSR"))) + "`**");
        }
        if (e.getMessage().getContentRaw().startsWith("`Headline Complete`")) {
        	e.getMessage().delete().complete();
        	if (HandManager.playmode=='h') GameData.txtchnl.sendMessage("`Update`");
        }
        if (e.getMessage().getContentRaw().startsWith("`Event Complete`")) {
        	e.getMessage().delete().complete();
        	if (HandManager.playmode=='f') {
        		GameData.txtchnl.sendMessage(PlayerList.getArray().get(PlayerList.getPhasing()).getAsMention() + ", you may now play card for Operations.");
        		HandManager.playmode='o';
        	}
        	if (HandManager.playmode=='e') GameData.txtchnl.sendMessage("`Update`");
        }
        if (e.getMessage().getContentRaw().startsWith("`Operations Complete`")) {
        	e.getMessage().delete().complete();
        	if (HandManager.playmode=='l') {
        		GameData.txtchnl.sendMessage(PlayerList.getArray().get(CardList.getCard(HandManager.activecard).getAssociation()).getAsMention() + ", you may now play card for event."); //impossible to get -1 in that area.
        		HandManager.playmode='e';
        	}
        	if (HandManager.playmode=='o') GameData.txtchnl.sendMessage("`Update`");
        }
        if (e.getMessage().getContentRaw().startsWith("`Spacing Complete")) {
        	e.getMessage().delete().complete();
        	if (HandManager.playmode=='s') GameData.txtchnl.sendMessage("`Update`");
        }
    }
}
