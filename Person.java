package Phase3;

public abstract class Person {
	private String ID;
	private String Name;
	private int Age;
	private String Gender;
	private String Address;
	private String ContactInfo;
	private String Role;
	
	public Person() {}
	public Person(String iD, String name, String role, int age, String gender, String address, String contactInfo) {
		ID = iD;
		Name = name;
		Age = age;
		Role = role;
		Gender = gender;
		Address = address;
		ContactInfo = contactInfo;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getContactInfo() {
		return ContactInfo;
	}
	public void setContactInfo(String contactInfo) {
		ContactInfo = contactInfo;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone(); 
    }
	
	@Override
	public String toString() {  // returns information about a specific person
		return "Person [ID=" + ID + ", Name=" + Name + ", Age=" + Age + ", Gender=" + Gender + ", Address=" + Address
				+ ", ContactInfo=" + ContactInfo + "]";
	}
	
	
}
