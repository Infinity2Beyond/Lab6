package common.data;

public enum mood {
	SADNESS  ,
    SORROW ,
    GLOOM ,
    APATHY ,
    RAGE ;
	public static String nameList() {
        String nameList = "";
        for (mood mood : values()) {
            nameList += mood.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
	

}
