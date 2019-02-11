# tsbot

### Notes on directory:

* `cards`: Everything to do with how the cards of each side are managed. Adding and removing cards (randomly or by choice), dealing, and reshuffling. 
* `events/[cardname].java`: How that card's event works.
* `game`: From joining and leaving to resetting the game when it's all over, everything to do with the game at large.
* `main`: The base of the bot, mostly handles listening for commands and all that jazz.
* `map`: Everything that edits the map goes here, from influence incrementation to DEFCON declination.
* `readwrite`: (Probably for future implementations because I cannot Java for the life of me) A save function in case one player or the bot needs to go for some reason. 
