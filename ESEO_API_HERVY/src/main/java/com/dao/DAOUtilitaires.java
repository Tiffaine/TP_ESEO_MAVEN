package com.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Classe Utilitaire pour les DAO.
 * 
 * <p>Cette classe centralise des attributs et des méthodes utilisés par les DAO.</p>
 * 
 * @author Thomas MENARD, Maxime LENORMAND & Lilian BRAUD
 */
public final class DAOUtilitaires {
	
	// attributs des NoteInteret
	protected static final String ATTRIBUT_ID_PROFESSEUR = "idProfesseur";
	protected static final String ATTRIBUT_ID_SUJET = "idSujet";
	protected static final String ATTRIBUT_NOTE = "note";
	
	private static final String SELECT = "SELECT";
	private static final String UPDATE = "UPDATE";
	private static final String DELETE = "DELETE";
	private static final String WHERE = " WHERE ";
	private static final String WHERE_ID = " WHERE id";
	private static final String AND = " AND ";
	private static final String AND_ID = " AND id";
	private static final String ID = ".id";
	
	private static Logger logger = Logger.getLogger(DAOUtilitaires.class.getName());

	/**
	 * Constructeur caché par défaut (car c'est une classe finale utilitaire,
	 * contenant uniquement des méthodes appelées de manière statique).
	 */
	private DAOUtilitaires() {

	}
	
	
	// ########################################################################################################
	// #                            Methodes pour la fermeture des ressources                                 #
	// ########################################################################################################
	
	/**
	 * Ferme le resultset.
	 * 
	 * @param resultSet le resultSet à fermer.
	 */
	public static void fermeture(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Echec de la fermeture du ResultSet : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Ferme le statement.
	 * 
	 * @param statement le statement à fermer.
	 */
	public static void fermeture(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Echec de la fermeture du Statement : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Ferme la connection.
	 * 
	 * @param connection la connection à fermer.
	 */
	public static void fermeture(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.WARN, "Echec de la fermeture de la connexion : " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Ferme le statement et la connection.
	 * 
	 * @param statement le statement à fermer.
	 * @param connection la connection à fermer.
	 */
	public static void fermetures(Statement statement, Connection connection) {
		fermeture(statement);
		fermeture(connection);
	}

	/**
	 * Ferme le resultSet, le statement et la connection.
	 * 
	 * @param resultSet le resultSet à fermer.
	 * @param statement le statement à fermer.
	 * @param connection la connection à fermer.
	 */
	public static void fermetures(ResultSet resultSet, Statement statement, Connection connection) {
		fermeture(resultSet);
		fermeture(statement);
		fermeture(connection);
	}
	
	
	// ########################################################################################################
	// #                            Methodes pour la création des requêtes préparées                          #
	// ########################################################################################################

	/**
	 * Initialise une requête préparée.
	 * 
	 * @param connection la connexion à la BDD.
	 * @param sql la requête SQL.
	 * @param returnGeneratedKeys le boolean qui permet de générer des ID ou pas.
	 * @param objets la liste d'objets à insérer dans la requête.
	 * @return preparedStatement la requête préparée initialisée.
	 * @throws SQLException
	 */
	protected static String initialisationRequetePreparee(String sql, Object... objets) {
		String[] listeSQL = (sql+" ").split("\\?");
		StringBuilder newSQL = new StringBuilder(listeSQL[0]);
		for(int i = 0; i<objets.length; i++) {
			newSQL.append("\"" + objets[i] + "\"" + listeSQL[i+1]);
		}
		return newSQL.toString().replaceAll("\"null\"", "null");
	}	
	
	/**
	 * Initialise une requête préparée.
	 * 
	 * @param sql la requête SQL.
	 * @param la liste d'objets à insérer dans la requête.
	 * @return La requête SQL complété des paramètres
	 * @throws SQLException
	 */
	protected static String initialisationRequetePreparee(String sql, String[] parametres) {
		String[] listeSQL = (sql+" ").split("\\?");
		StringBuilder newSQL = new StringBuilder(listeSQL[0]);
		for(int i = 0; i<parametres.length; i++) {
			newSQL.append("\"" + parametres[i] + "\"" + listeSQL[i+1]);
		}
		return newSQL.toString().replaceAll("\"null\"", "null");
	}

	
}
