package slackTime

import java.io.{InputStream, OutputStream}
import java.nio.charset.StandardCharsets.UTF_8
import org.joda.time.DateTime
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write
import slackTime.DateAndTime._

import scala.util.{Failure, Success, Try}


case class SlashCommand(
                         token        : String,
                         team_id      : String,
                         team_domain  : String,
                         channel_id   : String,
                         channel_name : String,
                         user_id      : String,
                         user_name    : String,
                         command      : String,
                         text         : String,
                         response_url : String
                       )

case class LongResponse(
                         text          : String,
                         response_type : String = "in_channel"
                       )

class Slack {

  implicit val formats = DefaultFormats

  def reply(json: JValue): String = Try(json.extract[SlashCommand]) match {
    case Success(cmd) if cmd.text.isEmpty => "Usage: /time brighton sydney new-york"
    case Success(cmd) =>
      val now = DateTime.now
      val places = names(cmd.text)
      val descriptions = places.map(name => describe(now, name, zoneIdOf(name.toLowerCase)))
      write(LongResponse(descriptions.mkString("\n")))
    case Failure(e) => s"Sorry, I don't understand that $e"
  }

  def time(in: InputStream, out: OutputStream): Unit = {

    val payload = scala.io.Source.fromInputStream(in).mkString("")

    val json: JValue = parse(payload)

    out.write(reply(json).getBytes(UTF_8))
  }

}
