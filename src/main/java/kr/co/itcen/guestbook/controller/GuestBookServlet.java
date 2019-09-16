package kr.co.itcen.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.guestbook.dao.GuestBookDao;
import kr.co.itcen.guestbook.vo.GuestBookVo;

public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("a");
		
		if("add".equals(action)) {
			String name =request.getParameter("name");
			String password = request.getParameter("password");
			String contents = request.getParameter("contents");
			
			GuestBookVo vo = new GuestBookVo();		
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(contents);	
			
			new GuestBookDao().insert(vo);
			
			response.sendRedirect(request.getContextPath()+"/gb");
		} else if("deleteform".equals(action)) {		    
			
		    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);			    
			
		} else if("delete".equals(action)) {
			String paramno =request.getParameter("no");
			String password = request.getParameter("password");
			Long no = Long.parseLong(paramno);
			
			GuestBookVo vo = new GuestBookVo();	
			vo.setNo(no);
			vo.setPassword(password);
			
			new GuestBookDao().delete(vo);
			
			response.sendRedirect(request.getContextPath()+"/gb");
		} else {
			/* index(list) */
			List<GuestBookVo> list = new GuestBookDao().getList();
			request.setAttribute("list", list);
			
			//forwarding 
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
