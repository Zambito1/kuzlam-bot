package com.kuzlam.bot.discord

import sx.blah.discord.handle.obj.IMessage

object Commands {
    private val commands = Map[String, IMessage => Unit](
        "test" -> (iMessage => {
            BotUtils.sendMessage(iMessage.getChannel, "You ran the command 'test' " +
                    s"with the args ${parseIMessage(iMessage)._2.toList}")
        })
    )

    /**
      * Execute the command given in the message if the command is valid
      * @param executor Message which contains a command
      */
    def execute(executor: IMessage): Unit = {
        val (command, _) = parseIMessage(executor)

        if(commands.contains(command))
            commands(command)(executor)
        else BotUtils.logError(s"Command not found: ${executor.getAuthor.getName} " +
                s"called ${executor.getContent} in ${executor.getChannel} in ${executor.getGuild}")
    }


    /**
      * Parses an IMessage into the command and the Array of arguments
      * @param iMessage The message to be parsed
      * @return a Tuple containing the command and the Array of arguments.
      */
    private def parseIMessage(iMessage: IMessage): (String, Array[String]) = {
        val command = iMessage.getContent.split(" ")(0).drop(BotUtils.BOT_PREFIX.length).toLowerCase
        val args = iMessage.getContent.split(" ").tail

        (command, args)
    }
}
