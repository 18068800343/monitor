package hs.bm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hs.bm.server.ShortMessageService;
import hs.bm.vo.ResObj;

public class BackUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BackUpServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type"); 
		ResObj ro = new ResObj();
		ro.setSuccess("fail");
		ro.setError(1);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
