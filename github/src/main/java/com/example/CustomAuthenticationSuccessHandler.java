package com.example;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

/**
 * Created by rashmitr on 2/7/2017.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	public final Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 120;

	@Value("${github.client.redirect-uri}")
	private StringBuffer redirectURI;

	private StringBuffer newRedirectURI;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		System.out.println("**** Inside onAuthenticationSuccess ");
		request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);
		handle(request, response, authentication);

	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		newRedirectURI = new StringBuffer(redirectURI);
		System.out.println("Redirecting to : "+redirectURI);
		redirectStrategy.sendRedirect(request, response, newRedirectURI.toString());
	}
}
