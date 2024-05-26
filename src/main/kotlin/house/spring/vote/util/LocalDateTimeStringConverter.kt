package house.spring.vote.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeStringConverter {

    private const val PATTERN: String = "yyyy-MM-dd HH:mm:ss"
    fun convertToString(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ofPattern(PATTERN))
    }

    fun convertToLocalDateTime(localDateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ofPattern(PATTERN))
    }

}