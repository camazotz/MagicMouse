import java.io.IOException;

import java.io.OutputStreamWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

@WebServlet("/DoubleMeServlet")
public class DoubleMeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    double screenX=0, screenY=0;
    public DoubleMeServlet() {
        super();
 
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getOutputStream().println("Hurray !! This Servlet Works");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenX = screenSize.getWidth();
        screenY = screenSize.getHeight();
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//System.out.println("Conn");
        try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0 ;
            //System.out.println("befRead");
            //response.getOutputStream().println("before read");
            while ((c = sin.read(input, count, input.length-count)) != -1) {
            	//System.out.println("yo");
                count +=c;
            }
            sin.close();
            
            boolean isClick = false;
            String receivedString = new String(input);
            //System.out.println(receivedString);
            int robotX = 0, robotY = 0;
            if (!receivedString.equals("click")){
	            String[] coords = receivedString.split(" ", 2);
	            double xFrac = Double.parseDouble(coords[0]);
	            double yFrac = Double.parseDouble(coords[1]);
	            robotX = (int)(xFrac * screenX);
	            robotY = (int)(yFrac * screenY);
	            //System.out.println(robotX + " " + robotY);
            }
            else {
            	isClick = true;
            }
            try {
				Robot myMouse = new Robot();
				if (!isClick)
					myMouse.mouseMove(robotX, robotY);
				else{
					myMouse.mousePress(InputEvent.BUTTON1_MASK);
					myMouse.mouseRelease(InputEvent.BUTTON1_MASK);
				}
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
        } catch (IOException e) {
 
 
//            try{
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().print(e.getMessage());
//                response.getWriter().close();
//            } catch (IOException ioe) {}
        }   
        }
 
}