package Phase3;

import java.io.*;

public class Martyr extends Person implements Cloneable {
	private String DateOfMartyrdom;
	private String CauseOfDeath;
	private String PlaceOfDeath;

	public Martyr() {
	}

	public Martyr(String iD, String name, String role, int age, String gender, String address, String contactInfo,
			String dateOfMartyrdom, String causeOfDeath, String placeOfDeath) {
		super(iD, name, role, age, gender, address, contactInfo);
		DateOfMartyrdom = dateOfMartyrdom;
		CauseOfDeath = causeOfDeath;
		PlaceOfDeath = placeOfDeath;
	}

	public Martyr(String name, String date) {
		this.setName(name);
		this.DateOfMartyrdom = date;
	}

	public String getDateOfMartyrdom() {
		return DateOfMartyrdom;
	}

	public void setDateOfMartyrdom(String dateOfMartyrdom) {
		DateOfMartyrdom = dateOfMartyrdom;
	}

	public String getCauseOfDeath() {
		return CauseOfDeath;
	}

	public void setCauseOfDeath(String causeOfDeath) {
		CauseOfDeath = causeOfDeath;
	}

	public String getPlaceOfDeath() {
		return PlaceOfDeath;
	}

	public void setPlaceOfDeath(String placeOfDeath) {
		PlaceOfDeath = placeOfDeath;
	}

	public Martyr deep() {
		Martyr p = new Martyr();
		p.setID(this.getID());
		p.setName(this.getName());
		p.setRole(this.getRole());
		p.setAge(this.getAge());
		p.setGender(this.getGender());
		p.setAddress(this.getAddress());
		p.setContactInfo(this.getContactInfo());
		p.DateOfMartyrdom = this.DateOfMartyrdom;
		p.CauseOfDeath = this.CauseOfDeath;
		p.PlaceOfDeath = this.PlaceOfDeath;
		return p;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void writeToFile() {         // writing data to the binary file
		try (FileOutputStream output = new FileOutputStream("MartyrsListtt.dat", true);
				DataOutputStream data = new DataOutputStream(output)) {
			data.writeUTF(this.getName()+" "+this.getDateOfMartyrdom());               // Name Date-Of-Martyrdom
			data.writeChar('\n');                                                     // new Line in the file

		} catch (IOException e) {
			System.out.println("Sorry, something went wrong");
		}
	}
	


	@Override
	public String toString() {
		return super.toString() + "\n[DateOfMartyrdom=" + DateOfMartyrdom + ", CauseOfDeath=" + CauseOfDeath
				+ ", PlaceOfDeath=" + PlaceOfDeath + "]";
	}

}