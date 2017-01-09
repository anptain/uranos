package com.sanyin.uranos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.code.kaptcha.Constants;
import com.sanyin.uranos.exception.BadCaptchaException;

public class CaptchaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final static String FORM_CAPTCHA_KEY = "captcha";

	public CaptchaAuthenticationFilter() {
		setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (requiresAuthentication(request, response)) {
			String sessionCaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
			String captcha = request.getParameter(FORM_CAPTCHA_KEY);
			if (!StringUtils.equals(sessionCaptcha, captcha)) {
				unsuccessfulAuthentication(request, response, new BadCaptchaException("验证码认证失败"));
				return;
			}
		}
		chain.doFilter(request, response);
	}
}