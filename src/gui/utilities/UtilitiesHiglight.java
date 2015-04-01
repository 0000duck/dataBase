package gui.utilities;

public class UtilitiesHiglight {

		public static int start;
		public static int end;
			
		public static void findText (String Text, String Find) {
			String text1, find1;
			text1 = Text.toLowerCase();
			find1 = Find.toLowerCase();
			start = text1.indexOf(find1);
			end = start + find1.length();
		}
}	
