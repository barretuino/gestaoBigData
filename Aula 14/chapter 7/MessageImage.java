package coreservlets;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/** Utilities for building images showing shadowed messages.
 *  <P>
 *  Requires JDK 1.4 since it uses the ImageIO class.
 *  JDK 1.4 is standard with J2EE-compliant app servers
 *  with servlets 2.4 and JSP 2.0. However, standalone
 *  servlet/JSP engines require only JDK 1.3 or later, and
 *  version 2.3 of the servlet spec requires only JDK
 *  1.2 or later. So, although most servers run on JDK 1.4,
 *  this code is not necessarily portable across all servers.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class MessageImage {
  
  /** Creates an Image of a string with an oblique
   *  shadow behind it. Used by the ShadowedText servlet.
   */

  public static BufferedImage makeMessageImage(String message,
                                               String fontName,
                                               int fontSize) {
    
    Font font = new Font(fontName, Font.PLAIN, fontSize);
    FontMetrics metrics = getFontMetrics(font);
    int messageWidth = metrics.stringWidth(message);
    int baselineX = messageWidth/10;
    int width = messageWidth+2*(baselineX + fontSize);
    int height = fontSize*7/2;
    int baselineY = height*8/10;
    BufferedImage messageImage =
      new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D)messageImage.getGraphics();
    g2d.setBackground(Color.white);
    g2d.clearRect(0, 0, width, height);
    g2d.setFont(font);
    g2d.translate(baselineX, baselineY);
    g2d.setPaint(Color.lightGray);
    AffineTransform origTransform = g2d.getTransform();
    g2d.shear(-0.95, 0);
    g2d.scale(1, 3);
    g2d.drawString(message, 0, 0);
    g2d.setTransform(origTransform);
    g2d.setPaint(Color.black);
    g2d.drawString(message, 0, 0);
    return(messageImage);
  }
  
  public static void writeJPEG(BufferedImage image,
                               OutputStream out) {
    try {
      ImageIO.write(image, "jpg", out);
    } catch(IOException ioe) {
      System.err.println("Error outputting JPEG: " + ioe);
    }
  }

  public static void writeJPEG(BufferedImage image,
                               File file) {
    try {
      ImageIO.write(image, "jpg", file);
    } catch(IOException ioe) {
      System.err.println("Error writing JPEG file: " + ioe);
    }
  }

  public static String[] getFontNames() {
    GraphicsEnvironment env =
      GraphicsEnvironment.getLocalGraphicsEnvironment();
    return(env.getAvailableFontFamilyNames());
  }
  
  /** We need a Graphics object to get a FontMetrics object
   *  (an object that says how big strings are in given fonts).
   *  But, you need an image from which to derive the Graphics
   *  object. Since the size of the "real" image will depend on
   *  how big the string is, we create a very small temporary
   *  image first, get the FontMetrics, figure out how
   *  big the real image should be, then use a real image
   *  of that size.
   */
  
  private static FontMetrics getFontMetrics(Font font) {
    BufferedImage tempImage =
      new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = (Graphics2D)tempImage.getGraphics();
    return(g2d.getFontMetrics(font));
  }
}
