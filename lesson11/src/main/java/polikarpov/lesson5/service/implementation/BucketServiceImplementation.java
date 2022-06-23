package polikarpov.lesson5.service.implementation;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import polikarpov.lesson5.dao.BucketDao;
import polikarpov.lesson5.dao.implementation.BucketDaoImplementation;
import polikarpov.lesson5.domain.Bucket;
import polikarpov.lesson5.service.BucketService;

public class BucketServiceImplementation implements BucketService {
	
	private static Logger LOGGER = Logger.getLogger(BucketServiceImplementation.class);
	
	private static BucketServiceImplementation bucketServiceImplementation;
	
	private BucketDao bucketDao;

	private BucketServiceImplementation() {

		try {
			bucketDao = new BucketDaoImplementation();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
		}

	}
	
	public static BucketService getBucketService() {
		if (bucketServiceImplementation == null)
			bucketServiceImplementation = new BucketServiceImplementation();
		return bucketServiceImplementation;
	}

	@Override
	public Bucket create(Bucket bucket) {
		return bucketDao.create(bucket);
	}

	@Override
	public Bucket read(Integer id) {
		return bucketDao.read(id);
	}

	@Override
	public Bucket update(Bucket t) {
		return bucketDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		bucketDao.delete(id);
	}

	@Override
	public List<Bucket> readAll() {
		return bucketDao.readAll();
	}
}
