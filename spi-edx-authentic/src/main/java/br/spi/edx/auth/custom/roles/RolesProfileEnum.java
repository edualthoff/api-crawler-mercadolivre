package br.spi.edx.auth.custom.roles;

public enum RolesProfileEnum {

	ROLE_USER("usuario", "c58f0b36-ee55-4828-8055-85a30c9ca0f3"),
	ROLE_CLIENT("",""),
	ROLE_ADMINISTRADOR("administrador", "9958ac92-24fa-46fa-84f2-e353ef887300"), ROLE_MANAGER("",""), ROLE_ADMIN("",""), ROLE_OWNER("","");

	private String roleKeycloak;
	private String groupId;
	RolesProfileEnum(String roleKeycloak, String groupId) {
		this.roleKeycloak = roleKeycloak;
		this.groupId = groupId;
	}

	public String getRoleKeycloak() {
		return roleKeycloak;
	}

	public String roleKeycloak(RolesProfileEnum role) {
		switch (role) {
		case ROLE_ADMINISTRADOR: {
			return ROLE_ADMINISTRADOR.getRoleKeycloak();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + role);
		}
	}

	public String getGroupId() {
		return groupId;
	}
	
	public String groupId(RolesProfileEnum role) {
		switch (role) {
		case ROLE_ADMINISTRADOR: {
			return ROLE_ADMINISTRADOR.getGroupId();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + role);
		}
	}
}
