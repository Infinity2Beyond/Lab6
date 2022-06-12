package common.data;

public enum weapontype {
	AXE,
    PISTOL,
    KNIFE,
    MACHINE_GUN;
	public static String nameList() {
        String nameList = "";
        for (weapontype weapontype : values()) {
            nameList += weapontype.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }

}
