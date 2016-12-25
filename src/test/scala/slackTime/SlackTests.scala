package slackTime

import org.joda.time.{DateTime, DateTimeUtils}
import org.json4s.jackson.JsonMethods._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class SlackTests extends FlatSpec with Matchers with MockitoSugar with BeforeAndAfter {

  def payload(text: String) = s"""
     {
      "token" : "tokenvalue",
      "team_id" : "T123AB4CD",
      "team_domain" : "underscoreio",
      "channel_id" : "G1ABCD2EF",
      "channel_name" : "privategroup",
      "user_id" : "U012U3ABC",
      "user_name" : "richard",
      "command" : "/time",
      "text" : "$text",
      "response_url" : "https://hooks.slack.com/commands/T123AB4CD/18701940688/0foo"
    }
   """

  val s = new Slack
  val DEFAULT_DATETIME = "2013-03-10T08:30:00Z"

  before {
    DateTimeUtils.setCurrentMillisFixed(new DateTime(DEFAULT_DATETIME).getMillis)
  }

  after {
    DateTimeUtils.setCurrentMillisSystem()
  }

  behavior of "Slack.reply"

  "With nothing to parse" should "return an error message" in {
    val emptyJson = parse("{}")

    s.reply(emptyJson) shouldBe
      "Sorry, I don't understand that org.json4s.package$MappingException: No usable value for token\nDid not find value which can be converted into java.lang.String"
  }

  "With a payload to parse but empty text" should "return a slack command" in {
    val jsonWithEmptyText = parse(payload(""))
    s.reply(jsonWithEmptyText) shouldBe "Usage: /time brighton sydney new-york"
  }

  "With a payload to parse and text filled in" should "return a slack command" in {
    val jsonWithEmptyText = parse(payload("london"))

    s.reply(jsonWithEmptyText) shouldBe "{\"text\":\"In london it's 08:30 on Sunday\",\"response_type\":\"in_channel\"}"
  }

}
