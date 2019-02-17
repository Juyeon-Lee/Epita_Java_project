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

public class QuestionJDBCDAO {
	

	private static final String INSERT_STATEMENT = "INSERT INTO QUESTION (QUESTION, MCQ, TOPIC, DIFFICULTY) VALUES (?, ?, ?, ?)";
	private static final String SEARCH_STATEMENT = "SELECT * FROM QUESTION ";
	private static final String UPDATE_STATEMENT = "UPDATE QUESTION SET QUESTION=?, MCQ=?, TOPIC=?, DIFFICULTY=? WHERE ID=?";
	private static final String DELETE_STATEMENT = "DELETE FROM QUESTION WHERE ID = ?";

    private static final Logger LOG = Logger.getGlobal();
	List<Integer> list = new ArrayList<Integer>(); // list for Id

	private Connection getConnection() throws SQLException {
		Configuration conf = Configuration.getInstance();
		String jdbcUrl = conf.getConfigurationValue("jdbc.url");
		String user = conf.getConfigurationValue("jdbc.user");
		String password = conf.getConfigurationValue("jdbc.password");
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		return connection;
	}
	
	public String toStringofTopics(List<String> topics) {
		return String.join(",", topics); //topicsCommaSeparated..
	}
	
	public List<String> toListfromString(String topic){
		return Arrays.asList(topic.split("\\s*,\\s*"));
	}
	
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

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void update(ArrayList<String> list) {
		try (Connection connection = getConnection();
			 PreparedStatement updateStatement = connection.prepareStatement(UPDATE_STATEMENT)){
			updateStatement.setString(1, list.get(0)); //question
			updateStatement.setInt(2, Integer.parseInt(list.get(1))); //mcq
			updateStatement.setString(3, list.get(2)); //topics
			updateStatement.setInt(4, Integer.parseInt(list.get(3))); //difficulty
            updateStatement.setInt(5, Integer.parseInt(list.get(4)));
			updateStatement.execute();
			LOG.info("A question is updated.");
		}catch (SQLException e) {
			e.printStackTrace();
		}

	}

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public List<Question> showAll(String condition) {
		List<Question> resultList = new ArrayList<Question>();

		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement( SEARCH_STATEMENT+condition );) {

			ResultSet results = preparedStatement.executeQuery();
			while (results.next()) {
				int id = results.getInt("ID");
				String question = results.getString("QUESTION");
				int mcq = results.getInt("MCQ");
				String topic = results.getString("TOPIC");
				int difficulty = results.getInt("DIFFICULTY");
				Question currentQuestion = new Question(id, question, mcq, null, difficulty);
				resultList.add(currentQuestion);
			}
			results.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<resultList.size();i++) {
			System.out.println(resultList.get(i).getId());
			list.add(resultList.get(i).getId());
			//System.out.print(list.get(i)+" ");
		}

	}


	public List<Question> searchByTopic(String topic) {
		List<Question> resultList = new ArrayList<Question>();
		try (Connection connection = getConnection();
	             PreparedStatement searchStatement = connection.prepareStatement(SEARCH_STATEMENT);
	        ) {
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
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return resultList;
	}
	
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return 0;
	}
	
		public List<Question> search(Question question) {
			List<Question> resultList = new ArrayList<Question>();

				/*SELECT
			    ID,DIFFICULTY,QUESTION
			    FROM QUESTION
			    WHERE
			       DIFFICULTY = 1
			    and
			      QUESTION LIKE '%JV%'

			      */
			String selectQuery = "select ID,DIFFICULTY,QUESTION from QUESTION WHERE DIFFICULTY = ?";
			try (Connection connection = getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
			) {

				//preparedStatement.setInt(1,question.getId());
				preparedStatement.setInt(1,question.getDifficulty());
				//preparedStatement.setString(3,question.getQuestion());
				ResultSet results = preparedStatement.executeQuery();
				while (results.next()) {
					int id = results.getInt("ID");
					int difficulty = results.getInt("DIFFICULTY");
					String question_1 = results.getString("QUESTION");
					Question currentQuestion = new Question(id,difficulty,question_1);
					resultList.add(currentQuestion);
				}
				results.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultList;
		}
		
	

}
