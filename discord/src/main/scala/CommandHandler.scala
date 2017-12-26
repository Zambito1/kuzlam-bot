
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent
import java.util._


class CommandHandler {
    @EventSubscriber def onMessageReceived(event: MessageReceivedEvent): Unit = { // Note for error handling, you'll probably want to log failed commands with a logger or sout
// In most cases it's not advised to annoy the user with a reply incase they didn't intend to trigger a
// command anyway, such as a user typing ?notacommand, the bot should not say "notacommand" doesn't exist in
// most situations. It's partially good practise and partially developer preference
// Given a message "/test arg1 arg2", argArray will contain ["/test", "arg1", "arg"]
val argArray = event.getMessage.getContent.split(" ")
        // First ensure at least the command and prefix is present, the arg length can be handled by your command func
        if (argArray.isEmpty) return

        // Check if the first arg (the command) starts with the prefix defined in the utils class
        if (!argArray(0).startsWith(BotUtils.BOT_PREFIX)) return

        // Extract the "command" part of the first arg out by just ditching the first character
        val commandStr = argArray(0).substring(1)

        // Load the rest of the args in the array into a List for safer access
        val argsList = argArray.tail


        // Begin the switch to handle the string to command mappings. It's likely wise to pass the whole event or
        // some part (IChannel) to the command handling it
        commandStr match {
            case "test" =>
                testCommand(event, argsList)
        }
    }

    private def testCommand(event: MessageReceivedEvent, args: Array[String]): Unit = {
        BotUtils.sendMessage(event.getChannel, "You ran the test command with args: " + args)
    }
}