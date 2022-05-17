package coreservlets;

/** Small class that encapsulates how to construct a
 *  search string for a particular search engine.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class SearchSpec {
  private String name, baseURL;

  public SearchSpec(String name,
                    String baseURL) {
    this.name = name;
    this.baseURL = baseURL;
  }

  /** Builds a URL for the results page by simply concatenating
   *  the base URL (http://...?someVar=") with the URL-encoded
   *  search string (jsp+training).
   */
  
  public String makeURL(String searchString) {
    return(baseURL + searchString);
  }

  public String getName() {
    return(name);
  }
}
