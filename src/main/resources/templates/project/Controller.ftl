package ${packageName};

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ${appName}Controller {

@GetMapping("/hello")
public String hello() {
return "Hello from ${appName}!";
}
}
