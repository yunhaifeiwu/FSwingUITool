import java.io.IOException;  
import java.io.PrintWriter;  
import java.io.StringWriter;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.logging.FileHandler;  
import java.util.logging.Formatter;  
import java.util.logging.Level;  
import java.util.logging.LogRecord;  
import java.util.logging.Logger;  
  
public class LogTest  
{  
    public static void main(String[] args) throws IOException  
    {  
        Logger log = Logger.getLogger(LogTest.class.getName());  
        log.setLevel(Level.INFO);  
          
        FileHandler fileHandler = new FileHandler("E:/testlog%g.log", true);  
        fileHandler.setLevel(Level.SEVERE);  
        fileHandler.setFormatter(new Formatter()  
                                 {  
                                    public String format(LogRecord record)  
                                    {  
                                        SimpleDateFormat sd = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");    
                                        String d = sd.format(new Date());    
                                        return d + record.getLevel() + ":" + record.getMessage() + "/n";  
                                    }  
                                 });  
        log.addHandler(fileHandler);  
        try  
        {  
            throw new Exception("ldddddddd");  
        }  
        catch (Exception e)  
        {  
            StringWriter sw = new StringWriter();  
            e.printStackTrace(new PrintWriter(sw));  
            log.severe(sw.toString());  
        }  
        log.info("aaa");  
    }  
} 