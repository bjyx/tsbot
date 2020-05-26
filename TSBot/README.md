# tsbot

### Notes on directory:

* `cards`: Everything to do with how the cards of each side are managed. Adding and removing cards (randomly or by choice), dealing, and reshuffling. 
* `commands`: The commands used during the game.
* `events/[cardname].java`: How each card's event works.
* `game`: From joining and leaving to resetting the game when it's all over, everything to do with the game at large.
* `images`: Pictures used by the bot.
* `log`: The function that produces the log.
* `main`: The base of the bot. Would normally contain the Launcher and convenience commands, but they are not shown here. 
* `map`: The map, and everything that edits the map. 
* `promo`: The cards included in the Kickstarter promo. 
* `readwrite`: A save function in case one player or the bot needs to go for some reason. 
* `turnzero`: The function that handles the Turn Zero Expansion to the game.
* `yiyo`: The cards in [this fan-made expansion pack](https://www.reddit.com/r/twilightstruggle/comments/en61mu/twilight_struggle_fan_expansion_year_in_year_out/) by u/Aogu on Reddit.