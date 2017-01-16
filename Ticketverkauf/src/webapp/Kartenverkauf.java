package webapp;

import static webapp.Errorcodes.*;
import static webapp.Zustaende.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class Kartenverkauf {

	private boolean reservationEnabled = true;
	private Sitzplatz[] sitzplaetze;
	
	private DataSource datasource;
	
	public Kartenverkauf(int anzahlSitzplaetze, DataSource datasource) {
		this.datasource = datasource;
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

		//Sitzplatz sitzplatz = sitzplaetze[number];

		//switch (sitzplatz.getZustand()) {
		switch(this.getZustand(number)) {
		case FREI:
			//sitzplatz.setZustand(VERKAUFT);
			this.setZustand(number, VERKAUFT);
			break;
		case RESERVIERT:
			throw new OperationErrorException(BEREITS_RESERVIERT);
		case VERKAUFT:
			throw new OperationErrorException(BEREITS_VERKAUFT);
		}
	}

	public synchronized void reservierungEinesTickets(int number, String name) {

		if(this.getReservationEnabledStatus()) {
		//if (reservationEnabled) {

			if (number < 0 || number > sitzplaetze.length) {
				throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
			}

			//Sitzplatz sitzplatz = sitzplaetze[number];

			//switch (sitzplatz.getZustand()) {
			switch(this.getZustand(number)) {
			case FREI:
				this.setZustand(number, RESERVIERT);
				this.setName(number, name);
				//sitzplatz.setZustand(RESERVIERT);
				//sitzplatz.setName(name);
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

		if(this.getReservationEnabledStatus()) {
		//if (reservationEnabled) {

			if (number < 0 || number > sitzplaetze.length) {
				throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
			}

			//Sitzplatz sitzplatz = sitzplaetze[number];

			//switch (sitzplatz.getZustand()) {
			switch(this.getZustand(number)) {
			case RESERVIERT:

				if (name.equals(this.getName(number))) {
					//sitzplatz.setZustand(VERKAUFT);
					this.setZustand(number, VERKAUFT);
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

		//Sitzplatz sitzplatz = sitzplaetze[number];

		//Zustaende zustand = sitzplatz.getZustand();
		Zustaende zustand = this.getZustand(number);

		if (zustand == RESERVIERT || zustand == VERKAUFT) {
			//sitzplatz.setZustand(FREI);
			this.setZustand(number, FREI);
			this.setName(number, null);
		} else {
			throw new OperationErrorException(STORNIERUNG_FREIEN_TICKETS);
		}

	}

	public synchronized void reservierungenAufheben() {

		//reservationEnabled = false;
		
		this.setReservationDisabled();

		/* for (int i = 0; i < sitzplaetze.length; i++) {
			if (sitzplaetze[i].getZustand() == RESERVIERT) {
				sitzplaetze[i].setName("");
				sitzplaetze[i].setZustand(FREI);
			}
		} */
		
		this.setAllZustand();
		
		
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
	
	
	
	//Datenbank //
	
	/* public synchronized void verkaufEinesTicketDatenbank(int number) {

		if (number < 0 || number > sitzplaetze.length) {
			throw new OperationErrorException(UNGUELTIGE_SITZNUMMER);
		}

		switch (this.getZustand(number)) {
		case FREI:
			this.setZustand(number, VERKAUFT);
			break;
		case RESERVIERT:
			throw new OperationErrorException(BEREITS_RESERVIERT);
		case VERKAUFT:
			throw new OperationErrorException(BEREITS_VERKAUFT);
		}
	}
	*/
	
	
	
	private synchronized void setZustand(int n, Zustaende zustand){
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement updateZustand = null;
		    String updateString = "update sitzplaetze set status = ? where nummer = ?";
		    
		    updateZustand = connection.prepareStatement(updateString);

		    updateZustand.setString(1,zustand.toString());
		    updateZustand.setInt(2, n+1);
		    
		    updateZustand.executeUpdate();
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
	}
	
	
	
	private synchronized void setName(int n, String name){
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement updateZustand = null;
		    String updateString = "update sitzplaetze set reservierungsname = ? where nummer = ?";
		    
		    updateZustand = connection.prepareStatement(updateString);

		    updateZustand.setString(1,name);
		    updateZustand.setInt(2, n+1);
		    
		    updateZustand.executeUpdate();
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
	}
	
	
	private synchronized void setAllZustand(){
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement updateZustand = null;
		    String updateString = "update sitzplaetze set status = ? where status = ?";
		    
		    updateZustand = connection.prepareStatement(updateString);

		    updateZustand.setString(1,Zustaende.FREI.toString());
		    updateZustand.setString(2,Zustaende.RESERVIERT.toString());
		    
		    updateZustand.executeUpdate();
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
	}
	
	
	
	public synchronized Zustaende getZustand(int n) {
		
		Zustaende zustand = null;
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement getZustand = null;
		    String zustandString = "select status from sitzplaetze where nummer = ?";
		    
		    getZustand = connection.prepareStatement(zustandString);
		    getZustand.setInt(1, n+1);
		    
		    ResultSet rs = getZustand.executeQuery();
		    
		    while ( rs.next() )
		    {
		    	zustand = Zustaende.valueOf(rs.getString("status"));
		    }
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
		return zustand;
	}
	
	
	public synchronized String getName(int n) {
		
		String name = null;
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement getName = null;
		    String nameString = "select reservierungsname from sitzplaetze where nummer = ?";
		    
		    getName = connection.prepareStatement(nameString);
		    getName.setInt(1, n+1);
		    
		    ResultSet rs = getName.executeQuery();
		    
		    while ( rs.next() )
		    {
		    	name = rs.getString("reservierungsname");
		    }
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
		return name;
	}
	
	
	public synchronized Boolean getReservationEnabledStatus() {
		
		Boolean reservationEnabled = null;
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement getReservationStatus = null;
		    String reservationStatusString = "select enabled from reservationStatus";
		    
		    getReservationStatus = connection.prepareStatement(reservationStatusString);
		    
		    ResultSet rs = getReservationStatus.executeQuery();
		    
		    while ( rs.next() )
		    {
		    	reservationEnabled = rs.getBoolean("enabled");
		    }
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
		return reservationEnabled;
	}
	
	
private synchronized void setReservationDisabled(){
		
		try {
			Connection connection = datasource.getConnection();
			
			PreparedStatement updateReservationStatus = null;
		    String reservationStatusString = "update reservationStatus set enabled = ? where enabled = ?";
		    
		    updateReservationStatus = connection.prepareStatement(reservationStatusString);

		    updateReservationStatus.setBoolean(1, false);
		    updateReservationStatus.setBoolean(2, true);
		    
		    updateReservationStatus.executeUpdate();
			
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OperationErrorException(DATENUEBERTRAGUNG);
		}
		
	}
	


}

//TODO: NumberFormatException
//TODO: JSP in URL entfernen

//TODO: Prüfung und MEthode läuft einfach durch wenns passt, Wenn nicht worft Exception -> Code schlanker