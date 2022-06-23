package polikarpov.lesson5.service;

import java.util.Map;

import polikarpov.lesson5.domain.Product;
import polikarpov.lesson5.shared.AbstractCRUD;

public interface ProductService extends AbstractCRUD<Product>{
	public Map<Integer, Product> readAllMap();
}
