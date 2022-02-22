package org.elegantsoft.app.service

import com.typesafe.scalalogging.LazyLogging
import org.elegantsoft.app.{AppApplication, AppConfiguration}
import org.elegantsoft.app.service.impl.ServerServiceImpl
import org.scalatest.matchers.should.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.TestContextManager

import scala.jdk.CollectionConverters.CollectionHasAsScala

@SpringBootTest
@ComponentScan(basePackageClasses = Array(classOf[AppConfiguration]))
class ServerServiceTest extends org.scalatest.wordspec.AnyWordSpec with Matchers with LazyLogging {
  @Autowired
  val service: ServerServiceImpl = null

  new TestContextManager(this.getClass).prepareTestInstance(this)

  "ServerController" should {
    "have a service bean" in {
      service should not be null
    }

    "list all servers" in {
      val response = service.list(10)

      logger.info(response.getClass.getSimpleName)
      logger.info(response.mkString("\n"))
    }

    "get server image url" in {
      val url = service.setServerImageUrl()

      logger.info(url)
    }
  }
}
