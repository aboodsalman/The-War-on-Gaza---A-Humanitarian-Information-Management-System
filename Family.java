package Phase3;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class Family implements Sortable, Cloneable, Comparable<Object>{
	private String familyName;
	private ArrayList<Person> members = new ArrayList<>();
	private ArrayList<Person> parents = new ArrayList<>();
	
	public Family(String name) {
		familyName=name;
	}
	
	public Family() {}
	
	public boolean addMember(Person member, String role) throws NoParents{ // adding new member to the family
		if(!role.toLowerCase().equals("son") && !role.toLowerCase().equals("daughter")) {
			return false;
		}
		if(member.getRole().toLowerCase().equals("son") && member.getGender().toLowerCase().equals("female")) { // matching gender and role
			System.out.println("Sorry, the dad can't be female");
			return false;
		}
		if(member.getRole().toLowerCase().equals("daughter") && member.getGender().toLowerCase().equals("male")) {  // matching gender and role
			System.out.println("Sorry, the mom can't be male");
			return false;
		}
		if(parents.size()<2) throw new NoParents("You can't add a member without parents int the family");  // there's no parents exception
		if(parents.get(0).getAge()-member.getAge()<17 || parents.get(1).getAge()-member.getAge()<17) {      // difference between parents and members ages
			System.out.println("Sorry, the least allowed difference between a son/daughter and his parents is 17 years");
			return false;
		}
		writeToFile(member);
		members.add(member);
		return true;
	}
	
	public boolean removeMember(Person member) { // removing a specific member from the family
		int s=members.size();
		for(int i=0; i<s; i++) {
			if(members.get(i).getID().equals(member.getID())) { // to check if the person is exist in this family
				members.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Person> getMembers(){
		return members;
	}
	
	public void addParents(Person parent) {
		for(int i=0; i<parents.size(); i++) {
			if(parents.get(i).getRole().toLowerCase().equals(parent.getRole().toLowerCase())) {
				System.out.println("Sorry, there's a "+parent.getRole().toLowerCase()+" for this family");
				return;
			}
		}
		
		for(int i=0; i<members.size(); i++) {
			if(parent.getAge()-members.get(i).getAge()<17) {  // difference between parents and members ages
				System.out.println("Sorry, the least allowed difference between a son/daughter and his parents is 17 years");
				return;
			}
		}
		if(parent.getAge()<16) {
			System.out.println("Sorry, the parents age can't be less than 16");
			return;
		}
		if(parent.getRole().toLowerCase().equals("dad") && parent.getGender().toLowerCase().equals("female")) { // matching gender and role
			System.out.println("Sorry, the dad can't be female");
			return;
		}
		if(parent.getRole().toLowerCase().equals("mom") && parent.getGender().toLowerCase().equals("male")) { // matching gender and role
			System.out.println("Sorry, the mom can't be male");
			return;
		}
		parents.add(parent);
		writeToFile(parent);
		System.out.println("The parent is added successfully");
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public boolean removeParent(Person parent) {
		int s=parents.size();
		for(int i=0; i<s; i++) {
			if(parents.get(i).getID().equals(parent.getID())) { // to check if the parent is exist in this family
				parents.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Person> getParents(){
		return parents;
	}
	
	
	
	@Override
	public String toString() {
		return "Family [familyName=" + familyName + ", members=" + members + ", parents=" + parents + "]";
	}
	
	
	public ArrayList<Family> sortByMartyrs(ArrayList<Family> families){  // sorting by martyrs method
		
		ArrayList<Family> families1 = new ArrayList<>();
		
			try {                                                                              // to make a copy of the family
				for(int i=0; i<families.size(); i++) families1.add((Family) families.get(i).clone());
			} catch (CloneNotSupportedException e) {
				System.out.println("Sorry, something wrong occured");
				return null;
			}
		Collections.sort(families1);                                                 // sorting according to compare to method
		return families1;
	}
	
	public ArrayList<Family> sortByOrphans(ArrayList<Family> families){    // sorting by orphans method
		ArrayList<Family> families1 = new ArrayList<>();
		Family family;
		int max=0;
		try {
			for(int i=0; i<families.size(); i++) families1.add((Family) families.get(i).clone());  // to make a copy of the family
		} catch (CloneNotSupportedException e) {
			System.out.println("Sorry, something wrong occured");
			return null;
		}
		ArrayList<Integer> orphans = new ArrayList<>();
		for(int i=0; i<families1.size(); i++) {                            // selection sort algorithm
			int o=0;
			for(int j=0; j<families1.get(i).parents.size(); j++) {
				if(families1.get(i).parents.get(j) instanceof Martyr) o++;
			}
			if(o==2) orphans.add(families1.get(i).members.size());
			else orphans.add(0);
		} 
		for(int i=0; i<families1.size(); i++) {
			int num= orphans.get(i), index=i;
			max = orphans.get(i);
			family = families1.get(i);
			for(int k=i+1;k<orphans.size();k++) {
				if(max < orphans.get(k)) {
					max = orphans.get(k);
					index=k;
				}
			}
			orphans.set(i, orphans.get(index));
			orphans.set(index, num);
			families1.set(i, families1.get(index));
			families1.set(index, family);
		}
		return families1;
	}
	
	public Family deep() {                                // deep copy method
		Family f = new Family();
		f.setFamilyName(this.getFamilyName());
		for(int i=0; i<this.members.size(); i++) {              // making a deep copy from every member
			if(this.members.get(i) instanceof Martyr) {
				f.members.add(((Martyr)this.members.get(i)).deep());
			}
			else f.members.add(((LivePerson)this.members.get(i)).deep());
		}
		for(int i=0; i<this.parents.size(); i++) {                     // making a deep copy from every parent
			if(this.parents.get(i) instanceof Martyr) {
				f.parents.add(((Martyr)this.parents.get(i)).deep());
			}
			else f.parents.add(((LivePerson)this.parents.get(i)).deep());
		}
		return f;
	}
	
	public void writeToFile(Person p) {                                    // writing data to file
		ArrayList<String> data = new ArrayList<>(), ids = new ArrayList<>();
		try {
			File f = new File("data.txt");
			try (Scanner input = new Scanner(f)) {                          // to close the scanner automatically
				while(input.hasNext()) {
					String id = input.next();
					data.add(id);
					ids.add(id);
					data.add(input.next());
					data.add(input.next());
					data.add(input.next());
					data.add(input.next());
					data.add(input.next());
					data.add(input.next());
					String sit = input.next().toLowerCase();
					data.add(sit);
					if(sit.equals("martyr")) {
						data.add(input.next());
						data.add(input.next());
						data.add(input.next());
					}
				}
			}
			try (PrintWriter pw = new PrintWriter(f)) {
				for(int i=0; i<data.size(); i++) {
					pw.println(data.get(i));
				}
				String id = p.getID();
				if(ids.contains(id)==false) {
					pw.println(p.getID());
					pw.println(p.getName());
					pw.println(p.getAge());
					pw.println(p.getGender());
					pw.println(p.getRole());
					pw.println(p.getAddress());
					pw.println(p.getContactInfo());
					if(p instanceof Martyr) {
						pw.println("martyr");
						pw.println(((Martyr)p).getDateOfMartyrdom());
						pw.println(((Martyr)p).getPlaceOfDeath());
						pw.println(((Martyr)p).getCauseOfDeath());
					}
					else pw.println("live");
				}
			}
		} catch (FileNotFoundException e) {                                    // there's an exception
			System.out.println("Sorry, something wrong occured");
		}
	}
	
	@Override
	public int compareTo(Object f) {
		if(this.equals(f)) return 0;
		int c1=0, c2=0;
		for(int i=0; i<this.members.size(); i++) if(this.members.get(i) instanceof Martyr) c1++; // count the number of martyrs in the first family
		for(int i=0; i<this.parents.size(); i++) if(this.parents.get(i) instanceof Martyr) c1++;
		for(int i=0; i<((Family)f).members.size(); i++) if(((Family)f).members.get(i) instanceof Martyr) c2++; // count the number of martyrs in the second family
		for(int i=0; i<((Family)f).parents.size(); i++) if(((Family)f).parents.get(i) instanceof Martyr) c2++;
		if(c1>c2) return -1;
		if(c1<c2) return 1;
		return 0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone(); 
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Family) {
			int s1=members.size(), s2= ((Family)obj).members.size(), c1=0, c2=0;
			for(int i=0; i<s1; i++) if(this.members.get(i) instanceof Martyr) c1++; // count the number of martyrs in the first family
			for(int i=0; i<this.parents.size(); i++) if(this.parents.get(i) instanceof Martyr) c1++;
			for(int i=0; i<s2; i++) if(((Family)obj).members.get(i) instanceof Martyr) c2++; // count the number of martyrs in the second family
			for(int i=0; i<((Family)obj).parents.size(); i++) if(((Family)obj).parents.get(i) instanceof Martyr) c2++;
			return c1==c2; // return true if they are equal
		}
		return false;
	}

}
