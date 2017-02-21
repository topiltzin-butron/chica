package xyz.cafeconleche.web.chica.dto;

import java.io.Serializable;

public class Face implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String eyes;
	private String nose;
	private String mouth;

	public String getEyes() {
		return eyes;
	}

	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	public String getNose() {
		return nose;
	}

	public void setNose(String nose) {
		this.nose = nose;
	}

	public String getMouth() {
		return mouth;
	}

	public void setMouth(String mouth) {
		this.mouth = mouth;
	}

	@Override
	public String toString() {
		return super.toString() + " eyes: " + eyes + ", nose: " + nose + ", mouth: " + mouth;
	}

}
