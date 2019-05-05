package other;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class FontLoader {

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }

        UIManager.getLookAndFeelDefaults()
                .put("defaultFont", f);

        for (Map.Entry<Object, Object> entry : javax.swing.UIManager.getDefaults().entrySet()) {
            Object key = entry.getKey();
            Object value = javax.swing.UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
                javax.swing.UIManager.put(key, f);
            }
        }
    }
    public static javax.swing.plaf.FontUIResource Load() throws IOException, FontFormatException {
        File font_file = new File(System.getProperty("user.dir") + "\\" +
                "fonts/Yekan.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);

        javax.swing.plaf.FontUIResource f=new javax.swing.plaf.FontUIResource(font);
        return f;
    }
}
