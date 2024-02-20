package Phase3;
import java.io.*;
import java.util.*;
public class Manager {
	private ArrayList<Family> families = new ArrayList<>();
	
	public boolean addFamily(Family family) { // to add new member to the family
		int s=families.size();
		for(int i=0; i<s; i++) {
			if(families.get(i).getFamilyName().equals(family.getFamilyName())) return false; // to check if the family is exist before
		}
		families.add(family);
		return true;
	}
	
	public boolean deleteFamily(String familyName){
		int s=families.size();
		for(int i=0; i<s; i++) {
			if(families.get(i).getFamilyName().equals(familyName)) { // finding the family by name to remove it
				families.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean updateFamily(String familyName, Family updateFamily){ // to update a family information (family name or members information)
		int s=families.size();
		for(int i=0; i<s; i++) {
			if(families.get(i).getFamilyName().toLowerCase().equals(familyName.toLowerCase())) { // finding the family we want to update
				families.set(i, updateFamily);
				return true;
			}
		}
		return false;
	}
	
	public Family searchByName(String familyName) {
		int s=families.size();
		for(int i=0; i<s; i++) {
			if(families.get(i).getFamilyName().toLowerCase().equals(familyName.toLowerCase())) { // finding the family we search about by name to return it
				return families.get(i);
			}
		}
		return null;
	}
	
	public Person searchPersonById(String personId) {
		int s=families.size();
		for(int i=0; i<s; i++) {
			for(int j=0; j<families.get(i).getMembers().size(); j++) {
				if(families.get(i).getMembers().get(j).getID().equals(personId)) { // finding the person we search about by ID to return him
					return families.get(i).getMembers().get(j);
				}
			}
			for(int j=0; j<families.get(i).getParents().size(); j++) {
				if(families.get(i).getParents().get(j).getID().equals(personId)) { // finding the person we search about by ID to return him
					return families.get(i).getParents().get(j);
				}
			}
		}
		return null;
	}
	
	public int calculateTotalMartyrs() { // calculating the number of martyrs in the system
		int s=families.size(), res=0;
		for(int i=0; i<s; i++) {
			for(int j=0; j<families.get(i).getMembers().size(); j++) {
				if(families.get(i).getMembers().get(j) instanceof Martyr) { // to check if the person is a martyr
					res++;
				}
			}
			for(int j=0; j<families.get(i).getParents().size(); j++) {
				if(families.get(i).getParents().get(j) instanceof Martyr) { // to check if the person is a martyr
					res++;
				}
			}
		}
		return res;
		
	}
	
	public int calculateTotalOrphans() { // to calculate the number of orphans in the system 
		int s=families.size(), c, res=0;
		for(int i=0; i<s; i++) {
			c=0;
			for(int j=0; j<families.get(i).getParents().size(); j++) {
				if(families.get(i).getParents().get(j) instanceof Martyr) { // to check if the parent is a martyr
					c++;
				}
			}
			if(c==2) res+=families.get(i).getMembers().size(); // c=2 means that the two parents are martyrs, so the members except the parents are orphans
		}
		return res;
	}
	
	public int calculateTotalLivePersons() {
		int s=families.size(), res=0;
		for(int i=0; i<s; i++) {
			for(int j=0; j<families.get(i).getMembers().size(); j++) {
				if(families.get(i).getMembers().get(j) instanceof LivePerson) { // to check if the person is alive
					res++;
				}
			}
			for(int j=0; j<families.get(i).getParents().size(); j++) {
				if(families.get(i).getParents().get(j) instanceof LivePerson) { // to check if the person is alive
					res++;
				}
			}
		}
		return res;
	}
	
	public ArrayList<Integer> calculateFamilyStatistics(String familyName){
		Integer s=families.size(), live=0, mar=0, orphans=0, c=0;
		ArrayList<Integer> statistics = new ArrayList<>();
		for(int i=0; i<s; i++) {
			if(families.get(i).getFamilyName().equals(familyName)) {
				for(int j=0; j<families.get(i).getMembers().size(); j++) {
					if(families.get(i).getMembers().get(j) instanceof Martyr) mar++; // to check if the person is a martyr and increase their count
					else if(families.get(i).getMembers().get(j) instanceof LivePerson) live++; // to check if the person is alive and increase their count
				}
				for(int k=0; k<families.get(i).getParents().size(); k++) {
					if(families.get(i).getParents().get(k) instanceof Martyr) c++;
					else if(families.get(i).getParents().get(k) instanceof LivePerson) live++;
				}
				mar+=c;
				if(c==2) orphans = families.get(i).getMembers().size();// c=2 means that the two parents are martyrs, so the members except the parents are orphans
			}
		}
		statistics.add(mar);
		statistics.add(orphans);
		statistics.add(live);
		return statistics;
	}
	
	public ArrayList<Family> getFamilies() {
		return families;
	}

	public void setFamilies(ArrayList<Family> families) {
		this.families = families;
	}

	public ArrayList<Integer> calculateGlobalStatistics(){
		ArrayList<Integer> statistics = new ArrayList<>();
		statistics.add(calculateTotalMartyrs()); // adding the number of martyrs in the system to the list
		statistics.add(calculateTotalOrphans()); // adding the number of orphans in the system to the list 
		statistics.add(calculateTotalLivePersons()); // adding the number of live people in the system to the list
		return statistics;
	}
	
	public boolean uploadToFile(ArrayList<Family> fam) {   // uploading data to the file ordered by number of martyrs
		try {
			File f = new File("families.txt");
			PrintWriter output = new PrintWriter(f);
			for(int i=0; i<fam.size(); i++) {
				String name = fam.get(i).getFamilyName();
				output.println("Family "+(i+1)+": "+name+" ("+calculateFamilyStatistics(name).get(0)+") Martyrs");
				output.println("...................");
				output.print("Parents: ");
				for(int j=0; j<fam.get(i).getParents().size(); j++) {                    // write the parents names
					output.print(fam.get(i).getParents().get(j).getName()+" ");
				}
				output.print("\nMembers: ");
				for(int j=0; j<fam.get(i).getMembers().size(); j++) {                    // write the members names
					output.print(fam.get(i).getMembers().get(j).getName()+" ");
				}
				output.println("\n");
			}
			output.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	public Person readFromFile(String id) {                                    // reading a person from file
		Person p=null;
		try {
			File f = new File("data.txt");
			try (Scanner input = new Scanner(f)) {                             // to close the scanner automatically
				while(input.hasNext()) {
					String ID = input.next();
					String name = input.next();
					int age = input.nextInt();
					String gender = input.next();
					String role = input.next();
					String address = input.next();
					String contactInfo = input.next();
					String sit = input.next().toLowerCase();
					String date="", place="", cause="";
					if(sit.equals("martyr")) {
						date = input.next();
						place = input.next();
						cause = input.next();
					}
					if(id.equals(ID)) {
						if(sit.equals("martyr")) p = new Martyr(ID, name, role, age, gender, address, contactInfo, date, place, cause);
						else p = new LivePerson(ID, name, role, age, gender, address, contactInfo);
						break;
					}
				}
			}
			return p;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		String allFamilies="";
		for(int i=0; i<families.size(); i++) allFamilies += families.get(i).getFamilyName() +" ";
		
		return "The families names are: " + allFamilies; 
	}
}
