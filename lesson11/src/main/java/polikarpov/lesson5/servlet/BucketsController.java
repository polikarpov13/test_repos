package polikarpov.lesson5.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import polikarpov.lesson5.domain.Bucket;
import polikarpov.lesson5.domain.Product;
import polikarpov.lesson5.dto.BucketDto;
import polikarpov.lesson5.service.BucketService;
import polikarpov.lesson5.service.ProductService;
import polikarpov.lesson5.service.implementation.BucketServiceImplementation;
import polikarpov.lesson5.service.implementation.ProductServiceImplementation;

@WebServlet("/buckets")
public class BucketsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private	BucketService bucketService = BucketServiceImplementation.getBucketService();
	private ProductService productService = ProductServiceImplementation.getProductService();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Bucket> buckets = bucketService.readAll();
		Map<Integer, Product> idToProduct = productService.readAllMap();
		List<BucketDto> listOfBucketDtos = map(buckets, idToProduct);
		
		String json = new Gson().toJson(listOfBucketDtos);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	public List<BucketDto> map(List<Bucket> buckets, Map<Integer, Product> idToProduct){

		return	buckets.stream().map(bucket->{
			BucketDto bucketDto = new BucketDto();
			bucketDto.bucketId = bucket.getId();
			bucketDto.purchaseDate = bucket.getPurchaseDate();
		   
			Product product = idToProduct.get(bucket.getProductId());
		    bucketDto.name = product.getName();
		    bucketDto.description = product.getDescription();
		    bucketDto.price = product.getPrice();
			
			return bucketDto;
		}).collect(Collectors.toList());
		
	} 
}
