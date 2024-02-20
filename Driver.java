package Phase3;

import java.util.*;

public class Driver {

	public static void main(String[] args) {
		Manager manager = new Manager();
		Scanner scan = new Scanner(System.in);

		int choice;

		do {
			System.out.println(
					"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
							+ "Choose an operation from 1 to 16:\n1. Add a family.\n2. Add a family member.\n3. Add parent.\n4. Remove a family member.\n"
							+ "5. Remove parent.\n6. Delete a family.\n7. Compare number of martyrs between two families.\n8. Update a family."
							+ "\n9. Search about a person by ID.\n10. Search about a family by name.\n11. Calculate the glopal statistics."
							+ "\n12. Calculate a specific family statistics.\n13. Sort families.\n14. Upload families data to the file. \n15."
							+ " Copy an object of Martyr, LivePerson or Family.\n16. Exit.");
			choice = 0;
			boolean complete = true;
			while (complete) {                             // to try input again if the input is invalid
				try {
					System.out.print("Enter Your Choice: ");
					choice = scan.nextInt();
					complete = false;
				} catch (InputMismatchException ex) {
					System.out.println("Try again. (Invalid input: an integer is required)");
					scan.nextLine();
				}
			}

			switch (choice) {
			case 1:
				System.out.print("Please enter the family name: ");
				String name = scan.next();
				if (manager.addFamily(new Family(name))) { // check if the family is added successfully to the system
					System.out.println(
							"The family is added successfully, if you want to add members and parents, use choices 2 & 3");
				} else
					System.out.println("This family is exist before");
				break;
			// ----------------------------------------------------------------------------------------------------------------------
			case 2:
				complete = true;
				int num = 0;
				while (complete) {                                  // to try input again if the input is invalid
					try {
						System.out.print("Enter 1 to read data from console, 2 from the file: ");
						num = scan.nextInt();
						complete = false;
						if(num!=1 && num!=2) {                      // the input is just 1 or 2
							System.out.println("Invalid input");
							complete=true;
						}
					} catch (InputMismatchException ex) {
						System.out.println("Try again. (Invalid input: an integer is required)");
						scan.nextLine();                                           // clear the scanner buffer
					}
				}
				System.out.print("Please enter the family name you want to add to: ");
				String familyName = scan.next();
				Family f = manager.searchByName(familyName);
				if (f == null) { // to check if the family isn't exist
					System.out.println("Sorry, the family isn't found");
					break;
				}
				if (num == 2) {
					System.out.print("Enter the ID of the person: ");
					String id = scan.next();
					if (manager.readFromFile(id) != null) {                                  // search about the person in the file
						if (manager.searchPersonById(manager.readFromFile(id).getID()) != null) {
							System.out.println("Sorry, the member is added before");
						} else
							try {
								if (f.addMember(manager.readFromFile(id), manager.readFromFile(id).getRole())) {
									System.out.println("The member is added successfully");
								} else
									System.out.println("The member isn't added successfully");
							} catch (NoParents e) {                                       // theres no parents exception
								System.out.println(e);
							}
						break;
					} else
						System.out.println("Sorry, the member is not found in the file");
				}
				System.out.print("Please enter the members information \nID: ");
				String id = scan.next();
				System.out.print("Name: ");
				name = scan.next();
				int age = 0;
				complete = true;
				while (complete) {                                         // to try input again if the input is invalid
					try {
						System.out.print("Age: ");
						age = scan.nextInt();
						complete = false;
						if (age < 0 || age > 120) {                        // the age should be between 0 and 120
							System.out.println("Sorry, age should be between 0 and 120 years");
							complete = true;
							scan.nextLine();
						}
					} catch (InputMismatchException ex) {
						System.out.println("Try again. (Invalid input: an integer is required)");
						scan.nextLine();
					}
				}
				complete = true;
				String gender = "";
				while (complete) {
					System.out.print("Gender (Male or Female): ");
					gender = scan.next();
					complete = false;
					if (!gender.toLowerCase().equals("male") && !gender.toLowerCase().equals("female")) {
						System.out.println("Sorry, the gender should be just male or female");
						complete = true;
					}
				}
				System.out.print("Address: ");
				String address = scan.next();
				System.out.print("Contact information using this syntax (Email_PhoneNumber): ");
				String contact = scan.next();
				System.out.print("Role: ");
				String role = scan.next();
				System.out.print("Situation (M for martyrs and L for live people): ");
				char sit = scan.next().charAt(0);
				if (manager.searchPersonById(id) != null) { // check if the member is added before in the system
					System.out.println("The member is added before");
					break;
				}
				if (sit == 'M' || sit == 'm') { // to check if the person is a martyr to input date, place and cause of
												// martyrdom
					System.out.print("Enter the date of martyrdom: ");
					String date = scan.next();
					System.out.print("Place of martyrdom: ");
					String place = scan.next();
					System.out.print("Cause of martyrdom: ");
					String cause = scan.next();
					// creating the object using Martyr class because the person is a martyr
					try {
						if (f.addMember(new Martyr(id, name, role, age, gender, address, contact, date, cause, place),
								role)) {
							System.out.println("Member is added successfully");
						} else
							System.out.println("Member isn't added successfully");
					} catch (NoParents e) {
						System.out.println(e);
					}
				} else if (sit == 'L' || sit == 'l') {
					// creating the object using LivePerson class because the person is alive
					try {
						if (f.addMember(new LivePerson(id, name, role, age, gender, address, contact), role)) {
							System.out.println("Member is added successfully");
						} else
							System.out.println("Member isn't added successfully");
					} catch (NoParents e) {
						System.out.println(e);
					}
				} else {
					System.out.println("Invalid input");
					break;
				}
				break;
			// ----------------------------------------------------------------------------------------------------------------------
			case 3:
				System.out.print("Please enter the family name you want to add to: ");
				familyName = scan.next();
				f = manager.searchByName(familyName);

				if (f == null) { // to check if the family isn't exist
					System.out.println("Sorry, the family isn't found");
					break;
				}
				boolean ff=false;
				while (f.getParents().size() != 2) {
					ff=true;
					complete = true;
					num = 0;
					while (complete) {                                    // to try input again if the input is invalid
						try {
							System.out.print("Enter 1 to read data from console, 2 from the file: ");
							num = scan.nextInt();
							complete = false;
							if(num!=1 && num!=2) {                         // the input is just 1 or 2
								System.out.println("Invalid input");
								complete=true;
							}
						} catch (InputMismatchException ex) {
							System.out.println("Try again. (Invalid input: an integer is required)");
							scan.nextLine();
						}
					}
					if (num == 2) {
						System.out.print("Enter the ID of the parent: ");
						id = scan.next();
						if (manager.readFromFile(id) != null) {
							if (manager.searchPersonById(manager.readFromFile(id).getID()) != null) {
								System.out.println("Sorry, the member is added before");
							} else
								f.addParents(manager.readFromFile(id));
						} else
							System.out.println("Sorry, the member is not found in the file");
					} else {
						System.out.print("Please enter the members information \nID: ");
						id = scan.next();
						System.out.print("Name: ");
						name = scan.next();
						System.out.print("Role: ");
						role = scan.next();
						age = 0;
						complete = true; 
						while (complete) {                           // to try input again if the input is invalid
							try {
								System.out.print("Age: ");
								age = scan.nextInt();
								complete = false;
								if (age < 0 || age > 120) {
									System.out.println("Sorry, age should be between 0 and 120 years");
									complete = true;
									scan.nextLine();
								}
							} catch (InputMismatchException ex) {
								System.out.println("Try again. (Invalid input: an integer is required)");
								scan.nextLine();
							}
						}
						complete = true;
						gender = "";
						while (complete) {                             // to try input again if the input is invalid (just male or female)
							System.out.print("Gender (Male or Female): ");
							gender = scan.next();
							complete = false;
							if (!gender.toLowerCase().equals("male") && !gender.toLowerCase().equals("female")) {
								System.out.println("Sorry, the gender should be just male or female");
								complete = true;
							}
						}
						System.out.print("Address: ");
						address = scan.next();
						System.out.print("Contact information using this syntax (Email_PhoneNumber): ");
						contact = scan.next();
						System.out.print("Situation (M for martyrs and L for live people): ");
						sit = scan.next().charAt(0);
						if (manager.searchPersonById(id) != null) { // to check if the member is added before in the
																	// system
							System.out.println("The member is added before");
							break;
						}
						if (sit == 'M' || sit == 'm') { // to check if the person is a martyr to input date, place and
														// cause of
														// martyrdom
							System.out.print("Enter the date of martyrdom: ");
							String date = scan.next();
							System.out.print("Place of martyrdom: ");
							String place = scan.next();
							System.out.print("Cause of martyrdom: ");
							String cause = scan.next();
							// creating the object using Martyr class because the person is a martyr
							manager.searchByName(familyName).addParents(
									new Martyr(id, name, role, age, gender, address, contact, date, cause, place));
						} else if (sit == 'L' || sit == 'l') { // to check if the person is alive
							// creating the object using LivePerson class because the person is alive
							manager.searchByName(familyName)
									.addParents(new LivePerson(id, name, role, age, gender, address, contact));
						} else
							System.out.println("Invalid input");
					}
				}
				if(!ff) System.out.println("Sorry, this family has parents");
				break;
			// ------------------------------------------------------------------------------------------------------------------
			case 4:
				System.out.print("Enter the name of the family you want to remove from: ");
				name = scan.next();
				if (manager.searchByName(name) == null) { // to check if the family isn't exist
					System.out.println("Sorry, the family isn't found");
					break;
				}
				System.out.print("Enter the members ID: ");
				id = scan.next();
				Person person = new LivePerson(id, "", "", 0, "", "", ""); // we just need the id to remove a person
				if (manager.searchByName(name).removeMember(person)) { // to check if the person removed successfully
					System.out.println("The member is removed successfully");
				} else
					System.out.println("The member isn't found");
				break;
			// -----------------------------------------------------------------------------------------------------------------
			case 5:
				System.out.print("Enter the name of the family you want to remove from: ");
				familyName = scan.next();
				f = manager.searchByName(familyName);
				if (f == null) { // to check if the family isn't exist
					System.out.println("Sorry, the family isn't found");
					break;
				}
				System.out.print("Enter the parents ID: ");
				id = scan.next();
				person = new LivePerson(id, "", "", 0, "", "", ""); // we just need the id to remove a person
				if (f.removeParent(person)) { // to check if the person removed successfully
					System.out.println("The parent is removed successfully, add another one instead of him/her");
					while (f.getParents().size() != 2) {
						complete = true;
						num = 0;
						while (complete) {                            // to try input again if the input is invalid
							try {
								System.out.print("Enter 1 to read data from console, 2 from the file: ");
								num = scan.nextInt();
								complete = false;
								if(num!=1 && num!=2) {
									System.out.println("Invalid input");
									complete=true;
								}
							} catch (InputMismatchException ex) {
								System.out.println("Try again. (Invalid input: an integer is required)");
								scan.nextLine();
							}
						}
						if (num == 2) {
							System.out.print("Enter the ID of the parent: ");
							id = scan.next();
							if (manager.readFromFile(id) != null) {
								if (manager.searchPersonById(manager.readFromFile(id).getID()) != null) {
									System.out.println("Sorry, the member is added before");
								} else
									f.addParents(manager.readFromFile(id));
							} else
								System.out.println("Sorry, the member is not found in the file");
						} else {
							System.out.print("Please enter the members information \nID: ");
							id = scan.next();
							if (manager.searchPersonById(id) != null) { // to check if the member is added before in the
								// system
								System.out.println("The member is added before, try again");
								continue;
							}
							System.out.print("Name: ");
							name = scan.next();
							System.out.print("Role: ");
							role = scan.next();
							age = 0;
							complete = true;
							while (complete) {                      // to try input again if the input is invalid
								try {
									System.out.print("Age: ");
									age = scan.nextInt();
									complete = false;
									if (age < 0 || age > 120) {        // the age can't be less than 0 or more than 120
										System.out.println("Sorry, age should be between 0 and 120 years");
										complete = true;
										scan.nextLine();
									}
								} catch (InputMismatchException ex) {
									System.out.println("Try again. (Invalid input: an integer is required)");
									scan.nextLine();
								}
							}
							complete = true;
							gender = "";
							while (complete) {                              // to try input again if the input is invalid
								System.out.print("Gender (Male or Female): ");
								gender = scan.next();
								complete = false;
								if (!gender.toLowerCase().equals("male") && !gender.toLowerCase().equals("female")) {
									System.out.println("Sorry, the gender should be just male or female");
									complete = true;
								}
							}
							System.out.print("Address: ");
							address = scan.next();
							System.out.print("Contact information using this syntax (Email_PhoneNumber): ");
							contact = scan.next();
							System.out.print("Situation (M for martyrs and L for live people): ");
							sit = scan.next().charAt(0);
							if (sit == 'M' || sit == 'm') { // to check if the person is a martyr to input date, place
															// and cause of
															// martyrdom
								System.out.print("Enter the date of martyrdom: ");
								String date = scan.next();
								System.out.print("Place of martyrdom: ");
								String place = scan.next();
								System.out.print("Cause of martyrdom: ");
								String cause = scan.next();
								// creating the object using Martyr class because the person is a martyr
								manager.searchByName(familyName).addParents(
										new Martyr(id, name, role, age, gender, address, contact, date, cause, place));
							} else if (sit == 'L' || sit == 'l') { // to check if the person is alive
								// creating the object using LivePerson class because the person is alive
								manager.searchByName(familyName)
										.addParents(new LivePerson(id, name, role, age, gender, address, contact));
							} else
								System.out.println("Invalid input");
						}
					}
				} else
					System.out.println("The parent isn't found");
				break;
			// ---------------------------------------------------------------------------------------------------------------------
			case 6:
				System.out.print("Enter the name of the family you want to remove: ");
				name = scan.next();
				if (manager.deleteFamily(name))
					System.out.println("The family is removed successfully");
				else
					System.out.println("The family isn't found");
				break;
			// ----------------------------------------------------------------------------------------------------------------------
			case 7:
				System.out.print("Enter the name of the first family: ");
				String name1 = scan.next();
				System.out.print("Enter the name of the second family: ");
				String name2 = scan.next();
				if (manager.searchByName(name1) == null || manager.searchByName(name2) == null) { // to check if one of
																									// the families
																									// isn't exist
					System.out.println("The family isn't exist");
					break;
				}
				if (manager.searchByName(name1).equals(manager.searchByName(name2))) {
					System.out.println("They have the same number of martyrs");
				} else
					System.out.println("They don't have the same number of martyrs");
				break;
			// -------------------------------------------------------------------------------------------------------------------------
			case 8:
				boolean flag = false;
				System.out.print("Enter the name of the family you want to update: ");
				name1 = scan.next();
				if (manager.searchByName(name1) == null) { // to check if the family we want to update is exist
					System.out.println("The family isn't exist");
					break;
				}
				System.out.print("Enter the new name of the family: ");
				name2 = scan.next();
				if (!name1.toLowerCase().equals(name2.toLowerCase())) {
					if (manager.searchByName(name2) != null) { // to check if the new name of the family is exist before
						System.out.println("Sorry, This family name is exist before");
						break;
					}
				}
				Family f1 = new Family(name2), oldFamily = manager.searchByName(name1); // a new object to save data
																						// after updating
				System.out.print("Enter the ID of the member you want to modify: ");
				id = scan.next();
				if (manager.searchPersonById(id) == null) {
					System.out.println("Sorry, The person isn't exist");
					break;
				}
				role = manager.searchPersonById(id).getRole();
				gender = manager.searchPersonById(id).getGender();
				String date="", place="", cause="";
				if(manager.searchPersonById(id) instanceof Martyr) {
					date= ((Martyr)manager.searchPersonById(id)).getDateOfMartyrdom();
					place=((Martyr)manager.searchPersonById(id)).getPlaceOfDeath();
					cause=((Martyr)manager.searchPersonById(id)).getCauseOfDeath();
				}
				for (int i = 0; i < oldFamily.getParents().size(); i++) {
					if (oldFamily.getParents().get(i).getID().equals(id)) { // to find the person we want to modify
						flag = true;
						System.out.println("Those are his informations: \n" + oldFamily.getParents().get(i).toString()
								+ "\nEnter his new ones");
						System.out.print("Name: ");
						name = scan.next();
						age = 0;
						complete = true;
						while (complete) {                                     // to try input again if the input is invalid
							try {
								System.out.print("Age: ");
								age = scan.nextInt();
								complete = false;
								if ((age < 0 || age > 120) && (role.toLowerCase().equals("son")        // the age of siplings is between 0 and 120
										|| role.toLowerCase().equals("daughter"))) {
									System.out.println("The age should be between 0 and 120");
									complete = true;
									scan.nextLine();
								} else if ((age < 16 || age > 120)
										&& (role.toLowerCase().equals("dad") || role.toLowerCase().equals("mom"))) {// the age of parents is between 16 and 120
									System.out.println("The age of a parent should be between 16 and 120");
									complete = true;
									scan.nextLine();
								} else if (role.toLowerCase().equals("dad") || role.toLowerCase().equals("mom")) {
									for (int j = 0; j < oldFamily.getMembers().size(); j++) {
										if (age - oldFamily.getMembers().get(j).getAge() < 17) {  // the difference of parents age and siplings age
											System.out.println(
													"Sorry, the difference between ages of parents and members should be at least 17");
											complete = true;
											scan.nextLine();
											break;
										}
									}
								} else if (role.toLowerCase().equals("son") || role.toLowerCase().equals("daughter")) {
									for (int j = 0; j < oldFamily.getParents().size(); j++) {
										if (oldFamily.getParents().get(j).getAge() - age < 17) {  // the difference of parents age and siplings age
											System.out.println(
													"Sorry, the difference between ages of parents and members should be at least 17");
											complete = true;
											scan.nextLine();
											break;
										}
									}
								}
							} catch (InputMismatchException ex) {
								System.out.println("Try again. (Invalid input: an integer is required)");
								scan.nextLine();
							}
						}
						System.out.print("Address: ");
						address = scan.next();
						System.out.print("Contact information: ");
						contact = scan.next();
						if (oldFamily.getParents().get(i) instanceof LivePerson) { // to let the user modify his
																					// martyrdom data
							System.out.print("Situation (M for martyrs and L for live people): ");
							sit = scan.next().charAt(0);
							if (sit == 'm' || sit == 'M') {
								System.out.print("Date of martyrdom: ");
								date = scan.next();
								System.out.print("Place of martyrdom: ");
								place = scan.next();
								System.out.print("Cause of martyrdom: ");
								cause = scan.next();
								f1.addParents(
										new Martyr(id, name, role, age, gender, address, contact, date, place, cause));
							} else if (sit == 'l' || sit == 'L')
								f1.addParents(new LivePerson(id, name, role, age, gender, address, contact));
							else {
								System.out.println("Invalid input");
								f1.addParents(new LivePerson(id, name, role, age, gender, address, contact));
							}
						} else
							f1.addParents(new Martyr(id, name, role, age, gender, address, contact, date, place, cause));
					}

					// adding every member in the old family to the new one after modifying the
					// required family
					else
						f1.addParents(oldFamily.getParents().get(i));
				}
				for (int i = 0; i < oldFamily.getMembers().size(); i++) { // !flag means he isn't a parent, we want to
																			// check if he's a sibling
					if (oldFamily.getMembers().get(i).getID().equals(id)) {
						flag = true;
						System.out.println("Those are his informations: \n" + oldFamily.getMembers().get(i).toString()
								+ "\nEnter his new ones");
						System.out.print("Name: ");
						name = scan.next();
						age = 0;
						complete = true;
						while (complete) {
							try {
								System.out.print("Age: ");
								age = scan.nextInt();
								complete = false;
								if ((age < 0 || age > 120) && (role.toLowerCase().equals("son")
										|| role.toLowerCase().equals("daughter"))) {
									System.out.println("The age should be between 0 and 120");
									complete = true;
									scan.nextLine();
								} else if ((age < 16 || age > 120)
										&& (role.toLowerCase().equals("dad") || role.toLowerCase().equals("mom"))) {
									System.out.println("The age of a parent should be between 16 and 120");
									complete = true;
									scan.nextLine();
								} else if (role.toLowerCase().equals("dad") || role.toLowerCase().equals("mom")) {
									for (int j = 0; j < oldFamily.getMembers().size(); j++) {
										if (age - oldFamily.getMembers().get(j).getAge() < 17) {  // the difference of parents age and siplings age
											System.out.println(
													"Sorry, the difference between ages of parents and members should be at least 17");
											complete = true;
											scan.nextLine();
											break;
										}
									}
								} else if (role.toLowerCase().equals("son") || role.toLowerCase().equals("daughter")) {
									for (int j = 0; j < oldFamily.getParents().size(); j++) {
										if (oldFamily.getParents().get(j).getAge() - age < 17) {   // the difference of parents age and siplings age
											System.out.println(
													"Sorry, the difference between ages of parents and members should be at least 17");
											complete = true;
											scan.nextLine();
											break;
										}
									}
								}
							} catch (InputMismatchException ex) {
								System.out.println("Try again. (Invalid input: an integer is required)");
								scan.nextLine();
							}
						}
						System.out.print("Address: ");
						address = scan.next();
						System.out.print("Contact information: ");
						contact = scan.next();
						if (oldFamily.getMembers().get(i) instanceof LivePerson) { // to let the user modify his
																					// martyrdom data
							System.out.print("Situation (M for martyrs and L for live people): ");
							sit = scan.next().charAt(0);
							if (sit == 'm' || sit == 'M') {
								System.out.print("Date of martyrdom: ");
								date = scan.next();
								System.out.print("Place of martyrdom: ");
								place = scan.next();
								System.out.print("Cause of martyrdom: ");
								cause = scan.next();
								try {
									f1.addMember(new Martyr(id, name, role, age, gender, address, contact, date, place,
											cause), role);
								} catch (NoParents e) {
									System.out.println(e);
								}
							} else if (sit == 'l' || sit == 'L') {
								try {
									f1.addMember(new LivePerson(id, name, role, age, gender, address, contact), role);
								} catch (NoParents e) {
									System.out.println(e);
								}
							} else {
								System.out.println("Invalid input");
								f1.addParents(new LivePerson(id, name, role, age, gender, address, contact));
							}
						} else
							try {
								f1.addMember(new Martyr(id, name, role, age, gender, address, contact, date, place, cause), role);
							} catch (NoParents e) {
								System.out.println(e);
							}
					}
					// adding every member in the old family to the new one after modifying the
					// required family
					else try {
						f1.addMember(oldFamily.getMembers().get(i), oldFamily.getMembers().get(i).getRole());
					} catch (NoParents e) {
						System.out.println(e);
					}
				}
				if (!flag) { // !flag here means that the person isn't exist in this family
					System.out.println("The person isn't found in this family");
					manager.updateFamily(name1, f1); // the family name may be updated so we should call update method
														// although the person isn't exist
					break;
				}
				if (manager.updateFamily(name1, f1))
					System.out.println("The family is updated successfully");
				break;
			// ------------------------------------------------------------------------------------------------------------------------
			case 9:
				System.out.print("Enter the ID of the person you want to search about: ");
				id = scan.next();
				if (manager.searchPersonById(id) != null) { // to check if the person is exist
					System.out.println(manager.searchPersonById(id).toString());
				} else
					System.out.println("The person isn't found");
				break;
			// ---------------------------------------------------------------------------------------------------------------------------
			case 10:
				System.out.print("Enter the name of the family you want to search about: ");
				name = scan.next();
				Family family = manager.searchByName(name);
				if (family != null) { // to check if the family is exist
					System.out.println("The family is found and those are the members: ");
					for (int i = 0; i < family.getParents().size(); i++) { // printing information about all parents of the family
						System.out.println(family.getParents().get(i).toString());
					}
					for (int i = 0; i < family.getMembers().size(); i++) { // printing information about all members of the family
						System.out.println(family.getMembers().get(i).toString());
					}
				} else
					System.out.println("The family isn't found");
				break;
			// ----------------------------------------------------------------------------------------------------------------------
			case 11:
				// giving an array list of global information (martyrs, orphans, live people)
				ArrayList<Integer> list = manager.calculateGlobalStatistics();
				System.out.println("The number of martyrs: " + list.get(0));
				System.out.println("The number of orphans: " + list.get(1));
				System.out.println("The number of live people: " + list.get(2));
				break;
			// ---------------------------------------------------------------------------------------------------------------------
			case 12:
				System.out.print("Enter the name of the family: ");
				name = scan.next();
				if (manager.searchByName(name) == null) { // to check if the family isn't exist
					System.out.println("The family doesn't exist");
					break;
				}
				// giving an array list of family's information (martyrs, orphans, live people)
				list = manager.calculateFamilyStatistics(name);
				System.out.println("The number of martyrs: " + list.get(0));
				System.out.println("The number of orphans: " + list.get(1));
				System.out.println("The number of live people: " + list.get(2));
				break;
			// ------------------------------------------------------------------------------------------------------------------------
			case 13:
				num = 0;
				complete = true;
				while (complete) {                    // to check for invalid inputs
					try {
						System.out
								.print("Enter 1 if you want to sort by number of Martyrs or 2 by number of orphans: ");
						num = scan.nextInt();
						complete = false;
						if(num!=1 && num!=2) {
							System.out.println("Invalid input");
							complete=true;
						}
					} catch (InputMismatchException ex) {
						System.out.println("Try again. (Invalid input: an integer is required)");
						scan.nextLine();
					}
				}
				Family f2 = new Family();
				if (num == 1) {
					ArrayList<Family> families = f2.sortByMartyrs(manager.getFamilies());  // sort by martyrs
					for (int i = 0; i < families.size(); i++) {
						System.out.println(families.get(i).getFamilyName());
					}
				} else if (num == 2) {
					ArrayList<Family> families = f2.sortByOrphans(manager.getFamilies());  // sort by orphans
					for (int i = 0; i < families.size(); i++) {
						System.out.println(families.get(i).getFamilyName());
					}
				} else
					System.out.println("Invalid input");
				break;
			// ---------------------------------------------------------------------------------------------------------------------------------
			case 14:
				Family f3 = new Family();
				if (manager.uploadToFile(f3.sortByMartyrs(manager.getFamilies()))) {
					System.out.println("Families data is uploaded successfully");
				} else
					System.out.println("Families data is not uploaded successfully");
				break;
			// ----------------------------------------------------------------------------------------------------------------------------------
			case 15:
				complete = true;
				num = 0;
				while (complete) {              // to check for invalid inputs
					try {
						System.out.print("Enter 1 if you want to make copy of a family, 2 of person: ");
						num = scan.nextInt();
						complete = false;
					} catch (InputMismatchException ex) {
						System.out.println("Try again. (Invalid input: an integer is required)");
						scan.nextLine();
					}
				}
				if (num == 1) {                                               // to copy a family by its name
					System.out.print("Enter the name of the family: ");
					name = scan.next();
					if (manager.searchByName(name) == null)
						System.out.println("Family not found");
					else {
						f2 = (Family) manager.searchByName(name).deep();
						System.out.println("The family copied successfully, those are its information");
						System.out.println(f2.toString());
					}
				} else if (num == 2) {                                // to copy a person by his/her id
					System.out.print("Enter the person's id: ");
					id = scan.next();
					Person p = manager.searchPersonById(id);
					if (p == null)
						System.out.println("The person isn't found");
					else {
						if (p instanceof Martyr) {
							p = ((Martyr) manager.searchPersonById(id)).deep();
							System.out.println("The Person copied successfully, those are his information");
							System.out.println(p.toString());
						} else {
							p = ((LivePerson) manager.searchPersonById(id)).deep();
							System.out.println("The Person copied successfully, those are his information");
							System.out.println(p.toString());
						}
					}
				} else
					System.out.println("Invalid input");
				break;
			// ---------------------------------------------------------------------------------------------------------------------------
			case 16:
				System.out.println("Exiting...");
				break;
			// ------------------------------------------------------------------------------------------------------------------------------
			default:
				System.out.println("Invalid input, please try again");
				break;
			}

		} while (choice != 16);
	}
}