package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.computerdatabase.dataBinders.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.model.Company;
import com.excilys.formation.computerdatabase.model.SelectOptions;

public enum CompanyDAO implements Crudable<Company> {
	INSTANCE;

	private final static Logger LOGGER = LoggerFactory.getLogger("com.excilys.formation.computerdatabase");
	
	private final static String FIELDS = "company.id as company_id, company.name as company_name";

	ConnectionFactory connectionFactory;

	CompanyDAO() {
		this.connectionFactory = ConnectionFactory.getInstance();
	}

	private final String countQuery = "SELECT COUNT(*) as count from `computer-database-db`.company;";

	@Override
	public int count() {
		Connection connection = connectionFactory.getConnection();

		int count = 0;

		try (Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(countQuery);

			if (resultSet.first()) {
				count = resultSet.getInt("count");
			}

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return count;
	}

	private final String findAllQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company;";

	@Override
	public List<Company> find() {
		Connection connection = connectionFactory.getConnection();

		List<Company> companies = null;

		try (Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery(findAllQuery);

			CompanyMapper mapper = new CompanyMapper();

			companies = mapper.mapList(resultSet);

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return companies;
	}

	private final String findWithRangeQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company limit ?, ?;";

	@Override
	public List<Company> find(SelectOptions options) {
		Connection connection = connectionFactory.getConnection();
		List<Company> companies = null;

		try (PreparedStatement statement = connection.prepareStatement(findWithRangeQuery)){		
			statement.setLong(1, options.getOffset());
			statement.setLong(2, options.getRange());
			ResultSet resultSet = statement.executeQuery();
			CompanyMapper mapper = new CompanyMapper();

			companies = mapper.mapList(resultSet);

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return companies;
	}

	private final String findByIdQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company WHERE id = ? ;";

	@Override
	public Company find(Long id) {
		Connection connection = connectionFactory.getConnection();
		Company company = null;

		try (PreparedStatement statement = connection.prepareStatement(findByIdQuery)){
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			CompanyMapper mapper = new CompanyMapper();

			if (resultSet.first()) {
				company = mapper.map(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return company;
	}
}
