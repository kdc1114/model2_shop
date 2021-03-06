package model2_shop.com.contoroller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model2_shop.com.dao.MemberDao;
import model2_shop.com.vo.MemberVo;
@WebServlet("/login.do")
public class MemberLogin extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		// login form 으로 이동
		req.getRequestDispatcher("/mem/login.jsp").forward(req, resp);
		// forward는 다른 view를 포함하는 한개의 페이지처럼 동작(url이 바뀌지 않음)
		// resp.sendRedirect(req.getContextPath()+"/mem/login.jsp");
		// url이 바뀌는 이동(다른페이지 요청)
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
		throws ServletException, IOException {
		// login form 에서 로그인 버튼을 누르면 실행되는 곳
		// 로그인 성공시 => 메인
		// 로그인 실패시 => 다시 로그인 페이지로 이동
		boolean login=false;
		String id=req.getParameter("id");
		String pw=req.getParameter("pw");
		System.out.println("id : "+id);
		System.out.println("pw : "+pw);
		// 1. 파라미터로 로그인 성공 실패와 아이디를 보낸다.
		// 2. 서버의 세션객체에 로그인 성공 실패와 아이디를 저장한다.
		MemberVo mem=null;
		MemberDao memDao=new MemberDao();
		
		try {
			mem=memDao.login(id, pw);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		};
		HttpSession session = req.getSession();
		
		if(mem!=null) { // 로그인 성공
			System.out.println(mem);
			session.setAttribute("id", mem.getId());
			session.setAttribute("name", mem.getName());
			session.setAttribute("grade", mem.getGrade());
			login=true;
		}else { // 로그인 실패
			session.setAttribute("id", id);
		};
		session.setAttribute("login", login);
		
		String redirect_page=req.getContextPath()+((login)?"/":"/login.do");
		resp.sendRedirect(redirect_page);
	};
};








