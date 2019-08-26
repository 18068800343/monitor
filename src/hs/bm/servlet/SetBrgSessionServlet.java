package hs.bm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hs.bm.dao.BrgCardDao;
import hs.bm.vo.OperationConstruct;
import hs.bm.vo.ResObj;

public class SetBrgSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetBrgSessionServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		ResObj ro = new ResObj();
		if(type.equals("setBrgSession")){
			String id=request.getParameter("id");
			OperationConstruct opc=BrgCardDao.getInstance().getBrgInfo(id);
			request.getSession().setAttribute("OperationConstruct", opc);
			if(opc.getId()!=null){
				ro.setSuccess("success");
				ro.setObj(opc);
			}else{
				ro.setSuccess("error");
			}
			ro.ToJsp(response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
