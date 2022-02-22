package org.elegantsoft.app.controllers

import com.typesafe.scalalogging.LazyLogging
import org.elegantsoft.app.AppApplication
import org.scalatest.matchers.should.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.TestContextManager

import javax.annotation.PostConstruct

@SpringBootTest(classes = Array(classOf[AppApplication]), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerResourceTest extends org.scalatest.wordspec.AnyWordSpec with Matchers with LazyLogging {
  @LocalServerPort
  var localPort: Int = _

  @Autowired
  val builder: RestTemplateBuilder = null

  var client: TestRestTemplate = _

  @PostConstruct
  def setup(): Unit = {
    val tmp = builder.rootUri("http://localhost:" + localPort)

    this.client = new TestRestTemplate(tmp, null, null, HttpClientOption.ENABLE_COOKIES)
  }

  new TestContextManager(this.getClass).prepareTestInstance(this)

  "ServerResource" should {
    "have a service bean" in {
      client should not be null
    }

    "list all servers" in {
      val response = client.getForEntity("/server/list", classOf[Any])

      logger.info(response.getClass.getSimpleName)
      logger.info(response.toString)
    }

    "get image bytes" in {
      val response = client.getForObject("/server/image/server1.png", classOf[Array[Byte]])

      logger.info(response.getClass.getSimpleName)
    }
  }
}
