package org.elegantsoft.app.controllers

import com.typesafe.scalalogging.LazyLogging
import org.elegantsoft.app.enums.Status.SERVER_UP
import org.elegantsoft.app.model.{Response, Server}
import org.elegantsoft.app.service.impl.ServerServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.{CREATED, OK}
import org.springframework.http.MediaType.IMAGE_PNG_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation._

import java.nio.file.{Files, Paths}
import java.time.LocalDateTime.now
import javax.validation.Valid
import scala.jdk.CollectionConverters.IteratorHasAsScala

@RestController
@RequestMapping(path = Array("/", "/server"))
class ServerResource extends LazyLogging {
  @Autowired
  var serverService: ServerServiceImpl = _

  @GetMapping(path = Array("/","/list"))
  @ResponseBody
  def getServers: ResponseEntity[Response] = {
    Thread.sleep(1000)
    ResponseEntity.ok(Response(
      timestamp = now(),
      statusCode = OK.value(),
      status = OK,
      reason = null,
      message = "Getting all servers",
      developerMessage = null,
      data = Map("servers"-> serverService.list(30))
    ))
  }

  @GetMapping(path = Array("/ping/{ip}"))
  def pingServer(@PathVariable("ip") ip: String): ResponseEntity[Response] = {
    val server = serverService.ping(ip)

    val response = Response(
    timestamp = now(),
    statusCode = OK.value(),
    status = OK,
    reason = null,
    message = if (server.status == SERVER_UP) "Ping success" else "Ping failed",
    developerMessage = null,
    data = Map.apply(("server", server)))

    ResponseEntity.ok(response)
  }

  @PostMapping(path = Array("/save"))
  def saveServer(@RequestBody @Valid server: Server): ResponseEntity[Response] = {

    val response = Response(
    timestamp = now(),
    statusCode = CREATED.value(),
    status = CREATED,
    reason = null,
    message = "Server Created",
    developerMessage = null,
    data = Map(("server", serverService.create(server))))

    ResponseEntity.ok(response)
  }

  @GetMapping(path = Array("/get/{id}"))
  def getServer(@PathVariable("id") id: Long): ResponseEntity[Response] = {

    val response = Response(
    timestamp = now(),
    statusCode = OK.value(),
    status = OK,
    reason = null,
    message = "Server found",
    developerMessage = null,
    data = Map(("server", serverService.get(id))))

    ResponseEntity.ok(response)
  }

  @DeleteMapping(path = Array("/delete/{id}"))
  def deleteServer(@PathVariable("id") id: Long): ResponseEntity[Response] = {
    val response = Response(
    timestamp = now(),
    statusCode = OK.value(),
    status = OK,
    reason = null,
    message = "Server deleted",
    developerMessage = null,
    data = Map(("deleted", serverService.delete(id))))

    ResponseEntity.ok(response)
  }

  @GetMapping(path = Array("/server/image/{name}"), produces = Array(IMAGE_PNG_VALUE))
  def getServerImage(@PathVariable("name") name: String): Array[Byte] = {

    println(this.getClass.getResource(s"/images").getPath)
    Files.readAllBytes(Paths.get(this.getClass.getResource(s"/images/$name").getPath))
  }
}
