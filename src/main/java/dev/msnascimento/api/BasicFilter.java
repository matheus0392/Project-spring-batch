package dev.msnascimento.api;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class BasicFilter implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(BasicFilter.class);

	@Override
	public void doFilter(ServletRequest request, javax.servlet.ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		LOG.info("request : {}", req.getRequestURI());

		if (req.getRequestURI().startsWith("/WebApi")) {
			req.getRequestDispatcher(req.getRequestURI().substring(7, req.getRequestURI().length()))
			.forward(request, response);
			return;
		}

		chain.doFilter(request, response);
		/*
		 * LOG.info( "Committing a transaction for req : {}", req.getRequestURI());
		 */
	}

}