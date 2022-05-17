import com.components.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Control extends HttpServlet
{
	Connection con = null;   // the connection to the database
	Statement stmt = null;   // a statement object for the queries
	String dbName;           // the database name

    private ServletContext context;   // objects used to transfer control to the jsp pages
    private RequestDispatcher dispatcher;

    /*Initializing the servlet*/
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
		context = config.getServletContext();
		
    }
 
     /*
     The doPost method handles POST requests
	 This method is accessed first in from the time the user will enter a datasource name in the main page.
	 the function will first enstablished a connection to the database with the requested data source name. if the data
     source does not exist then the control is tranfer to an error page with a corresponding message. if the database exist
	 then we save time by getting all the meta-data from the database ( table names, and columns) and storing them in the
	 bean. the bean is stored in the session object and control is transfered to the query page wich loads the two frames. One
	 for entering the query and one for results. This function is accessed only whenever we are in the main page.
	 */

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
	    ResultSet rsTables = null;    // resultset objects for the database tables and columns 
	    ResultSet rsColumns = null;

        DatabaseMetaData dbmd = null; // databse metadata object to access the meta-data from the database    
	    QueryBean qBean = new QueryBean(); // the bean object to store the information
        dbName = (String) req.getParameter("dbName"); // the database source name entered from the user
		qBean.setDbName(dbName);   // store the database name in the query bean so pages can diplayed it later.
        String url = "jdbc:odbc:" + dbName; 	/*Specifies the data source name*/
		System.out.println(dbName);
        String tableName;

		// the code below gets the database table names and columns and stores them in the bean
		try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con =DriverManager.getConnection(url,"",""); /*Sets up a connection with the particular driver*/
            HttpSession session = req.getSession(true);
            dbmd = con.getMetaData();
			rsTables = dbmd.getTables(null, null, null, null);
			while (rsTables.next())
            {
			    StringBuffer buff = new StringBuffer();
                tableName = rsTables.getString("TABLE_NAME");
	            if (rsTables.getString("TABLE_TYPE").equals("TABLE"))
				{
                    buff.append(tableName + " ");
		            rsColumns = dbmd.getColumns(null,null,tableName,null);
                    while (rsColumns.next())
                    {
                        buff.append(rsColumns.getString("COLUMN_NAME") + " ");
				    }
				    qBean.setTables(buff.toString());
			    }
			}
            session.setAttribute("qBean", qBean);  // put the bean in the session
            res.sendRedirect("/jspservlets/Query.jsp"); // transfer control to the query  page
        }

        catch (Exception e)
        {   // if there is an error with the database source transfer control to an error page
		    con = null;
			dispatcher = context.getRequestDispatcher("/errorDbSource.jsp");
			dispatcher.forward(req, res);
		}
   }

   /* The doGet method handles GET requests. This function is accesed every time we enter a query using the query form of
      the queryInput pages and also every time we want to display the contents of a table in the metadata window since we
	  actually perform a SELECT * FROM tableName statement. We display the results in the same page if there are select statements
	  to save code. There is only one difference. In the query frames with actually display the query itself along with the results
      but in the metadata we dont. Since we access only one method the doGet for both operations we determine at the beginning of
	  the function if we came from queryInput or from metaTables page. If we came from the queryInput page we perform the necessary
	  calculation and we put the results along with the query itself in a bean. if is a select statement we transfer control to the
	  QueryOutputProccess page where we display the query itself and a table with the results. If its an update, insert, or delete
	  we perform the operation but we transfer control in a different page the QueryOutputProccesUpdate where we output the result of
	  our statement. 
		If we come from the metadata then we perform the query SELECT * FROM tableName and we store the results in the bean as we did
	  before. Since we don't display the query itself from the metadata but we are using the same page we save to the query bean an
	  empty string.
	  In all the cases we check if the user has made an error by entering no data or bad data in the query window. If this is the case
	  we transfer control toa page where we display the appropriate message.
	  If the user has casued an SQL exception like selecting data from a table that does not exist, we get the corresponding message
	  of the exception and we transfer control to another page.
   */

   public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
	    String query; // the query 
	    ResultSet rs = null; // a resultset object that holds the results of the query
		QueryBean qBean = new QueryBean();   // a QueryBean object
        try
        { 
		    HttpSession  session = req.getSession(true);           //  get the session object
            stmt = con.createStatement();     // create a statement object

			String tableName = (String)req.getParameter("tableName"); // check to see if the user is coming from the metadata  
			if(tableName != null)                                     // by checking for a parameter tableName. If its not there
			{                                                         // the user is coming from the query frame.
			   query = "select * from " + tableName;
			   qBean.setQuery(" ");
			}
			else
			{
			   query =(String)req.getParameter("query");
			   qBean.setQuery(query);
			}
			System.out.println(query);
			boolean isQuery = false;//bollean value to know where to transfer control (QueryOutputProccess or QueryOutputProccessUpdate)
  
			if (checkQuery(query)) // check if the user hase entered a valid query (SELECT, INSERT, UPDATE, DELETE)
			   {
			       if ("SELECT".equalsIgnoreCase(query.substring(0,6)))//if is a select then get the resuts and store them in the bean
				   {
                       rs = stmt.executeQuery(query);
			   	       ResultSetMetaData rsmd = rs.getMetaData();
					   int numCols = rsmd.getColumnCount();
				       qBean.setNumColumns(numCols);
	                   isQuery = true;

                       for ( int i = 1; i <= numCols; i++)
                       {
						  qBean.setColumnLabel(rsmd.getColumnLabel(i));
						  qBean.setColumnTypeName(rsmd.getColumnTypeName(i));
                       }
		
                       while(rs.next())
                       {
                        for (int i = 1; i <= numCols; i++)
                            qBean.setResults(rs.getString(i));
                       }
		           }

				   else // if its not a SELECT that means is an INSERT, UPDATE OR DELETE. Perform the operation.
				   {
				       stmt.executeUpdate(query);
				   }
                               session.setAttribute("qBean", qBean); // put the bean back in the session

				   // transfer control to the correct jsp page. (is different if its a SELECT statement)
				   if(isQuery)
			          dispatcher = context.getRequestDispatcher("/QueryOutputProcess.jsp");
                   else
                      dispatcher = context.getRequestDispatcher("/QueryOutputProcessUpdate.jsp");
			       dispatcher.forward(req, res);
			   }
			   // If the user entered a null query or a bad query (other than SELECT, INSERT, UPDATE, DELETE) 
			   // then transfer control to an error page indicating the message.
			   else
			   {
			    if(query.length() == 0)   
                   dispatcher = context.getRequestDispatcher("/errorQuery.jsp?message=No SQL expression entered");
                else
				   dispatcher = context.getRequestDispatcher("/errorQuery.jsp?message=ERROR...Invalid SQL expression entered");
	           dispatcher.forward(req, res);    
			   }
	    }
		catch (SQLException e)
        {   // if there is an SQL exception get the exception's message and state and transfer control to an error page
	        dispatcher = context.getRequestDispatcher("/errorException.jsp?state=" + e.getSQLState() + "&message=" + e.getMessage());
			dispatcher.forward(req, res);
        }
        catch( Exception e)
    {
        System.out.println(e);
    }
    finally
    {
        if (rs != null) /* data in the result set */
        {
            try { rs.close(); }
            catch(Exception ex) {}
            rs = null;
        }
    }
 
}

// method for checking if the user hase enter a valid query (SELECT, INSERT, UPDATE, DELETE)
public boolean checkQuery(String query)
{ 
    boolean temp = false;
	if(query.length() > 0 && query.length() > 5)
	{
	    if(("SELECT".equalsIgnoreCase(query.substring(0,6))) ||
		   ("INSERT".equalsIgnoreCase(query.substring(0,6))) ||
		   ("UPDATE".equalsIgnoreCase(query.substring(0,6)))   ||
		   ("DELETE".equalsIgnoreCase(query.substring(0,6))))
               temp = true;
    }
    return temp;
}
}
