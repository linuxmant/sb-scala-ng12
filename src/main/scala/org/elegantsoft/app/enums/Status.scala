package org.elegantsoft.app.enums

import com.fasterxml.jackson.core.`type`.TypeReference

object Status extends Enumeration {
  type Status = Value

  val SERVER_UP, SERVER_DOWN = Value
}

class StatusTypeReference extends TypeReference[Status.type]