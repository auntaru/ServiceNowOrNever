/*

https://spring.io/guides/gs/spring-boot/
https://github.com/Apress/pro-spring-boot-2/blob/master/pro-spring-boot-2nd/ch02/spring-cli-apps/java-web/SimpleWebApp.java
https://www.youtube.com/watch?v=9zge7NBNsnM

Building an Application with Spring Boot
https://spring.io/guides/gs/spring-boot/
https://github.com/spring-guides/gs-spring-boot

BuiLD : 
mvn clean package
RuN : 
mvnw spring-boot:run
http://localhost:8080/
  => Spring Boot Rocks in Java too!


PS C:\Users\A634538\git\ServiceNowOrNever\src\main\java\start\spring\io\boot> dir
    Directory: C:\Users\A634538\git\ServiceNowOrNever\src\main\java\start\spring\io\boot
Mode                LastWriteTime         Length Name
----                -------------         ------ ----
-a----       10/15/2020   8:46 PM            884 DemoApplication.java
PS C:\Users\A634538\git\ServiceNowOrNever\src\main\java\start\spring\io\boot>

https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/2.3.4.RELEASE/
PS C:\Users\A634538\git\ServiceNowOrNever\src\main\java\start\spring\io\boot> 
         C:\Java\spring-2.3.4\bin\spring run DemoApplication.java

& 'C:\Users\A634538\AppData\Local\Far Manager x64\Far.exe'


PS C:\Users\A634538\git\ServiceNowOrNever> mvn clean package
PS C:\Users\A634538\git\ServiceNowOrNever> mvn spring-boot:run

*/

package start.spring.io.boot;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @RequestMapping("/")
    public String greetings(){
        return "<h1>Spring Boot Rocks in Java too!</h1>";
    }

    @Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}    
    
}
