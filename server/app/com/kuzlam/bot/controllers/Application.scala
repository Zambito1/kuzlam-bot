package com.kuzlam.bot.controllers

import javax.inject._
import com.kuzlam.bot.shared.SharedMessages
import play.api.mvc._
import com.kuzlam.bot.discord.DiscordBot

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

    def index = Action {
        Ok(views.html.index(SharedMessages.itWorks))
    }

    def startBot = Action {
        DiscordBot()
        Ok(views.html.botstart())
    }

}
