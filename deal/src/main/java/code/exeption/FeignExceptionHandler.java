package code.exeption;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class FeignExceptionHandler {
    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public String handleFeignStatusException(FeignException e, HttpServletResponse response) {
        log.error("error in conveyor ms: " + e.contentUTF8());
        response.setStatus(e.status());
        return e.contentUTF8();
    }
}
