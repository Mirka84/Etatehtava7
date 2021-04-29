package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.util.ArrayList; 
import model.dao.AsiakasDao;
import model.Asiakas2;


@WebServlet("/asiakkaat2/*")
public class Asiakkaat2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Asiakkaat2() {
        super();
        System.out.println("asiakkaat2.asiakkaat2()"); 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		System.out.println("asiakkaat2.doGet");
		String pathInfo = request.getPathInfo();				
		System.out.println("polku: "+pathInfo);	
		AsiakasDao dao=new AsiakasDao(); 
		ArrayList<Asiakas2> asiakkaat;
		String strJSON="";
		if(pathInfo==null) {	
			asiakkaat=dao.listaaKaikki(); 
			strJSON=new JSONObject().put("asiakkaat2", asiakkaat).toString(); 
		}else if(pathInfo.indexOf("haeyksi")!=-1) {
			int asiakas_id=Integer.parseInt(pathInfo.replace("/haeyksi/", ""));
			Asiakas2 asiakas=dao.etsiAsiakas(asiakas_id); 
			JSONObject JSON=new JSONObject();
			JSON.put("asiakas_id", asiakas.getAsiakas_id());
			JSON.put("etunimi", asiakas.getEtunimi());
			JSON.put("sukunimi", asiakas.getSukunimi());
			JSON.put("puhelin", asiakas.getPuhelin());
			JSON.put("sposti", asiakas.getSposti()); 
			strJSON=JSON.toString(); 
		}else {
			String hakusana=pathInfo.replace("/", "");
			asiakkaat=dao.listaaKaikki(hakusana);
			strJSON=new JSONObject().put("asiakkaat2", asiakkaat).toString();
		}
	
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strJSON);		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("asiakkaat2.doPost"); 
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		Asiakas2 asiakas=new Asiakas2(); 
		asiakas.setAsiakas_id(jsonObj.getInt("asiakas_id"));
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		AsiakasDao dao=new AsiakasDao(); 
		if(dao.lisaaAsiakas(asiakas)) {
			out.println("{\"response\":1}"); 
		} else {
			out.println("{\"response\":0}");
		}
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asiakkaat2.doPut"); 
		JSONObject jsonObj = new JsonStrToObj().convert(request);
		int asiakas_id=jsonObj.getInt("asiakas_id"); 
		Asiakas2 asiakas=new Asiakas2(); 
		asiakas.setAsiakas_id(jsonObj.getInt("asiakas_id"));
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		AsiakasDao dao=new AsiakasDao(); 
		if(dao.muutaAsiakas(asiakas, asiakas_id)) {
			out.println("{\"response\":1}"); 
		} else {
			out.println("{\"response\":0}");
		}
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asiakkaat2.doDelete()"); 
		String pathInfo=request.getPathInfo(); 
		System.out.println("polku:" +pathInfo); 
		String poistettavaAsiakas_id=pathInfo.replace("/", "");
		int poistettavaID=Integer.parseInt(poistettavaAsiakas_id); 
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		AsiakasDao dao=new AsiakasDao(); 
		if(dao.poistaAsiakas(poistettavaID)) {
			out.println("{\"response\":1}"); 
		} else {
			out.println("{\"response\":0}");
		}
	}
}
