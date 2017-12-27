package com.kuzlam.bot.discord

/**
  * Based on https://discord4j.readthedocs.io/en/latest/Command-structures/?highlight=BotUtils
  */

import sx.blah.discord.api.IDiscordClient

object DiscordBot {
    def apply(): Unit = {
        val token = "Mzk1MzU5MzQzMDgxNDIyODYx.DSRupQ.VkNioYpx1OFcbDR8IHtOJbUND4E"

        val cli: IDiscordClient = BotUtils.getBuiltDiscordClient(token)

        // Register a listener via the EventSubscriber annotation which allows for organisation and delegation of events
        cli.getDispatcher.registerListener(new CommandHandler())

        // Only login after all events are registered otherwise some may be missed.
        cli.login()
    }
}
