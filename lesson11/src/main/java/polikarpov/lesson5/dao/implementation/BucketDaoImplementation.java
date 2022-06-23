package polikarpov.lesson5.dao.implementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import polikarpov.lesson5.dao.BucketDao;
import polikarpov.lesson5.domain.Bucket;
import polikarpov.lesson5.utils.ConnectionUtils;

public class BucketDaoImplementation implements BucketDao {

	private static String readEverything = "select * from bucket";
	private static String create = "insert into bucket(user_id, product_id, purchase_date) values (?, ?, ?);";
	private static String readById = "select * from bucket where id = ?";
	private static String deleteById = "delete from bucket where id = ?";

	private static Logger LOGGER = Logger.getLogger(BucketDaoImplementation.class);

	private Connection connection;
	private PreparedStatement preparedStatement;

	public BucketDaoImplementation()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		connection = ConnectionUtils.openConnection();
	}

	@Override
	public Bucket create(Bucket bucket) {
		try {
			preparedStatement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, bucket.getUserId());
			preparedStatement.setInt(2, bucket.getProductId());
			preparedStatement.setDate(3, new Date(bucket.getPurchaseDate().getTime()));
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			rs.next();
			bucket.setId(rs.getInt(1));
		} catch (SQLException e) {
			LOGGER.error(e);
		}
		return bucket;
	}

	@Override
	public Bucket read(Integer id) {
		Bucket bucket = null;
		try {
			preparedStatement = connection.prepareStatement(readById);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();

			Integer bucketId = result.getInt("id");
			Integer userId = result.getInt("user_id");
			Integer productId = result.getInt("product_id");
			Date purchaseDate = result.getDate("purchase_date");

			bucket = new Bucket(bucketId, userId, productId, purchaseDate);

		} catch (SQLException e) {
			LOGGER.error(e);
		}

		return bucket;
	}

	@Override
	public Bucket update(Bucket t) {
		return null;
	}

	@Override
	public void delete(Integer id) {
		try {
			preparedStatement = connection.prepareStatement(deleteById);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error(e);
		}

	}

	public List<Bucket> readAll() {

		List<Bucket> bucketRecords = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(readEverything);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				Integer bucketId = result.getInt("id");
				Integer userId = result.getInt("user_id");
				Integer productId = result.getInt("product_id");
				java.util.Date purchaseDate = result.getDate("purchase_date");
				bucketRecords.add(new Bucket(bucketId, userId, productId, purchaseDate));
			}
		} catch (SQLException e) {
			LOGGER.error(e);
		}

		return bucketRecords;
	}

}
