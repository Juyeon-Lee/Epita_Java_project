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

	private final static Logger LOG = Logger.getGlobal();

	public int create(List<String> mcqInfo) {

		try (Connection connection = getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(INSERT_STATEMENT);) {

			insertStatement.setString(1, mcqInfo.get(0)); //choice1
			insertStatement.setString(2, mcqInfo.get(1)); //choice2
			insertStatement.setString(3, mcqInfo.get(2)); //choice3
			insertStatement.setString(4, mcqInfo.get(3)); //choice4
			insertStatement.setString(5, mcqInfo.get(4)); //answer

			insertStatement.execute();
			LOG.info("insert success");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String selectQuery = "select * from MCQQUESTION " +
				"WHERE {choice1=mcqInfo.get(0) and choice2=mcqInfo.get(1) " +
				"and choice3=mcqInfo.get(2) and choice4=mcqInfo.get(3) and answer=mcqInfo.get(4)}";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);)
		{
			ResultSet results = preparedStatement.executeQuery();
			int mcqId = results.getInt(0);
			LOG.info("done getting MCQId : "+mcqId);
			results.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;// 0 : error
	}

    public void update(MCQQuestion MCQ) {

        try (Connection connection = getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(UPDATE_STATEMENT)){
            updateStatement.setString(1, MCQ.getQuestion());
            updateStatement.setInt(2, MCQ.getDifficulty());
            updateStatement.setInt(3, MCQ.getId());
            updateStatement.executeQuery();
        }catch (SQLException e) {
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
             PreparedStatement deleteStatement = connection.prepareStatement(DELETE_STATEMENT)){
            deleteStatement.setInt(1, question.getId());
            deleteStatement.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public boolean correctAnswer(int mcqid,String answer )
	{
		List<MCQQuestion> resultList = new ArrayList<MCQQuestion>();
		resultList = showChoice(mcqid);
		MCQQuestion rAnswer = resultList.get(0);

		return rAnswer.getAnswer().equals(answer);

	}

	public List<MCQQuestion> showChoice(int mcqid) {
		List<MCQQuestion> resultList = new ArrayList<MCQQuestion>();

		String selectQuery = "select * from MCQQUESTION WHERE MCQID = ?";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);) {

			preparedStatement.setInt(1, mcqid);
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				int MCQID = results.getInt("MCQID");
				String choice1 = results.getString("CHOICE1");
				String choice2 = results.getString("CHOICE2");
				String choice3 = results.getString("CHOICE3");
				String choice4 = results.getString("CHOICE4");
				String answer = results.getString("Answer");
				MCQQuestion currentQuestion = new MCQQuestion(MCQID, choice1, choice2, choice3, choice4, answer);
				resultList.add(currentQuestion);
			}
			results.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

}
