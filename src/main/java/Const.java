
import javax.swing.*;
import java.awt.*;


/**
 * UI相关的常量
 *
 * @author R
 */
class Const {
    //  0.3937008 * 72*6 = 170
    //  0.3937008 * 72*4 = 113;
    static int PAPER_WIDTH = 170;
    static int PAPER_HEIGHT = 113;

    static int LABEL_HEIGHT = 15;
    static int LABEL_WIDTH = 60;
    static int TOP_MARGIN = 20;

//    final static double PAPER_WIDTH = 240;
//    final static double PAPER_HEIGHT = 160;


    // 系统常用字体
//    public final static Font FONT_YAHEI_12 = new Font("微软雅黑", Font.PLAIN, 12);
    final static Font SONG_9 = new Font("宋体", Font.PLAIN, 9);

    //主窗口背景色
//    public final static Color GREEN = new Color(37, 174, 96);
//    public final static Color GREEN_ACTIVE = new Color(106, 200, 146);
//    public final static Color GREEN_LIGHT = new Color(166, 237, 198);


    //主图标
//    public final static ImageIcon ICON_APP = new ImageIcon(MainApp.class.getResource("/icon/app.png"));

    //按钮 图标
    // 解析
    public final static ImageIcon ICON_RESOLVE = new ImageIcon(MainApp.class.getResource("/解析.png"));
    public final static ImageIcon ICON_RESOLVE_ENABLED = new ImageIcon(MainApp.class.getResource("/解析-选中.png"));

    // 打印
    public final static ImageIcon ICON_PRINT = new ImageIcon(MainApp.class.getResource("/打印.png"));
    public final static ImageIcon ICON_PRINT_ENABLED = new ImageIcon(MainApp.class.getResource("/打印-选中.png"));

}
