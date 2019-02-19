package quiz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import quiz.datamodel.Question;

/**
 * For Question Table
 * @author LeeJuyeon
 * @author SonMoeun
 *
 */
public class QuestionJDBCDAO {
	

	private static final String INSERT_STATEMENT = "INSERT INTO QUESTION (QUESTION, MCQ, TOPIC, DIFFICULTY) VALUES (?, ?, ?, ?)";
	private static final String SEARCH_STATEMENT = "SELECT * FROM QUESTION ";
	private static final String UPDATE_STATEMENT = "UPDATE QUESTION SET QUESTION=?, MCQ=?, TOPIC=?, DIFFICULTY=? WHERE ID=?";
	private static final String DELETE_STATEMENT = "DELETE FROM QUESTION WHERE ID = ?";

    private static final Logger LOG = Logger.getGlobal();
	List<Integer> list = new ArrayList<Integer>(); // list for Id

	/**
	 * @see quiz.services.Configuration#getInstance()
	 * @see quiz.services.Configuration#getConfigurationValue(String)
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {
		Configuration conf = Configuration.getInstance();
		String jdbcUrl = conf.getConfigurationValue("jdbc.url");
		String user = conf.getConfigurationValue("jdbc.user");
		String password = conf.getConfigurationValue("jdbc.password");
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
	}
	
	/**
	 * This method will convert a list of topics to a String of topics.
	 * The String will be comma separated.
	 * 
	 * @param topics - List of String
	 * @return String 
	 * @author LeeJuyeon
	 */
	public String toStringofTopics(List<String> topics) {
		return String.join(",", topics); //topicsCommaSeparated..
	}
	
	/**
	 * This method will convert a String of topics to a list of topics.
	 * 
	 * @param topic - string
	 * @return List of String
	 * @author LeeJuyeon
	 */
	public List<String> toListfromString(String topic){
		return Arrays.asList(topic.split("\\s*,\\s*"));
	}
	
