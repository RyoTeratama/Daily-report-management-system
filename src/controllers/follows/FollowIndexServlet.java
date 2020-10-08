package controllers.follows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndexServlet
 */
@WebServlet("/follows/index")
public class FollowIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Follow> follows = em.createNamedQuery("getAllFollowData", Follow.class)
                .setParameter("logid", login_employee.getId())
                .getResultList();

        //JPQL：checkLoginCode用のEmployee型の変数を定義
        // checkLoginCodeは１行のみの結果を返す
        Employee employee;
        //List型の変数を定義するときは以下のようにnew ArrayListで定義するのが良
        List<Employee> employees = new ArrayList<Employee>();
        List<Report> report = new ArrayList<Report>();
        List<Report> reports = new ArrayList<Report>();


        for(Follow f : follows){
                //以下JPQLは常に１行のみ返す（where = Employee.idのため）
        employee = em.createNamedQuery("checkLoginCode", Employee.class)
                    .setParameter("id", f.getFollowed_id() )
                    .getSingleResult();
                // １行のみの結果（Employee型）をList<Employee>型の変数に格納
                    employees.add(employee);
        }

        for(Employee e : employees){
        // 以下JPQLは複数の結果（Report型）を返すため、List<Report>で受ける。
        report = em.createNamedQuery("getMyAllReports", Report.class)
                    .setParameter("employee", e)
                    .getResultList();
        // List<Report>型の変数reportの中身を展開して、List<Report> reportsに格納し直す
        for(Report r : report){
            reports.add(r);
            }
        }



        em.close();


        request.setAttribute("reports", reports);
//        request.setAttribute("reports_count", reports_count);

        request.setAttribute("page", page);
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follows/index.jsp");
        rd.forward(request, response);
    }

}