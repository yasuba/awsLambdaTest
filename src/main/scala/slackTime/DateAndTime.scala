package slackTime

import java.time.ZoneId
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import scala.collection.JavaConverters._

trait DateAndTime {

  lazy val allZones = ZoneId.getAvailableZoneIds.asScala

  val uk = ZoneId.of("Europe/London")
  val eastCoast = ZoneId.of("America/New_York")
  val westCoast = ZoneId.of("America/Los_Angeles")

  val aliases = Map(
    "brighton"     -> uk,
    "portland"     -> westCoast,
    "philadelphia" -> eastCoast,
    "philly"       -> eastCoast
  )



  def names(text: String): Set[String] =
    text.split("\\s+").toSet

  def nameMatch(lowerCasePlace: String)(zoneId: String): Boolean =
    (zoneId.toLowerCase contains lowerCasePlace) ||
      (zoneId.toLowerCase contains lowerCasePlace.replace('-', '_'))

  def zoneIdOf(lowerCasePlace: String): Option[ZoneId] =
    aliases.get(lowerCasePlace) orElse {
      allZones
        .find(nameMatch(lowerCasePlace))
        .flatMap(n => Option(ZoneId.of(n)))
    }

  def describe(now: DateTime, place: String, zoneId: Option[ZoneId]): String = {
    zoneId match {
      case None => s"I don't know the time in $place"
      case Some(z) => val format = DateTimeFormat.forPattern(s"'In $place it''s' HH:mm 'on' EEEE")
        .withZone(DateTimeZone.forID(z.getId))
        now.toString(format)
    }
  }

}

object DateAndTime extends DateAndTime
