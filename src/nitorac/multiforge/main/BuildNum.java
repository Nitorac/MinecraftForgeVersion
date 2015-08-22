package nitorac.multiforge.main;

public class BuildNum {
	private int num1;
	private int num2;
	private int num3;
	private int num4;
	private String buildStr;
	
	public BuildNum(int num1, int num2, int num3, int num4){
		this.setNum1(num1);
		this.setNum2(num2);
		this.setNum3(num3);
		this.setNum4(num4);
		this.setBuildStr(String.format("%d.%d.%d.%d", num1, num2, num3, num4));
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int getNum3() {
		return num3;
	}

	public void setNum3(int num3) {
		this.num3 = num3;
	}

	public int getNum4() {
		return num4;
	}

	public void setNum4(int num4) {
		this.num4 = num4;
	}

	public String toString(){
		return buildStr;
	}

	public void setBuildStr(String buildStr) {
		this.buildStr = buildStr;
	}
	
	public void parse(String str){
		try{
			String[] temp = str.split("\\.");
			this.num1 = Integer.parseInt(temp[0]);
			this.num2 = Integer.parseInt(temp[1]);
			this.num3 = Integer.parseInt(temp[2]);
			this.num4 = Integer.parseInt(temp[3]);
			this.setBuildStr(String.format("%d.%d.%d.%d", num1, num2, num3, num4));
		}catch(Exception e){
			
		}
	}
}
