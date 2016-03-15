package com.excilys.formation.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.computerdatabase.dataBinders.mapper.CompanyMapper;
import com.excilys.formation.computerdatabase.model.Company;

public class CompanyDAO implements Crudable<Company> {
	private final static Logger LOGGER = LogManager.getLogger("com.excilys.formation.computerdatabase");

	private final static String FIELDS = "company.id as company_id, company.name as company_name";


	private ConnectionFactory connectionFactory;

	public CompanyDAO() {
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
			LOGGER.error(e);
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
			LOGGER.error(e);
		} finally {
			connectionFactory.closeConnection(connection);
		}

		return companies;
	}

	private final String findWithRangeQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company limit ?, ?;";
	@Override
	public List<Company> find(int from, int max) {
		Connection connection = connectionFactory.getConnection();
		List<Company> companies = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(findWithRangeQuery);
			statement.setLong(1, from);
			statement.setLong(2, max);
			ResultSet resultSet = statement.executeQuery();
			CompanyMapper mapper = new CompanyMapper();

			companies = mapper.mapList(resultSet);

		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			connectionFactory.closeConnection(connection);
		}

		return companies;
	}

	private final String findByIdQuery = "SELECT " + FIELDS + " FROM `computer-database-db`.company WHERE id = ? ;";
	@Override
	public Company find(Long id) {
		Connection connection = connectionFactory.getConnection();
		Company company = null;

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(findByIdQuery);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			CompanyMapper mapper = new CompanyMapper();

			if (resultSet.first()) {
				company = mapper.map(resultSet);
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception e2) {
					LOGGER.error(e2);
				}
			}
			connectionFactory.closeConnection(connection);
		}

		return company;
	}

	@Override
	public Company update(Company toUpdate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Long create(Company toCreate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Long id) {
		throw new UnsupportedOperationException();
	}

}
