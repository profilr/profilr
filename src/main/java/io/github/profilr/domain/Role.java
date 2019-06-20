package io.github.profilr.domain;

public abstract class Role {
	
	public static final Role[] roles = new Role[5];
	public static int nextRoleID = 0;
	
	public static final Role USER = new DefaultUser();
	public static final Role ADMIN = new Admin();
	public static final Role OWNER = new Owner();
	
	public static Role getRole(int role) {
		return roles[role];
	}
	
	private int id;
	
	public Role() {
		this.id = nextRoleID;
		roles[id] = this;
		nextRoleID++;
	}
	
	public int getID() {
		return this.id;
	}
	
	public abstract boolean canDelete();
	
	public abstract boolean canView();
	
	public abstract boolean canEdit();
	
}

class DefaultUser extends Role {
	
	public boolean canDelete() {
		return false;
	}

	public boolean canView() {
		return true;
	}
	
	public boolean canEdit() {
		return false;
	}
	
}


class Admin extends Role {
	
	public boolean canView() {
		return true;
	}

	public boolean canEdit() {
		return true;
	}

	public boolean canDelete() {
		return false;
	}
	
}

class Owner extends Role {

	public boolean canDelete() {
		return false;
	}

	public boolean canView() {
		return false;
	}

	public boolean canEdit() {
		return false;
	}

	
	
}

