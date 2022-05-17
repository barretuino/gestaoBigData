package com.components;
import java.util.*;
import java.sql.*;

// This is the bean that we use to store information from the database and to the jsp pages.
public class QueryBean implements java.io.Serializable
{
    
    private int numColumns;      // the number of columns for the particulaar query
	private Vector columnLabel;   // the names of the columns in the table
	private Vector columnTypeName; // of the column types of the tables
    private String query;         // the query
    private Vector results;      // the results from the results set are stored here
	private Vector tables;       // all the tables along with their column types
	private String dbName;      // the data source name

    // a constructor to initialize the private members
    public QueryBean()
    {
        columnLabel = new Vector();
		columnTypeName=  new Vector();
		results = new Vector();
		tables = new Vector();
		query = " ";
    }
   
   // set the dbName
    public void setDbName(String dbName)
	{
	 this.dbName = dbName;
	}

   // set the query
    public void setQuery(String query)
	{
	 this.query = query;
	}

    // set the number of columns
    public void setNumColumns(int numColumns)
    {
	 this.numColumns = numColumns;
	}
   
    // add a table with its columns int the vector
    public void setTables(String table)
	{
	 this.tables.add(table);
	}

    // add the results of each column of the query
    public void setResults(String result)
	{
	 this.results.add(result);
	}

    // add each column label
    public void setColumnLabel(String columnLabel)
    {
	 this.columnLabel.add(columnLabel);
	}

    // add each column type name for each column
    public void setColumnTypeName(String columnTypeName)
    {
	 this.columnTypeName.add(columnTypeName);
	}

    // get the data source name
    public String getDbName()
	{
	 return this.dbName;
	}

    // get the number of columns
	public int getNumColumns()
	{
	 return this.numColumns;
	}

    // get the vector with the column labels
	public Vector getColumnLabel()
	{
	 return this.columnLabel;
	}

    // get the vector wihh the results for each column
    public Vector getResults()
	{
	 return this.results;
	}

    // get the tables
    public Vector getTables()
	{
	 return this.tables;
	}

    // get the column type names
	public Vector getColumnTypeName()
	{
	 return this.columnTypeName;
	}

    // get the query
	public String getQuery()
	{
	 return this.query;
	}
}
    