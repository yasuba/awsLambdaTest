package slackTime

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import scala.collection.JavaConverters._

trait DateAndTime {

  lazy val zones = DateTimeZone.getAvailableIDs.asScala

  def names(text: String): Set[String] =
    text.split("\\s+").toSet

  def nameMatch(lowerCasePlace: String)(zoneId: String): Boolean =
    (zoneId.toLowerCase contains lowerCasePlace) ||
      (zoneId.toLowerCase contains lowerCasePlace.replace('-', '_'))

  def zoneIdOf(lowerCasePlace: String): Option[DateTimeZone] =
    zones
      .find(nameMatch(lowerCasePlace))
      .flatMap(n => Option(DateTimeZone.forID(n)))

  def describe(now: DateTime, place: String, zoneId: Option[DateTimeZone]): String = {
    zoneId match {
      case None => s"I don't know the time in $place"
      case Some(z) => val format = DateTimeFormat.forPattern(s"'In $place it''s' HH:mm 'on' EEEE")
        .withZone(DateTimeZone.forID(z.getID))
        now.toString(format)
    }
  }

}

object DateAndTime extends DateAndTime
