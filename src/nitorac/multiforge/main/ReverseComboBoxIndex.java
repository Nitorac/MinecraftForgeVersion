package nitorac.multiforge.main;

public class ReverseComboBoxIndex {
	
	private int size;
	
	public ReverseComboBoxIndex(int size){
		this.size=size;
	}

	public int invertedItem(int itemIndex){
		return Math.abs(itemIndex-(size-1));
	}
}
