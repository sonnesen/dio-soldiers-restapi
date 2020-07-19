package digitalinnovation.example.restfull.core.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SSOFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	log.info("Chegou no portão do castelo");

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

	Map<String, String> mapHeaders = Collections.list(headerNames)
		.stream()
		.collect(Collectors.toMap(key -> key, httpServletRequest::getHeader));

	Optional<String> optional = Optional.ofNullable(mapHeaders.get("authorization"));

	if (optional.isPresent() && "53cr37".equals(optional.get())) {
	    chain.doFilter(request, response);
	} else {
	    log.info("Entrada não autorizada!");
	    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	    httpServletResponse.sendError(403);
	}

	log.info("Saiu do portão castelo");
    }

}