	/**
	 * This method will perform 'insert' command by using parameters for question table.
	 * @param question - question, topics, difficulty
	 * @param mcq - int
	 * @see #toStringofTopics(List)
	 * @author LeeJuyeon(updated the original one)
	 */
	public void create(Question question, int mcq) {

		try (Connection connection = getConnection();
			 PreparedStatement insertStatement = connection.prepareStatement(INSERT_STATEMENT);) {
			QuestionJDBCDAO dao = new QuestionJDBCDAO();
			insertStatement.setString(1, question.getQuestion());
			if(mcq!=0)
				insertStatement.setInt(2, mcq);
			else
				insertStatement.setString(2, null);
			insertStatement.setString(3, dao.toStringofTopics(question.getTopics()));
			insertStatement.setInt(4, question.getDifficulty());
			insertStatement.execute();
			
			LOG.info("A question is created.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will perform 'update' command by using parameters.
	 * 
	 * @param list - ArrayList of String : question, mcq, topics, difficulty, id
	 * @author LeeJuyeon(updated the original one)
	 */
	public void update(ArrayList<String> list) {
		try (Connection connection = getConnection();
			 PreparedStatement updateStatement = connection.prepareStatement(UPDATE_STATEMENT)){
			updateStatement.setString(1, list.get(0)); //question
			updateStatement.setInt(2, Integer.parseInt(list.get(1))); //mcq
			updateStatement.setString(3, list.get(2)); //topics
			updateStatement.setInt(4, Integer.parseInt(list.get(3))); //difficulty
            updateStatement.setInt(5, Integer.parseInt(list.get(4))); //id
			updateStatement.execute();
			LOG.info("A question is updated.");
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will perform 'delete' command by id.
	 * @param id - int
	 */
	public void delete(int id) {
		try (Connection connection = getConnection();
			 PreparedStatement deleteStatement = connection.prepareStatement(DELETE_STATEMENT)){
			deleteStatement.setInt(1, id);
			deleteStatement.execute();
			LOG.info("A question is deleted.");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * It will print all information of all questions at console.
	 * If a question is open question, this method will print only id, question, topic, difficulty.
	 * If a question is MCQ question, this method will print id, question, topic, difficulty, and choices, answer.
	 * @see quiz.services.MCQQuestionJDBCDAO#print(int)
	 * @author LeeJuyeon
	 */
	public void printAll(){

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_STATEMENT);
        ) {
            MCQQuestionJDBCDAO mcq = new MCQQuestionJDBCDAO();
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                System.out.print("ID : "+ results.getInt("ID") + " / Question : " + results.getString("QUESTION") 
                +" / Topic : " + results.getString("TOPIC") + " / Difficulty : "+ results.getInt("DIFFICULTY"));
                if(results.getInt("MCQ")==0) {
                	System.out.println(" / This question is not a MCQ question.");
                }else {
                	System.out.println(" / linked MCQ id : " + results.getInt("MCQ"));
                	mcq.print(results.getInt("MCQ"));
                }
                
            }
            results.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * This method will do select query with a condition, and send List of questions with id, question, linked mcq id, topics, difficulty.
	 * 
	 * @see #toListfromString(String)
	 * @param condition - String
	 * @return List of Question
	 * @author SonMoeun
	 */
	public List<Question> showAll(String condition) {
		List<Question> resultList = new ArrayList<Question>();

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement( SEARCH_STATEMENT+condition );) {
			QuestionJDBCDAO dao = new QuestionJDBCDAO();
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				int id = results.getInt("ID");
				String question = results.getString("QUESTION");
				int mcq = results.getInt("MCQ");
				List<String> topics = dao.toListfromString(results.getString("TOPIC"));
				int difficulty = results.getInt("DIFFICULTY");
				Question currentQuestion = new Question(id, question, mcq, topics, difficulty);
				resultList.add(currentQuestion);
			}
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	/**
	 * The method will print all Ids of questions.
	 * 
	 * @author SonMoeun
	 */
	public void showAllID() {
		List<Question> resultList = new ArrayList<Question>();
		String selectQuery = "select ID from QUESTION";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
		) {
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				int id = results.getInt("ID");
				Question currentQuestion = new Question(id);

				resultList.add(currentQuestion);
			}
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0;i<resultList.size();i++) {
			System.out.println(resultList.get(i).getId());
			list.add(resultList.get(i).getId());
			//System.out.print(list.get(i)+" ");
		}

	}

	/**
	 * It will print some information of questions of one topic at console.
	 * It won't print about choices and answer.
	 * 
	 * @see #toListfromString(String)
	 * @param topic - String
	 * @return List of Question
	 * @author LeeJuyeon
	 */
	public List<Question> searchByTopic(String topic) {
		List<Question> resultList = new ArrayList<Question>();
		try (Connection connection = getConnection();
	             PreparedStatement searchStatement = connection.prepareStatement(SEARCH_STATEMENT)) 
		{
	         ResultSet results = searchStatement.executeQuery();
	         while (results.next()) {
	             List<String> st = toListfromString(results.getString("TOPIC"));
	             for(int i = 0; i<st.size(); i++) {
	             	if(st.get(i).equals(topic)) { //find the topic
	             		Question q = new Question(results.getInt("ID"),results.getString("QUESTION"),
	             				results.getInt("MCQ"),st,results.getInt("DIFFICULTY"));
		             	resultList.add(q);
	             		break;
		             }
	             }
	         }
	         results.close();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return resultList;
	}
	
	/**
	 * This method will return 1 or 0 that is indicated if it's linked or not with MCQquestion.
	 * @param mcqid - int
	 * @return int - linked : return 1 / unlinked : return 0
	 * @author LeeJuyeon
	 */
	public int ifLinkedornotWithMCQ(int mcqid) { //linked : return 1 / unlinked : return 0
		String searchQuery = "SELECT ID FROM QUESTION WHERE MCQ=" + mcqid;
		try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
			) {
				ResultSet results = preparedStatement.executeQuery();
				while (results.next()) {
					int id = results.getInt("ID");
					return id;
				}
				results.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return 0;
	}
	
	/**
	 * This method will excute query "select Id, difficulty, question from question where difficulty = ?".
	 * 
	 * @param question - Question
	 * @return list of Question
	 * @author SonMoeun
	 */
	public List<Question> search(Question question) {
		List<Question> resultList = new ArrayList<Question>();
	
		String selectQuery = "select ID,DIFFICULTY,QUESTION from QUESTION WHERE DIFFICULTY = ?";
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
		) {
			preparedStatement.setInt(1,question.getDifficulty());
			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				int id = results.getInt("ID");
				int difficulty = results.getInt("DIFFICULTY");
				String question_1 = results.getString("QUESTION");
				Question currentQuestion = new Question(id,difficulty,question_1);
				resultList.add(currentQuestion);
			}
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}
		
	

}
