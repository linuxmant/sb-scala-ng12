package org.elegantsoft.app.repo

import org.elegantsoft.app.model.Server
import org.springframework.data.jpa.repository.JpaRepository

trait ServerRepo extends JpaRepository[Server, Long] {
  def findByIpAddress(ip: String): Server
}
