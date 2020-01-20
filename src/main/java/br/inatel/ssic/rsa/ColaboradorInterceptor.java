package br.inatel.ssic.rsa;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ColaboradorInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String uri = request.getRequestURI();
		
		if(uri.endsWith("/") ||
				uri.contains("css") ||
				uri.contains("js") ||
				uri.contains("img") ||
				uri.contains("resources") ||
				uri.endsWith("/entrar")){
            return true;
        }
		
		if(request.getSession().getAttribute("colaboradorLogado") != null) {
			return true;
		}
		
		response.sendRedirect("/");
		return false;
	}
}
