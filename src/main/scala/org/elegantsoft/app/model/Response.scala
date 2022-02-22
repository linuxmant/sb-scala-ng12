package org.elegantsoft.app.model

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.experimental.SuperBuilder
import lombok.{AllArgsConstructor, Data, NoArgsConstructor}
import org.springframework.http.HttpStatus

import java.time.LocalDateTime
import scala.beans.BeanProperty

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
case class Response(
                       @BeanProperty timestamp: LocalDateTime,
                       @BeanProperty statusCode: Int,
                       @BeanProperty status: HttpStatus,
                       @BeanProperty reason: String,
                       @BeanProperty message: String,
                       @BeanProperty developerMessage: String,
                       @BeanProperty data: Map[Any, Any]
                   )
