package org.elegantsoft.app.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import lombok.Data
import org.elegantsoft.app.enums.Status.Status
import org.elegantsoft.app.enums.StatusTypeReference

import javax.persistence.GenerationType.AUTO
import javax.persistence.{Column, Entity, GeneratedValue, Id}
import javax.validation.constraints.NotEmpty
import scala.beans.BeanProperty

@SerialVersionUID(7876879811111L)
@Entity
@Data
class Server extends Serializable {
  def this(_id: Long, _ip: String, _name: String, _mem: String, _type: String, _img: String, _status: Status) {
    this()
    id = _id
    ipAddress = _ip
    name = _name
    memory = _mem
    `type` = _type
    image = _img
    status = _status
  }

  @Id
  @GeneratedValue(strategy = AUTO)
  @JsonProperty("id")
  @BeanProperty
  var id: Long = _

  @Column(unique = true)
  @NotEmpty(message = "IP address cannot be empty or null")
  @JsonProperty("ipAddress")
  @BeanProperty
  var ipAddress: String = _

  @JsonProperty("name")
  @BeanProperty
  var name: String = _

  @JsonProperty("memory")
  @BeanProperty
  var memory: String = _

  @JsonProperty("type")
  @BeanProperty
  var `type`: String = _

  @JsonProperty("image")
  @BeanProperty
  var image: String = _

  @JsonProperty("status")
  @BeanProperty
  @JsonScalaEnumeration(classOf[StatusTypeReference])
  var status: Status = _
}

