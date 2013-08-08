package com.steffenschroeder.jmines.gui;

import javax.swing.Icon;
import javax.swing.JButton;

class JButtonWithSameIconForEnabledAndDisabled extends JButton{
	private static final long serialVersionUID = 1L;

	@Override
	public void setIcon(Icon defaultIcon) {
		super.setIcon(defaultIcon);
		setDisabledIcon(defaultIcon);
	}
	
}