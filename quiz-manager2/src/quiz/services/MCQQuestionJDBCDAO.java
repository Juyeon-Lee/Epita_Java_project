package quiz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import quiz.datamodel.MCQQuestion;
import quiz.datamodel.Question;

public class MCQQuestionJDBCDAO {

	/*
	 * DELETE FROM QUESTION WHERE ID = 3;
	 *
	 * select * from question;
	 */

	private static final String INSERT_STATEMENT = "INSERT INTO MCQQUESTION (CHOICE1, CHOICE2, CHOICE3, CHOICE4, ANSWER) VALUES (?, ?, ?, ?, ?)";
	private static final String SEARCH_STATEMENT = "SELECT * FROM MCQQUESTION";
	private static final String UPDATE_STATEMENT = "UPDATE MCQQUESTION SET CHOICE1=?, CHOICE2=?, CHOICE3=?, CHOICE4=?, ANSWER=? WHERE MCQID=?";
	private static final String DELETE_STATEMENT = "DELETE FROM MCQQUESTION WHERE MCQID = ?";
	private static final String SELECT_STATEMENT = "SELECT MCQID from MCQQUESTION WHERE CHOICE1=? AND CHOICE2=? AND CHOICE3=? AND CHOICE4=? AND ANSWER=?";

	private static final Logger LOG = Logger.getGlobal();

	public int create(List<String> mcqInfo) {

		try (Connection connection = getConnection();
			 PreparedStatement insertStatement = connection.prepareStatement(INSERT_STATEMENT);) {

			insertStatement.setString(1, mcqInfo.get(0)); // choice1
			insertStatement.setString(2, mcqInfo.get(1)); // choice2
			insertStatement.setString(3, mcqInfo.get(2)); // choice3
			insertStatement.setString(4, mcqInfo.get(3)); // choice4
			insertStatement.setString(5, mcqInfo.get(4)); // answer

			insertStatement.execute();
//		    return 0;
			LOG.info("insert success");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Connection connection = getConnection();
			 PreparedStatement selectStatement = connection.prepareStatement(SELECT_STATEMENT);) {
			selectStatement.setString(1, mcqInfo.get(0));
			selectStatement.setString(2, mcqInfo.get(1));
			selectStatement.setString(3, mcqInfo.get(2));
			selectStatement.setString(4, mcqInfo.get(3));
			selectStatement.setString(5, mcqInfo.get(4));

			ResultSet results = selectStatement.executeQuery();
			int mcqId=0;
			if(results.next()){
				mcqId = results.getInt("MCQID");
			}
			LOG.info("done getting MCQId : " + mcqId);
			results.close();

			return mcqId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;// 0 : error
	}

	public void update(MCQQuestion MCQ) {

		try (Connection connection = getConnection();
			 PreparedStatement updateStatement = connection.prepareStatement(UPDATE_STATEMENT)) {
			updateStatement.setString(1, MCQ.getQuestion());
			updateStatement.setInt(2, MCQ.getDifficulty());
			updateStatement.setInt(3, MCQ.getId());
			updateStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private Connection getConnection() throws SQLException {
		Configuration conf = Configuration.getInstance();
		String jdbcUrl = conf.getConfigurationValue("jdbc.url");
		String user = conf.getConfigurationValue("jdbc.user");
		String password = conf.getConfigurationValue("jdbc.password");
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
	}

	public void delete(Question question) {

		try (Connection connection = getConnection();
			 PreparedStatement deleteStatement = connection.prepareStatement(DELETE_STATEMENT)) {
			deleteStatement.setInt(1, question.getId());
			deleteStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Question> search(Question question) {
		List<Question> resultList = new ArrayList<Question>();

		/*
		 * SELECT ID,DIFFICULTY,QUESTION FROM QUESTION WHERE DIFFICULTY = 1 and QUESTION
		 * LIKE '%JV%'
		 *
		 */
		String selectQuery = "select  from QUESTION WHERE ";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);) {

			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {

				Question currentQuestion = new Question();
				resultList.add(currentQuestion);
			}
			results.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
