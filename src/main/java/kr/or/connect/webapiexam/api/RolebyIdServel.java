package kr.or.connect.webapiexam.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.connect.jdbcexam.dao.RoleDao;
import kr.or.connect.jdbcexam.dto.Role;

/**
 * Servlet implementation class RolebyIdServel
 */
@WebServlet("/roles/*")
public class RolebyIdServel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RolebyIdServel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		String pathInfo = request.getPathInfo();
		String[] pathParts = pathInfo.split("/");
		String idSt = pathParts[1];
		int id = Integer.parseInt(idSt);
		
		RoleDao dao = new RoleDao();
		Role role = dao.getRole(id);
		
		ObjectMapper objectmapper = new ObjectMapper();
		String json = objectmapper.writeValueAsString(role);
		PrintWriter out = response.getWriter();
		
		out.println(json);
		out.close();
	}

}
