package utils;

import java.io.File;


public class ImageLoadingPaths {
	private static ImageLoadingPaths instance;

	private String bfPath;
	private File bfDir;
	private String fpPath;
	private File fpDir;
	private boolean uiCheck;
	private File uiDir;
	private String uiToken;
	private String uiPath;
	private boolean cbCheck;
	private File cbDir;
	private String cbToken;
	private String cbPath;
	private boolean forcePath;

	public ImageLoadingPaths() {
		bfPath = "";
		fpPath = "";
		uiCheck = false;
		uiToken = "";
		uiPath = "";
		cbCheck = false;
		cbToken = "";
		cbPath = "";
		forcePath = true;

	}

	public static ImageLoadingPaths getInstance() {
		if (instance == null) {
			instance = new ImageLoadingPaths();
		}
		return instance;

	}

	public String getBfPath() {
		return bfPath;
	}

	public String getCbPath() {
		return cbPath;
	}

	public String getCbToken() {
		return cbToken;
	}

	public String getFpPath() {
		return fpPath;
	}

	public String getUiPath() {
		return uiPath;
	}

	public String getUiToken() {
		return uiToken;
	}

	public boolean isCbCheck() {
		return cbCheck;
	}

	public boolean isForcePath() {
		return forcePath;
	}

	public boolean isUiCheck() {
		return uiCheck;
	}

	public void setBfPath(String bfPath) {
		this.bfPath = bfPath;
	}

	public void setCbCheck(boolean cbCheck) {
		this.cbCheck = cbCheck;
	}

	public void setCbPath(String cbPath) {
		this.cbPath = cbPath;
	}

	public void setCbToken(String cbToken) {
		this.cbToken = cbToken;
	}

	public void setForcePath(boolean forcePath) {
		this.forcePath = forcePath;
	}

	public void setFpPath(String fpPath) {
		this.fpPath = fpPath;
	}

	public void setUiCheck(boolean uiCheck) {
		this.uiCheck = uiCheck;
	}

	public void setUiPath(String uiPath) {
		this.uiPath = uiPath;
	}

	public void setUiToken(String uiToken) {
		this.uiToken = uiToken;
	}

	public File getBfDir() {
		return bfDir;
	}

	public void setBfDir(File bfDir) {
		this.bfDir = bfDir;
	}

	public File getFpDir() {
		return fpDir;
	}

	public void setFpDir(File fpDir) {
		this.fpDir = fpDir;
	}

	public File getUiDir() {
		return uiDir;
	}

	public void setUiDir(File uiDir) {
		this.uiDir = uiDir;
	}

	public File getCbDir() {
		return cbDir;
	}

	public void setCbDir(File cbDir) {
		this.cbDir = cbDir;
	}

	
}
