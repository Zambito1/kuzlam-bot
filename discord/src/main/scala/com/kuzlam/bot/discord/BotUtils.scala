package com.kuzlam.bot.discord

/**
  * Based on https://discord4j.readthedocs.io/en/latest/Command-structures/?highlight=BotUtils
  */

import sx.blah.discord.Discord4J.Discord4JLogger
import sx.blah.discord.api.{ClientBuilder, IDiscordClient}
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.util.RequestBuffer.IVoidRequest
import sx.blah.discord.util.{DiscordException, RequestBuffer}

object BotUtils {
    val BOT_PREFIX = "."

    def getBuiltDiscordClient(token: String): IDiscordClient = {
        new ClientBuilder().withToken(token).build()
    }

    /**
      * Function to safely send a message to a Discord channel.
      * @param channel The channel which the message will be sent to.
      * @param message The message that will be sent to the channel.
      */
    def sendMessage(channel: IChannel, message: String): Unit = {
        RequestBuffer.request(new IVoidRequest {
            override def doRequest(): Unit = {
                try{
                    channel.sendMessage(message)
                } catch {
                    case e: DiscordException =>
                        logError("Message could not be sent with error: " + e.getErrorMessage)
                }
            }
        })
    }

    private val logger = new Discord4JLogger("LOG")

    private[discord] def logError(err: String): Unit = {
        logger.error(err)
    }

    private[discord] def log(str: String): Unit = {
        logger.info(str)
    }

}
