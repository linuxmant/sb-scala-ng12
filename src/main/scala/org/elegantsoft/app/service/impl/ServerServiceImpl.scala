package org.elegantsoft.app.service.impl

import com.typesafe.scalalogging.LazyLogging
import lombok.RequiredArgsConstructor
import org.elegantsoft.app.enums.Status
import org.elegantsoft.app.model.Server
import org.elegantsoft.app.repo.ServerRepo
import org.elegantsoft.app.service.ServerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import java.net.InetAddress
import java.util
import javax.transaction.Transactional
import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.util.Random

@RequiredArgsConstructor
@Service
@Transactional
class ServerServiceImpl extends ServerService with LazyLogging {
  @Autowired
  val serverRepo: ServerRepo = null

  def setServerImageUrl(): String = {
    val serverImages = Array("server1.png", "server2.png", "server3.png", "server4.png")
    ServletUriComponentsBuilder.fromCurrentContextPath().path("/server/image/" +
        serverImages(new Random().nextInt(4))).toUriString
  }

  override def create(server: Server): Server = {
    logger.info("Saving server: {}", server.name)
    server.image = setServerImageUrl()
    serverRepo.save(server)
  }

  override def list(limit: Int): Iterable[Server] = {
    logger.info("Getting all servers")
    Thread.sleep(100)
    serverRepo.findAll(PageRequest.of(0, limit)).toList.asScala
  }

  override def get(id: Long): Server = {
    logger.info(s"Getting server by id: ${id}")
    serverRepo.findById(id).get()
  }

  override def update(server: Server): Server = {
    logger.info(s"Updating server: ${server.name}")
    serverRepo.save(server)
  }

  override def delete(id: Long): Boolean = {
    logger.info(s"deleting server: ${id}")
    serverRepo.deleteById(id)
    true
  }

  override def ping(ip: String): Server = {
    logger.info(s"Pinging server ip ${ip}")
    val server = serverRepo.findByIpAddress(ip)
    val address: InetAddress = InetAddress.getByName(ip)
    server.status = if (address.isReachable(10000)) Status.SERVER_UP else Status.SERVER_DOWN
    serverRepo.save(server)
  }
}
