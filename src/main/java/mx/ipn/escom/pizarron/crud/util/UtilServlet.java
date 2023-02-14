package mx.ipn.escom.pizarron.crud.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class UtilServlet {

    private UtilServlet() {
        throw new IllegalStateException("Utility class");
    }

    public static String getToken() {
        StringBuilder authToken = new StringBuilder();

        HttpServletRequest httpServletRequest = null;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        }

        if (Objects.nonNull(httpServletRequest)) {
            authToken.append(httpServletRequest.getHeader("token"));
        }

        return authToken.toString();
    }
}
