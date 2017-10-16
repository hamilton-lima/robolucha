package com.robolucha.models;

import com.robolucha.shared.Const;

public class GameElement  {

	private String canShow = Const.YES;

	@Override
	public String toString() {
		return "GameElement [canShow=" + canShow + "]";
	}

	public String getCanShow() {
		return canShow;
	}

	public void setCanShow(String canShow) {
		this.canShow = canShow;
	}


}
