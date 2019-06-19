package io.github.profilr.domain.db;

import java.util.ArrayList;
import java.util.List;

import io.github.profilr.domain.Section;
import io.github.profilr.domain.User;

public class SectionManager {
	
	/* TODO I'm considering adding something like a static map of sections that were recently accessed here so that
	*  we don't have to make db requests each time we want a reference to a section...
	*  Maybe something similar with other persistent objects too...
	*  Not sure if thats a good idea or not though.
	*/
	
	public static void addSection(Section c) {
		// TODO Add a record to the section table for the section
	}
	
	public static void removeSection(Section c) {
		/* TODO Remove the record from the section table along with records from the enrollment and role tables
		 * (Am I missing something here?)
		 */
	}
	
	public static Section getSection(int sectionId) {
		// TODO Get an instance of the section with the given section id.
		return new Section();
	}
	
	public static void enrollUserInSection(User u, Section c) {
		/* TODO Add a record to the enrollment table with the user's id and the section's id.
		 * Also should update the java instances.
		 */
	}
	
	public static void removeUserFromClass(User u, Section c) {
		// TODO Remove records from the enrollment table which containn the given user id and section id
	}
	
	public static void assignUserRole(User u, Section c, int role) {
		/* TODO Add a record to the roles table with the user id, section id, and role id.
		 * I think I might want to make an actual role class so that we can have stuff other than admin and user... maybe.
		 */
	}
	
	public static List<User> getUsersInSection(Section c) {
		/* TODO Get all records from the enrollment table with the given section id. Use the user ids from these records to
		*  get a list of user instances.
		*/
		return new ArrayList<User>();
	}
	
	public static List<Section> getSectionsEnrolledInByUser(User u) {
		/* TODO Get all records from the enrollment table with the given user id. Use the section ids from these records to
		 * get a list of section instances.
		 */
		return new ArrayList<Section>();
	}
	
}
