package Phase3;

public class LivePerson extends Person implements Cloneable {
	
	public LivePerson() {}
	public LivePerson(String iD, String name, String role, int age, String gender, String address, String contactInfo) {
		super(iD, name, role, age, gender, address, contactInfo);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone(); 
    }
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public LivePerson deep() {
		LivePerson p = new LivePerson();
		p.setID(this.getID());
		p.setName(this.getName());
		p.setRole(this.getRole());
		p.setAge(this.getAge());
		p.setGender(this.getGender());
		p.setAddress(this.getAddress());
		p.setContactInfo(this.getContactInfo());
		return p;
	}
	
}
