<%@page import="webapp.Kartenverkauf,webapp.Sitzplatz,webapp.Zustaende" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Kartenverkauf</title>
</head>
<body>
	<h2>Kartenverkauf</h2>

	<table>
		<tr>
			<td>
				<table rules="all" cellpadding="5" border="2" style="">
				
				<tr>
				<% 
				Kartenverkauf kartenverkauf = (Kartenverkauf) request.getServletContext().getAttribute("kartenverkauf");
				Sitzplatz[] sitzplaetze = kartenverkauf.getSitzplaetze();
				
				for(int i=0; i<kartenverkauf.getSitzplaetze().length; i++) {
					if(i%10 == 0 && i != 0){
						%></tr><tr> <%
					}
					
					switch(sitzplaetze[i].getZustand()){
						case FREI:
							%> <td><%=i+1%></td> <% 
							break;
						case RESERVIERT:
							%> <td bgcolor="lightgrey"><%=i+1%></td> <% 
							break;
						case VERKAUFT:
							%> <td bgcolor="grey"><%=i+1%></td> <% 
							break;
					}
					
					if(i == kartenverkauf.getSitzplaetze().length){
						%> </tr> <%
					}
				
				}
				%>
				 
				</table>
			</td>
			<td />
			<td align="right" valign="bottom" width="300">
				<table rules="all" cellpadding="5" border="2" style="">
					<tr>
						<td>frei</td>
					</tr>
					<tr>
						<td bgcolor="lightgrey">reserviert</td>
					</tr>
					<tr>
						<td bgcolor="grey">verkauft</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	Reservierungen können <%=kartenverkauf.getReservationStatus() ? "noch" : "nicht mehr" %> angenommen werden.

	<p />
	<a href="Verkauf_eines_freien_Tickets.html">Verkauf eines freien Tickets</a>
	<p />
	<a href="Reservierung_eines_Tickets.html">Reservierung eines Tickets</a>
	<p />
	<a href="Verkauf_eines_reservierten_Tickets.html">Verkauf eines reservierten Tickets</a>
	<p />
	<a href="Stornierung_eines_Tickets.html">Stornierung eines Tickets</a> 
	<p />
	<a href="Reservierungen_aufheben.html">Reservierungen aufheben</a>
	
	<p><%=kartenverkauf.toString() %></p>
	
</body>
</html>

<% 
//TODO: Header-Bereich auslagern, Hidden Elemente nicht benutzen
%>