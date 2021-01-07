import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HealthController {

    @get:ResponseBody
    @get:GetMapping(value = ["/health_check.html"])
    val ratingStatus: ResponseEntity<String>
        get() = ResponseEntity("Health", HttpStatus.OK)

}
