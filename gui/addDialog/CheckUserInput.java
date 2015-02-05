package gui.addDialog;


public class CheckUserInput {

	public static boolean versionCheck;
	
	public CheckUserInput() {

		versionCheck();
	}

	private void versionCheck() {
		String scanString = DialogFrame.textVersion.getText();
	   // Scanner scan = new Scanner(scanString);
		try {
			Double.parseDouble(scanString); 	//Value must be double for Version
		    if (scanString.length() < 5) {							//Value length of Version can not be longer then 5 characters
			    versionCheck = true; 
			}
		} catch (NumberFormatException e) {
			versionCheck = false;
		}
	}
}
