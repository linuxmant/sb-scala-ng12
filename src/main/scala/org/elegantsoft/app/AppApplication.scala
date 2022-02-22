package org.elegantsoft.app

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.elegantsoft.app.enums.Status.{SERVER_DOWN, SERVER_UP}
import org.elegantsoft.app.model.Server
import org.elegantsoft.app.repo.ServerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.{CorsRegistry, EnableWebMvc, WebMvcConfigurer}

import java.util

@SpringBootApplication
class AppApplication extends CommandLineRunner {

  @Autowired
  val serverRepo: ServerRepo = null

  override def run(args: String*): Unit = {
    args -> {
      serverRepo.save(new Server(1, "192.168.1.101", "Server1", "16 GB", "pc", "http://localhost:8000/static/images/server1.png", SERVER_UP))
      serverRepo.save(new Server(2, "192.168.1.20", "Server2", "32 GB", "pc", "http://localhost:8000/static/images/server2.png", SERVER_UP))
      serverRepo.save(new Server(3, "192.168.1.30", "Server3", "48 GB", "pc", "http://localhost:8000/static/images/server3.png", SERVER_DOWN))
      serverRepo.save(new Server(4, "192.168.1.40", "Server4", "62 GB", "pc", "http://localhost:8000/static/images/server4.png", SERVER_DOWN))
    }
  }
}

object AppApplication {
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[AppApplication])
  }
}

@Configuration
@EnableWebMvc
class AppConfiguration extends WebMvcConfigurer {

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]): Unit = {
    val conv = new MappingJackson2HttpMessageConverter()
    val mapper = JsonMapper.builder()
        .addModule(DefaultScalaModule)
        .addModule(new JavaTimeModule)
        .build()
    conv.setObjectMapper(mapper)
    converters.add(conv)
  }

  @Bean
  def corsConfigurer(): WebMvcConfigurer = {
    new WebMvcConfigurer {
      override def addCorsMappings(registry: CorsRegistry): Unit = {
        super.addCorsMappings(registry)
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
      }
    }
  }
}
