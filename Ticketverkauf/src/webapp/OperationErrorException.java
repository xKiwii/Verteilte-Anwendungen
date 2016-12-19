package webapp;
public class OperationErrorException extends RuntimeException{
	
	
	private static final long serialVersionUID = 8508064707464334303L;


	public OperationErrorException(Errorcodes errorcode) {
		 super(setErrorText(errorcode));
	 }

	public OperationErrorException(Errorcodes errorcode, Throwable e) {
		 super(setErrorText(errorcode),e);
	 }

	 
	 private static String setErrorText(Errorcodes errorcode){
		 
		 String errorText = "";
	 
		 switch(errorcode) {
	     	case BEREITS_RESERVIERT:
	     		errorText = "Dieser Sitzplatz ist bereits reserviert.";
	     		break;
	     	case BEREITS_VERKAUFT:
	     		errorText = "Dieser Sitzplatz ist bereits verkauft.";
	     		break;
	     	case RESERVIERUNG_NICHT_MOEGLICH:
	     		errorText = "Die Reservierungen sind nicht mehr möglich.";
	     		break;
	     	case UNGUELTIGER_NAME:
	     		errorText = "Der eingegbene Name stimmt nicht überein.";
	     		break;
	     	case UNGUELTIGE_SITZNUMMER:
	     		errorText = "Die eingebene Sitznummer existiert nicht";
	     		break;
	     	case MUSS_RESERVIERT_SEIN:
	     		errorText = "Dieser Sitzplatz ist nicht reserviert.";
	     		break;
	     	case STORNIERUNG_FREIEN_TICKETS:
	     		errorText = "Der ausgewählte Sitzplatz ist frei und kann deshalb nicht storniert werden.";
	     		break;
	     	case OPERATION_NICHT_GUELTIG:
	     		errorText = "Interner Fehler. Operation derzeit nicht möglich.";
	     		break;
	     	case FELDER_NICHT_AUSGEFUELLT:
	     		errorText = "Nicht alle benötigten Felder wurden korrekt ausgefüllt.";
	     		break;
	     	default:
	     		errorText = "Es ist ein Fehler aufgetreten";
		 }
		 
		 return errorText;
	 }
}
