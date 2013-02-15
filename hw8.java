import java.io.*;
import java.io.StringReader;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;






  public class hw8 extends HttpServlet {


    public void doGet(HttpServletRequest request,HttpServletResponse response)
        throws IOException, ServletException
    {
        
        response.setContentType("application/json; charset:utf-8");
        request.setCharacterEncoding("UTF-8");
		
		String title_val=request.getParameter("title");
		String tt_val=request.getParameter("title_type");
		JSONObject j_results = new JSONObject();
		
		PrintWriter out1 = response.getWriter();
	 try {	
		String value1= request.getParameter("title");
		String value2= request.getParameter("title_type");
		
		
	//	 WORKING JUST FINE  EXPECT MARY 
			String data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(value1, "UTF-8");
        data += "&" + URLEncoder.encode("title_type", "UTF-8") + "=" + URLEncoder.encode(value2, "UTF-8");
		String url_php="http://cs-server.bnm.edu:20212/hw8p.php?"+data;

        URL url = new URL(url_php);
        URLConnection conn = url.openConnection();
		conn.setAllowUserInteraction(false);
        conn.setDoOutput(true);
       
	   
	   
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        String line1,line2="";
        while ((line1 = rd.readLine()) != null)
		{
			line2+=line1;
        
        }
		
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(line2));
		
		JSONObject j_result = new JSONObject();
		
		
	
			
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = db.parse(is);
			NodeList games = doc.getElementsByTagName("result");
			
			for (int i = 0; i < games.getLength(); i++) 
			{
			  Node aNode = games.item(i);
			  NamedNodeMap attributes = aNode.getAttributes();
				
			JSONObject j_inresult = new JSONObject();
			  for (int a = 0; a < attributes.getLength(); a++) 
			  {
				
				Node theAttribute = attributes.item(a);
				
				j_inresult.put(theAttribute.getNodeName(),theAttribute.getNodeValue());
				//System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
			  }
			  j_result.accumulate("result",j_inresult);
			}
			j_results.put("results",j_result);
			
		}
		catch(MalformedURLException m)
		{
			out1.println("mexp");
			return;
		}
		catch(IOException ioo)
		{
			out1.println("ioexp");
			return;
		
		}
		catch(Exception e)
		{
			out1.println("error");
			return;
		}
		out1.println( j_results.toString(2) );

		
    }
}
