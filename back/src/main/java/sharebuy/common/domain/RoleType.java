package sharebuy.common.domain;

public enum RoleType {
    ADMIN(1),USER(2),GUEST(3);

    private final int level;

    RoleType(int level) {
        this.level = level;
    }

    public boolean canAccess(RoleType required){
        return this.level<= required.level;
    }
}
