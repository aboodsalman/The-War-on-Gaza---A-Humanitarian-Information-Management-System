package Phase3;

public class NoParents extends Exception {
	private String myMsg = "";

	public NoParents(String userMsg) {
		myMsg = userMsg;
	}

	public NoParents() {
		myMsg = "You can't add a member without parents in the family.";
	}

	@Override
	public String toString() {
		return myMsg;
	}

	public String getMyMsg() {
		return myMsg;
	}

	public void setMyMsg(String myMsg) {
		this.myMsg = myMsg;
	}
}
