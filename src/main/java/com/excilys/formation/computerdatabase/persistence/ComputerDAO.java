package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.dataBinders.mapper.ComputerMapper;
import com.excilys.formation.computerdatabase.model.Computer;

public class ComputerDAO implements Crudable<Computer> {

	private final static Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");

	private final static String FIELDS = "computer.id as computer_id, computer.name as computer_name, introduced, discontinued, company_id";

	private final String countQuery = "SELECT COUNT(*) as total from `computer-database-db`.computer;";
	private final String findAllQuery = "SELECT " + FIELDS
			+ " , company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id;";
	private final String findWithRangeQuery = "SELECT " + FIELDS
			+ ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id limit ?, ?;";
	private final String findByIdQuery = "SELECT " + FIELDS
			+ ", company.id as company_id, company.name as company_name FROM `computer-database-db`.computer left join `computer-database-db`.company on computer.company_id = company.id where computer.id = ?;";

	private final String insertQuery = "INSERT INTO `computer-database-db`.computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
	private final String updateQuery = "UPDATE `computer-database-db`.computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
	private final String deleteQuery = "DELETE FROM `computer-database-db`.computer WHERE id = ?;";

	private ConnectionFactory connectionFactory;

	public ComputerDAO() {
		this.connectionFactory = ConnectionFactory.getInstance();
	}

	@Override
	public int count() {
		Connection connection = connectionFactory.getConnection();

		int count = 0;

		try (Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(countQuery);

			if (resultSet.first()) {
				count = (int) resultSet.getInt("total");
			}

		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return count;
	}

	@Override
	public List<Computer> find() {
		Connection connection = connectionFactory.getConnection();

		List<Computer> computers = null;

		try (Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(findAllQuery);
			ComputerMapper mapper = new ComputerMapper();

			computers = mapper.mapList(resultSet);

		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return computers;
	}

	@Override
	public List<Computer> find(int from, int max) {
		Connection connection = connectionFactory.getConnection();

		List<Computer> computers = null;

		try (PreparedStatement statement = connection.prepareStatement(findWithRangeQuery)) {
			statement.setInt(1, from);
			statement.setInt(2, max);

			ResultSet resultSet = statement.executeQuery();
			ComputerMapper mapper = new ComputerMapper();

			computers = mapper.mapList(resultSet);

		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return computers;
	}

	@Override
	public Computer find(Long id) {
		Connection connection = connectionFactory.getConnection();

		Computer computer = null;

		try (PreparedStatement statement = connection.prepareStatement(findByIdQuery)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			ComputerMapper mapper = new ComputerMapper();

			if (resultSet.first()) {
				computer = mapper.map(resultSet);
			} else {
				return null;
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return computer;
	}

	@Override
	public Long create(Computer computer) {
		Connection connection = connectionFactory.getConnection();

		Long newId = null;

		try (PreparedStatement statement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
			if (computer.getName() == null) {
				LOGGER.error("ERROR Insert : Could not create an unnamed computer !");
				return null;
			}
			if (computer.getCompany().getId() == null) {
				LOGGER.error("ERROR Insert : Could not create a computer without it's company !!");
				return null;
			}

			statement.setString(1, computer.getName());
			statement.setDate(2, Date.valueOf(computer.getIntroduced()));
			statement.setDate(3, Date.valueOf(computer.getDiscontinued()));
			statement.setLong(4, computer.getCompany().getId());

			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				LOGGER.error("Error Insert : failed, no rows affected");
				return null;
			}
			ResultSet generatedId = statement.getGeneratedKeys();
			if (generatedId.next()) {
				newId = generatedId.getLong(1);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return newId;
	}

	@Override
	public boolean delete(Long id) {
		Connection connection = connectionFactory.getConnection();

		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
			statement.setLong(1, id);

			affectedRows = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Delete : failed, no rows affected");
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return affectedRows != 0 ? true : false;
	}

	@Override
	public Computer update(Computer computer) {
		Connection connection = connectionFactory.getConnection();

		int affectedRows = 0;

		Computer oldVersion = this.find(computer.getId());
		if (oldVersion == null) {
			return null;
		}

		try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
			ComputerMapper mapper = new ComputerMapper();
			mapper.merge(computer, oldVersion);

			if (computer.getName() == null) {
				LOGGER.error("ERROR Insert : Could not update to an unnamed computer !");
				return null;

			}
			if (computer.getCompany().getId() == null) {
				LOGGER.error("ERROR Insert : Could not update to a computer without it's company !!");
				return null;
			}

			statement.setString(1, computer.getName());
			statement.setDate(2, Date.valueOf(computer.getIntroduced()));
			statement.setDate(3, Date.valueOf(computer.getDiscontinued()));
			statement.setLong(4, computer.getCompany().getId());
			statement.setLong(5, computer.getId());

			affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				LOGGER.error("ERROR Update : failed, no rows affected");
				return null;
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return null;
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return computer;
	}

}