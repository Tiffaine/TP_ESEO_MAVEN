package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Ville;

@RestController
//@RequestMapping("/path")  //Permet de rajouter des éléments dans le path 
public class VilleController {
	private static Connection connect;
	private static String selectAll = "SELECT * FROM `ville_france`";

	private static String url = "jdbc:mysql://localhost/maven?useLegacyDatetimeCode=false&serverTimezone=Europe/Paris&useSSL=false";
	private static String user = "root";
	private static String pwd = "";
	private static String insertInto = "INSERT INTO `ville_france`(`Code_commune_INSEE`, `Nom_commune`, `Code_postal`, `Libelle_acheminement`, `Ligne_5`, `Latitude`, `Longitude`) VALUES (?,?,?,?,?,?,?);";


	public static Connection connectBDD() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			connect = DriverManager.getConnection(url, user, pwd);

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return connect;

	}

	@RequestMapping(value = "/testget", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Ville> selectAll(@RequestParam(required = false, value = "value") String value) throws SQLException {
		connectBDD();
		List<Ville> listeVilles = new ArrayList<Ville>();
		Statement stmt = connect.createStatement();
		ResultSet result = stmt.executeQuery(selectAll);
		while (result.next()) {

			listeVilles.add(new Ville(result.getString("Code_commune_INSEE"), result.getString("Nom_commune"),
					result.getString("Code_postal"), result.getString("Latitude"), result.getString("Longitude")));
		}
		return listeVilles;
	}

	@RequestMapping(value = "/testput", method = RequestMethod.PUT)
	@ResponseBody
	@CrossOrigin
	public void put(@RequestParam(required = true, value = "Code_commune_INSEE") String Code_commune_INSEE,
			@RequestParam(required = true, value = "nom_commune") String nom_commune,
			@RequestParam(required = true, value = "code_postal") String code_postal,
			@RequestParam(required = true, value = "libelle") String libelle,
			@RequestParam(required = true, value = "ligne5") String ligne5,
			@RequestParam(required = true, value = "latitude") String latitude,
			@RequestParam(required = true, value = "longitude") String longitude) throws SQLException {
		connectBDD();
		Statement stmt = connect.createStatement();
		java.sql.PreparedStatement prepare = this.connect.prepareStatement(insertInto);
		prepare.setString(1, Code_commune_INSEE);
		prepare.setString(2, nom_commune);
		prepare.setString(3, code_postal);
		prepare.setString(4, libelle);
		prepare.setString(5, ligne5);
		prepare.setString(6, latitude);
		prepare.setString(7, longitude);
		stmt.execute(insertInto);
		stmt.close();
		connect.close();
	}

	@RequestMapping(value = "/testpost", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public void post(@RequestParam(required = false, value = "nomCommune") String nomCommune, 
			@RequestParam(required = false, value = "codeCommune") String codeCommune) throws SQLException {
		
			connectBDD();

			Statement stmt = connect.createStatement();
			String requete = "UPDATE `ville_france` SET `Nom_commune`=\""+nomCommune+"\" where `Code_commune_INSEE`=\""+codeCommune+"\";";
					
			stmt.execute(requete);
			stmt.close();
			connect.close();
	}

	@RequestMapping(value = "/testdelete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam(required = false, value = "Code_commune_INSEE") String Code_commune_INSEE) throws SQLException {
			connectBDD();

			Statement stmt = connect.createStatement();
			String requete = "DELETE FROM `ville_france` WHERE `Code_commune_INSEE`=\""+Code_commune_INSEE+"\";";
			System.out.println("requete"+requete);
			stmt.execute(requete);
			stmt.close();
			connect.close();
		
	}

	@RequestMapping(value = "/testgetfiltre", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public List<Ville> getfiltre(@RequestParam(required = false, value = "nomCommune") String nomCommune) throws SQLException {
		List<Ville> listeVilles = new ArrayList<Ville>();
			connectBDD();

			Statement stmt = connect.createStatement();
			String requete = "Select * FROM `ville_france` WHERE `Nom_commune`=\""+nomCommune+"\";";
			System.out.println("requete"+requete);
			
			ResultSet result = stmt.executeQuery(requete);

			while (result.next()) {
				listeVilles.add(new Ville(result.getString("Code_commune_INSEE"), result.getString("Nom_commune"),
						result.getString("Code_postal"), result.getString("Latitude"), result.getString("Longitude")));
			}
			result.close();
			stmt.close();
			connect.close();
			System.out.println("liste ville"+listeVilles);

		
		return listeVilles;
	}

}
