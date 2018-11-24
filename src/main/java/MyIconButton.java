import javax.swing.*;
import java.awt.*;


public class MyIconButton extends JButton {

	private ImageIcon iconEnable, iconDisable;

	/**
	 * 构造方法
	 * @param iconNormal 默认图标
	 * @param iconEnable 激活图标
	 * @param iconDisable 失效图标
	 */
	public MyIconButton(ImageIcon iconNormal, ImageIcon iconEnable, ImageIcon iconDisable) {
		super(iconNormal);
		this.iconEnable = iconEnable;
		this.iconDisable = iconDisable;
		this.setSize(iconNormal.getIconWidth(),iconNormal.getIconHeight());

		initialize();
		setUp();
	}


	/**
	 * 初始化，设置按钮属性：无边，无焦点渲染，无内容区，各边距-3
	 */
	private void initialize() {
		this.setBorderPainted(false);
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusable(true);
		this.setMargin(new Insets(-3, -3, -3, -3));
	}


	/**
	 * 设置按钮图标：鼠标移过、按压、失效的图标 和设置按钮提示
	 */
	private void setUp() {
		this.setRolloverIcon(iconEnable);
		this.setPressedIcon(iconEnable);
		this.setDisabledIcon(iconDisable);
	}


}
