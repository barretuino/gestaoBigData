package coreservlets;

/** Utility with static method to build a URL for any
 *  of the most popular search engines.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class SearchUtilities {
  private static SearchSpec[] commonSpecs =
    { new SearchSpec("Google",
                     "http://www.google.com/search?q="),
      new SearchSpec("AllTheWeb",
                     "http://www.alltheweb.com/search?q="),
      new SearchSpec("Yahoo",
                     "http://search.yahoo.com/bin/search?p="),
      new SearchSpec("AltaVista",
                     "http://www.altavista.com/web/results?q="),
      new SearchSpec("Lycos",
                     "http://search.lycos.com/default.asp?query="),
      new SearchSpec("HotBot",
                     "http://hotbot.com/default.asp?query="),
      new SearchSpec("MSN",
                     "http://search.msn.com/results.asp?q="),
    };
  
  public static SearchSpec[] getCommonSpecs() {
    return(commonSpecs);
  }

  /** Given a search engine name and a search string, builds
   *  a URL for the results page of that search engine
   *  for that query. Returns null if the search engine name
   *  is not one of the ones it knows about.
   */
  
  public static String makeURL(String searchEngineName,
                               String searchString) {
    SearchSpec[] searchSpecs = getCommonSpecs();
    String searchURL = null;
    for(int i=0; i<searchSpecs.length; i++) {
      SearchSpec spec = searchSpecs[i];
      if (spec.getName().equalsIgnoreCase(searchEngineName)) {
        searchURL = spec.makeURL(searchString);
        break;
      }
    }
    return(searchURL);
  }
}
