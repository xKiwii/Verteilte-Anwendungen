package webapp;

import static webapp.Errorcodes.*;
import static webapp.Zustaende.*;

public class Kartenverkauf {

	private boolean reservationEnabled = true;
	private Sitzplatz[] sitzplaetze;

	public Kartenverkauf(int anzahlSitzplaetze) {

		if (anzahlSitzplaetze > 0) {
			this.sitzplaetze = new Sitzplatz[anzahlSitzplaetze];

			for (int i = 0; i < sitzplaetze.length; i++) {
				sitzplaetze[i] = new Sitzplatz(i + 1);
			}
		}
	}

	public synchronized void verkaufEinesTicket(int number) {

		if (number < 0 || number > sitzplaetze.length) {
			throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
		}

		Sitzplatz sitzplatz = sitzplaetze[number];

		switch (sitzplatz.getZustand()) {
		case FREI:
			sitzplatz.setZustand(VERKAUFT);
			break;
		case RESERVIERT:
			throw new OperationErrorException(BEREITS_RESERVIERT);
		case VERKAUFT:
			throw new OperationErrorException(BEREITS_VERKAUFT);
		}
	}

	public synchronized void reservierungEinesTickets(int number, String name) {

		if (reservationEnabled) {

			if (number < 0 || number > sitzplaetze.length) {
				throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
			}

			Sitzplatz sitzplatz = sitzplaetze[number];

			switch (sitzplatz.getZustand()) {
			case FREI:
				sitzplatz.setZustand(RESERVIERT);
				sitzplatz.setName(name);
				break;
			case RESERVIERT:
				throw new OperationErrorException(BEREITS_RESERVIERT);
			case VERKAUFT:
				throw new OperationErrorException(BEREITS_VERKAUFT);
			}
		} else {
			throw new OperationErrorException(RESERVIERUNG_NICHT_MOEGLICH);
		}

	}

	public synchronized void verkaufEinesReserviertenTickets(int number, String name){

		if (reservationEnabled) {

			if (number < 0 || number > sitzplaetze.length) {
				throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
			}

			Sitzplatz sitzplatz = sitzplaetze[number];

			switch (sitzplatz.getZustand()) {
			case RESERVIERT:

				if (name.equals(sitzplatz.getName())) {
					sitzplatz.setZustand(VERKAUFT);
				} else {
					throw new OperationErrorException(UNGUELTIGER_NAME);
				}
				break;
			case FREI:
				throw new OperationErrorException(MUSS_RESERVIERT_SEIN);
			case VERKAUFT:
				throw new OperationErrorException(BEREITS_VERKAUFT);
			}
		} else {
			throw new OperationErrorException(RESERVIERUNG_NICHT_MOEGLICH);
		}
	}

	public synchronized void stornierungEinesTickets(int number) {

		if (number < 0 || number > sitzplaetze.length) {
			throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
		}

		Sitzplatz sitzplatz = sitzplaetze[number];

		Zustaende zustand = sitzplatz.getZustand();

		if (zustand == RESERVIERT || zustand == VERKAUFT) {
			sitzplatz.setZustand(FREI);
		} else {
			throw new OperationErrorException(STORNIERUNG_FREIEN_TICKETS);
		}

	}

	public synchronized void reservierungenAufheben() {

		reservationEnabled = false;

		for (int i = 0; i < sitzplaetze.length; i++) {
			if (sitzplaetze[i].getZustand() == RESERVIERT) {
				sitzplaetze[i].setName("");
				sitzplaetze[i].setZustand(FREI);
			}
		}
	}

	public synchronized String toString() {

		String kartenverkaufText = "";
		for (int i = 0; i < sitzplaetze.length; i++) {
			kartenverkaufText += sitzplaetze[i].toString() + "; \n";
		}

		return kartenverkaufText;

	}
	
	public synchronized Sitzplatz[] getSitzplaetze(){
		return sitzplaetze;
	}
	
	public synchronized boolean getReservationStatus(){
		return reservationEnabled;
	}
	
	
	///////////////

}

//TODO: NumberFormatException
//TODO: JSP in URL entfernen

//TODO: Prüfung und MEthode läuft einfach durch wenns passt, Wenn nicht worft Exception -> Code schlanker