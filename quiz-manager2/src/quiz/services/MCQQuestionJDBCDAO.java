package quiz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	private static final String UPDATE_STATEMENT = "UPDATE MCQQUESTION SET CHOICE1=?, CHOICE2=?, CHOICE3=?, CHOICE4=?, ANSWER=? WHERE ID=?";
	private static final String DELETE_STATEMENT = "DELETE FROM MCQQUESTION WHERE MCQID = ?";

	/*
	 * public void create(MCQQuestion MCQ) {
	 *
	 * try (Connection connection = getConnection(); PreparedStatement
	 * insertStatement = connection.prepareStatement(INSERT_STATEMENT);) {
	 *
	 * insertStatement.setString(1, MCQ.getQuestion()); insertStatement.setInt(2,
	 * MCQ.getDifficulty()); MCQ.num ++; insertStatement.execute();
	 *
	 * } catch (SQLException e) { e.printStackTrace(); }
	 *
	 * }
	 *
	 * public void update(MCQQuestion MCQ) {
	 *
	 *
	 *
	 * try (Connection connection = getConnection(); PreparedStatement
	 * updateStatement = connection.prepareStatement(UPDATE_STATEMENT)){
	 * updateStatement.setString(1, MCQ.getQuestion()); updateStatement.setInt(2,
	 * MCQ.getDifficulty()); updateStatement.setInt(3, MCQ.getId());
	 * updateStatement.executeQuery(); }catch (SQLException e) {
	 * e.printStackTrace(); }
	 *
	 * }
	 *
	 *
	 * public void delete(Question question) {
	 *
	 * try (Connection connection = getConnection(); PreparedStatement
	 * deleteStatement = connection.prepareStatement(DELETE_STATEMENT)){
	 * deleteStatement.setInt(1, question.getId()); deleteStatement.executeQuery();
	 * }catch (SQLException e) { e.printStackTrace(); } }
	 */

	private Connection getConnection() throws SQLException {
		Configuration conf = Configuration.getInstance();
		String jdbcUrl = conf.getConfigurationValue("jdbc.url");
		String user = conf.getConfigurationValue("jdbc.user");
		String password = conf.getConfigurationValue("jdbc.password");
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
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
